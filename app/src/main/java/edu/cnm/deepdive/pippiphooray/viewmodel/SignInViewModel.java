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

@HiltViewModel
public class SignInViewModel extends ViewModel {

  private final GoogleAuthRepository repository;
  private final MutableLiveData<GoogleIdTokenCredential> credential;
  private final MutableLiveData<Throwable> throwable;

  @Inject
  public SignInViewModel(GoogleAuthRepository repository) {
    this.repository = repository;
    credential = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
  }

  public LiveData<GoogleIdTokenCredential> getCredential() {
    return credential;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void signInQuickly(Activity activity) {
    throwable.setValue(null);
    CompletableFuture<?> future = repository.signInQuickly(activity);
    handleFuture(future);
  }

  public void signIn(Activity activity) {
    throwable.setValue(null);
    CompletableFuture<?> future = repository.signIn(activity);
    handleFuture(future);
  }

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