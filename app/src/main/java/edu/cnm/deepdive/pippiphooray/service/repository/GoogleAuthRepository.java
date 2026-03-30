package edu.cnm.deepdive.pippiphooray.service.repository;

import android.app.Activity;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import java.util.concurrent.CompletableFuture;

public interface GoogleAuthRepository {

  CompletableFuture<GoogleIdTokenCredential> signInQuickly(Activity activity);

  CompletableFuture<GoogleIdTokenCredential> signIn(Activity activity);

  CompletableFuture<GoogleIdTokenCredential> refreshToken(
      Activity activity, GoogleIdTokenCredential credential);

  CompletableFuture<Void> signOut();

  class SignInRequiredException extends RuntimeException {

    public SignInRequiredException(String message, Throwable cause) {
      super(message, cause);
    }

  }

}