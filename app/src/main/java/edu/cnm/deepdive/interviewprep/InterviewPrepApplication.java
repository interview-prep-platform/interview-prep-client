package edu.cnm.deepdive.interviewprep;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.interviewprep.service.GoogleSignInRepository;

/**
 * Initializes (in the {@link #onCreate()} method) application-level resources. This class
 * <strong>must</strong> be referenced in {@code AndroidManifest.xml}, or it will not be loaded and
 * used by the Android system.
 */
public class InterviewPrepApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this); //make Android apps visible with Chromium developer tools in the browser so that we can see the structure of view bojects and the database
    GoogleSignInRepository.setContext(this);
  }
}
