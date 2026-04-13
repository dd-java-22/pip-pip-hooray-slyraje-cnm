package edu.cnm.deepdive.pippiphooray.viewmodel;

import android.app.Activity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.pippiphooray.service.repository.GoogleAuthRepository;
import java.util.concurrent.CompletableFuture;
import javax.inject.Inject;

/**
 * ViewModel for managing Google authentication and user sign-in/sign-out operations.
 */
@HiltViewModel
public class SignInViewModel extends ViewModel {

  private final GoogleAuthRepository repository;
  private final MutableLiveData<GoogleIdTokenCredential> credential;
  private final MutableLiveData<Throwable> throwable;

  /**
   * Constructs a SignInViewModel with the specified authentication repository.
   *
   * @param repository the repository for Google authentication operations
   */
  @Inject
  public SignInViewModel(GoogleAuthRepository repository) {
    this.repository = repository;
    credential = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
  }

  /**
   * Gets the current user credential as LiveData.
   *
   * @return a LiveData containing the Google ID token credential, or null if not signed in
   */
  public LiveData<GoogleIdTokenCredential> getCredential() {
    return credential;
  }

  /**
   * Gets any errors from authentication operations as LiveData.
   *
   * @return a LiveData containing any throwable exceptions from auth operations
   */
  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  /**
   * Attempts to sign in quickly using cached credentials.
   *
   * @param activity the activity context for the sign-in operation
   */
  public void signInQuickly(Activity activity) {
    throwable.setValue(null);
    CompletableFuture<?> future = repository.signInQuickly(activity);
    handleFuture(future);
  }

  /**
   * Initiates a sign-in flow with user interaction.
   *
   * @param activity the activity context for the sign-in operation
   */
  public void signIn(Activity activity) {
    throwable.setValue(null);
    CompletableFuture<?> future = repository.signIn(activity);
    handleFuture(future);
  }

  /**
   * Signs out the current user.
   */
  public void signOut() {
    throwable.setValue(null);
    repository
        .signOut()
        .thenRun(() -> credential.postValue(null))
        .exceptionally((t) -> {
          throwable.postValue(t);
          return null;
        });
  }

  /**
   * Handles the completion of an authentication future operation.
   *
   * @param future the CompletableFuture from an auth operation
   */
  @SuppressWarnings("unchecked")
  private void handleFuture(CompletableFuture<?> future) {
    ((CompletableFuture<GoogleIdTokenCredential>) future)
        .thenAccept(credential::postValue)
        .exceptionally((t) -> {
          throwable.postValue(t);
          credential.postValue(null);
          return null;
        });
  }

}