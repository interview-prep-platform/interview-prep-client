package edu.cnm.deepdive.interviewprep.service;

import android.app.Application;
import android.content.Intent;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import edu.cnm.deepdive.interviewprep.BuildConfig;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * Uses Google Sign In services to validate and authenticate a User
 * and creates a Bearer token that can be used to
 * validate and authenticate a user to the application.
 */
public class GoogleSignInRepository {

  private static final String BEARER_TOKEN_FORMAT = "Bearer %s";

  private static Application context;

  private final GoogleSignInClient client;

  private GoogleSignInAccount account;

  private GoogleSignInRepository() {
    GoogleSignInOptions options = new GoogleSignInOptions.Builder()
        .requestEmail()
        .requestId() //oauth key from google
        .requestProfile()
        .requestIdToken(BuildConfig.CLIENT_ID)//bearer token that we need
        .build();
    client = GoogleSignIn.getClient(context, options);
  }

  /**
   * Sets the Application context to the local context variable.
   * @param context an Application context.
   */
  public static void setContext(Application context) {
    GoogleSignInRepository.context = context;
  }


  /**
   * Loads a GoogleSignInRepository into memory.
   * @return an Instance of the GoogleSignInRepository.
   */
  public static GoogleSignInRepository getInstance() {
    return InstanceHolder.INSTANCE; //here is where this class gets loaded into memory, not when GoogleSignIn class is loaded
  }

  /**
   * Refreshes the validation and authentication of the account.  It performs this refresh in the background.
   * @return a reactivex {@link Single} of type {@link GoogleSignInAccount}.
   */
  public Single<GoogleSignInAccount> refresh() {
    return Single
        .create((SingleOnSubscribe<GoogleSignInAccount>) (emitter) ->
            client
                .silentSignIn()
                .addOnSuccessListener(this::setAccount)
                .addOnSuccessListener(emitter::onSuccess)
                .addOnFailureListener(emitter::onError)
        )
        .observeOn(Schedulers.io());
  }

  /**
   * Refreshes the Bearer Token for this application.
   * @return a reactivex {@link Single} of type {@link String}.
   */
  public Single<String> refreshBearerToken() {
    return refresh()
        .map(this::getBearerToken);
  }

  /**
   * Begins the application sign in process.
   * @param launcher an {@link ActivityResultLauncher} object of type {@link Intent}.
   */
  public void startSignIn(ActivityResultLauncher<Intent> launcher) {
    launcher.launch(client.getSignInIntent());
  }

  /**
   * Signs a user into the application using a User Google account from Google Sign In services.
   * @param result the result of the SignIn process
   * @return a reactivex {@link Single} of type {@link GoogleSignInAccount}
   */
  public Single<GoogleSignInAccount> completeSignIn(ActivityResult result) {
    return Single
        .create((SingleEmitter<GoogleSignInAccount> emitter) -> {
          try {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
            GoogleSignInAccount account = task.getResult(ApiException.class);
            setAccount(account);
            emitter.onSuccess(account);
          } catch (ApiException e) {
            emitter.onError(e);
          }
        })
        .observeOn(Schedulers.io());
  }

  /**
   * Signs the User out of their Google account.
   * @return a reactivex {@link Completable}.
   */
  public Completable signOut() {
    return Completable
        .create((emitter) ->
            client
                .signOut() //sign out gives us a void = null, which we don't care about
                .addOnSuccessListener((ignored) -> emitter.onComplete())
                .addOnCompleteListener((ignored) -> setAccount(null))
                .addOnFailureListener(emitter::onError)
        )
        .subscribeOn(Schedulers.io());
  }

  private void setAccount(GoogleSignInAccount account) {
    this.account = account;
    if (account != null) {
      Log.d(getClass().getSimpleName(),
          account.getIdToken() != null ? getBearerToken(account) : "none");
    }
  }

  private String getBearerToken(GoogleSignInAccount account) {
    return String.format(BEARER_TOKEN_FORMAT, account.getIdToken());
  }

  private static class InstanceHolder {
    private static final GoogleSignInRepository INSTANCE = new GoogleSignInRepository();
  }
}
