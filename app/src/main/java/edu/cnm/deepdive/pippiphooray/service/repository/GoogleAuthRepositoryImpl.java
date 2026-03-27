package edu.cnm.deepdive.pippiphooray.service.repository;

import android.app.Activity;
import android.content.Context;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.credentials.ClearCredentialStateRequest;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import dagger.hilt.android.qualifiers.ApplicationContext;
import edu.cnm.deepdive.pippiphooray.R;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.json.JSONObject;

@Singleton
public class GoogleAuthRepositoryImpl implements GoogleAuthRepository {

  private static final String TAG = GoogleAuthRepositoryImpl.class.getSimpleName();

  private final CredentialManager credentialManager;
  private final String clientId;

  @Inject
  GoogleAuthRepositoryImpl(@ApplicationContext Context context) {
    credentialManager = CredentialManager.create(context);
    clientId = context.getString(R.string.client_id);
  }

  @Override
  public CompletableFuture<GoogleIdTokenCredential> signInQuickly(Activity activity) {
    return attemptSignIn(activity, true, false);
  }

  @Override
  public CompletableFuture<GoogleIdTokenCredential> signIn(Activity activity) {
    return attemptSignIn(activity, false, false);
  }

  @Override
  public CompletableFuture<GoogleIdTokenCredential> refreshToken(
      Activity activity, GoogleIdTokenCredential credential) {
    if (!isTokenExpired(credential.getIdToken())) {
      return CompletableFuture.completedFuture(credential);
    }
    return attemptSignIn(activity, true, true);
  }

  @Override
  public CompletableFuture<Void> signOut() {
    return CompletableFuture.runAsync(() -> {
      try {
        credentialManager.clearCredentialState(
            new ClearCredentialStateRequest());
      } catch (Exception e) {
        Log.e(TAG, "Error clearing credential state", e);
      }
    });
  }

  private CompletableFuture<GoogleIdTokenCredential> attemptSignIn(
      Activity activity, boolean filterByAuthorizedAccounts, boolean autoSelect) {

    CompletableFuture<GoogleIdTokenCredential> future = new CompletableFuture<>();

    GetGoogleIdOption googleIdOption