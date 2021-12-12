package edu.cnm.deepdive.interviewprep.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.interviewprep.BuildConfig;
import edu.cnm.deepdive.interviewprep.model.Question;
import io.reactivex.Completable;
import io.reactivex.Single;
import java.util.List;
import java.util.UUID;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface WebServiceProxy {

  @GET("questions")
  Single<List<Question>> getQuestions(@Header("Authorization") String bearerToken);

  @GET("questions/random")
  Single<Question> getRandomQuestion(@Header("Authorization") String bearerToken);

  @GET("questions/{questionId}")
  Single<Question> getQuestion(@Path("questionId") UUID questionId,
      @Header("Authorization") String bearerToken);

  @PUT("questions/{questionId}")
  Single<Question> updateQuestion(@Body Question question,
      @Header("Authorization") String bearerToken);

  @POST("questions")
  Single<Question> createQuestion(@Body Question question,
      @Header("Authorization") String bearerToken);

  @DELETE("questions/{questionId}")
  Completable deleteQuestion(@Path("questionId") UUID questionId,
      @Header("Authorization") String bearerToken);

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
