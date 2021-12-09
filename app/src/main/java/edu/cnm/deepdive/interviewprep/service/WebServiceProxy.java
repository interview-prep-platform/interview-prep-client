package edu.cnm.deepdive.interviewprep.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.interviewprep.BuildConfig;
import edu.cnm.deepdive.interviewprep.model.Question;
import io.reactivex.Single;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface WebServiceProxy {

//  @POST("games")
//  Single<GameWithGuesses> startGame(@Body Game game, @Header("Authorization") String bearerToken);
//
//  @POST("games/{gameId}/guesses")
//  Single<Guess> submitGuess(@Body Guess guess, @Path("gameId") String gameId,
//      @Header("Authorization") String bearerToken);

  @GET("questions")
  Single<List<Question>> getQuestions(@Header("Authorization") String bearerToken);

  @GET("questions/{questionId}")
  Single<Question> getQuestion(@Path("questionId") String questionId, @Header("Authorization") String bearerToken);

  static WebServiceProxy getInstance() {
    return InstanceHolder.INSTANCE;
  }

  class InstanceHolder {

    private static final WebServiceProxy INSTANCE;

    static {
      Gson gson = new GsonBuilder()
          .excludeFieldsWithoutExposeAnnotation()
          .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
          .create();
      HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
      interceptor.setLevel(Level.BODY);
      OkHttpClient client = new OkHttpClient.Builder()
          .addInterceptor(interceptor)
          .build();
      Retrofit retrofit = new Retrofit.Builder()
          .baseUrl(BuildConfig.BASE_URL)
          .addConverterFactory(GsonConverterFactory.create(gson))
          .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
          .client(client)
          .build();
      INSTANCE = retrofit.create(WebServiceProxy.class);
    }

  }

}
