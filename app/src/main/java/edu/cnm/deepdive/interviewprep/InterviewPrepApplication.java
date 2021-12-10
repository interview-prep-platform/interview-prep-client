package edu.cnm.deepdive.interviewprep;

import android.app.Application;
import com.facebook.stetho.Stetho;
import edu.cnm.deepdive.interviewprep.service.GoogleSignInRepository;

public class InterviewPrepApplication extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    Stetho.initializeWithDefaults(this); //make Android apps visible with Chromium developer tools in the browser so that we can see the structure of view bojects and the database
    GoogleSignInRepository.setContext(this);
  }
}
