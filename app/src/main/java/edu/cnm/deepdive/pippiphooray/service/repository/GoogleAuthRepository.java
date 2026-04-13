package edu.cnm.deepdive.pippiphooray.service.repository;

import android.app.Activity;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import java.util.concurrent.CompletableFuture;

/**
 * Repository interface for Google authentication operations.
 */
public interface GoogleAuthRepository {

  /**
   * Attempts a quick sign-in using cached credentials without user interaction.
   *
   * @param activity the activity context for the sign-in operation
   * @return a CompletableFuture containing the Google ID token credential
   */
  CompletableFuture<GoogleIdTokenCredential> signInQuickly(Activity activity);

  /**
   * Initiates a full sign-in flow requiring user interaction.
   *
   * @param activity the activity context for the sign-in operation
   * @return a CompletableFuture containing the Google ID token credential
   */
  CompletableFuture<GoogleIdTokenCredential> signIn(Activity activity);

  /**
   * Refreshes an existing Google ID token credential.
   *
   * @param activity the activity context for the refresh operation
   * @param credential the credential to refresh
   * @return a CompletableFuture containing the refreshed credential
   */
  CompletableFuture<GoogleIdTokenCredential> refreshToken(
      Activity activity, GoogleIdTokenCredential credential);

  /**
   * Signs out the current user.
   *
   * @return a CompletableFuture that completes when the sign-out finishes
   */
  CompletableFuture<Void> signOut();

  /**
   * Exception thrown when sign-in is required but not completed.
   */
  class SignInRequiredException extends RuntimeException {

    /**
     * Constructs a SignInRequiredException with the specified message and cause.
     *
     * @param message the detail message
     * @param cause the cause of the exception
     */
    public SignInRequiredException(String message, Throwable cause) {
      super(message, cause);
    }

  }

}