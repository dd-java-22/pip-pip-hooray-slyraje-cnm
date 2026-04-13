package edu.cnm.deepdive.pippiphooray.service.repository;

import android.app.Activity;
import android.content.Context;
import android.os.CancellationSignal;
import android.util.Base64;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.credentials.ClearCredentialStateRequest;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.ClearCredentialException;
import androidx.credentials.exceptions.GetCredentialException;
import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import dagger.hilt.android.qualifiers.ApplicationContext;
import edu.cnm.deepdive.pippiphooray.R;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.json.JSONObject;

/**
 * Repository implementation for Google authentication operations.
 */
@Singleton
public class GoogleAuthRepositoryImpl implements GoogleAuthRepository {

  private static final String TAG = GoogleAuthRepositoryImpl.class.getSimpleName();

  private final CredentialManager credentialManager;
  private final String clientId;
  private final Executor directExecutor = Runnable::run;

  /**
   * Constructs a GoogleAuthRepositoryImpl with the specified application context.
   *
   * @param context the application context for accessing resources and credential manager
   */
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
      ClearCredentialStateRequest request = new ClearCredentialStateRequest();
      CancellationSignal cancellationSignal = new CancellationSignal();

      credentialManager.clearCredentialStateAsync(
          request,
          cancellationSignal,               // or null
          directExecutor,                   // run callbacks on caller thread
          new CredentialManagerCallback<Void, ClearCredentialException>() {
            @Override
            public void onResult(@NonNull Void result) {
              Log.d(TAG, "Credential state cleared.");
            }

            @Override
            public void onError(@NonNull ClearCredentialException e) {
              Log.e(TAG, "Error clearing credential state", e);
            }
          }
      );
    });
  }

  /**
   * Attempts to sign in the user using the credential manager.
   *
   * @param activity the activity context for the sign-in flow
   * @param filterByAuthorizedAccounts whether to filter by authorized accounts
   * @param autoSelect whether to automatically select if only one account is available
   * @return a CompletableFuture containing the Google ID token credential
   */
  private CompletableFuture<GoogleIdTokenCredential> attemptSignIn(
      Activity activity, boolean filterByAuthorizedAccounts, boolean autoSelect) {

    CompletableFuture<GoogleIdTokenCredential> future = new CompletableFuture<>();

    GetGoogleIdOption googleIdOption = new GetGoogleIdOption.Builder()
        .setFilterByAuthorizedAccounts(filterByAuthorizedAccounts)
        .setAutoSelectEnabled(autoSelect)
        .setServerClientId(clientId)
        .build();

    GetCredentialRequest request = new GetCredentialRequest.Builder()
        .addCredentialOption(googleIdOption)
        .build();

    CancellationSignal cancellationSignal = new CancellationSignal();

    credentialManager.getCredentialAsync(
        activity,                        // Context
        request,                         // GetCredentialRequest
        cancellationSignal,              // or null
        directExecutor,                  // Executor
        new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
          @Override
          public void onResult(@NonNull GetCredentialResponse response) {
            try {
              Credential credential = response.getCredential();
              if (credential instanceof CustomCredential
                  && GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
                  .equals(credential.getType())) {
                GoogleIdTokenCredential idTokenCredential =
                    GoogleIdTokenCredential.createFrom(
                        ((CustomCredential) credential).getData());
                future.complete(idTokenCredential);
              } else {
                future.completeExceptionally(
                    new IllegalStateException(
                        "Credential is not a Google ID token credential"));
              }
            } catch (Exception e) {
              future.completeExceptionally(
                  new SignInRequiredException("Google Sign In required", e));
            }
          }

          @Override
          public void onError(@NonNull GetCredentialException e) {
            future.completeExceptionally(
                new SignInRequiredException("Google Sign In failed", e));
          }
        }
    );

    return future;
  }

  /**
   * Checks if a JWT token has expired.
   *
   * @param idToken the JWT token to check
   * @return true if the token has expired or cannot be parsed, false otherwise
   */
  private boolean isTokenExpired(@NonNull String idToken) {
    try {
      String[] parts = idToken.split("\\.");
      if (parts.length < 2) {
        return true;
      }
      String payloadJson =
          new String(Base64.decode(parts[1], Base64.URL_SAFE | Base64.NO_WRAP));
      JSONObject payload = new JSONObject(payloadJson);
      long expiration = payload.getLong("exp");
      long nowPlusSkew = System.currentTimeMillis() / 1000L + 5 * 60;
      return expiration < nowPlusSkew;
    } catch (Exception e) {
      Log.e(TAG, "Failed to parse ID token", e);
      return true;
    }
  }
}