package edu.cnm.deepdive.interviewprep.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import edu.cnm.deepdive.interviewprep.BuildConfig;
import edu.cnm.deepdive.interviewprep.model.History;
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
import retrofit2.http.Query;

/**
 * This class lists REST endpoints that are used to access to the server.
 */
public interface WebServiceProxy {

  /**
   * This method defines the behavior of a GET request to the URL /interviewprep/questions/.
   *   It returns all questions from the server.
   * @param bearerToken  Token in the form of a bearer token used to authenticate with the server.
   *    *
   * @return A {@link List} of {@link Question}s in the form of a ReactiveX {@link Single}.
   */
  @GET("questions")
  Single<List<Question>> getQuestions(@Header("Authorization") String bearerToken);

  @GET("questions")
  Single<List<Question>> getAllQuestionsWithOrWithoutAnswers(@Query("answered") boolean answered, @Header("Authorization") String bearerToken);

  /**
   * This method defines the behavior of a GET request to the URL /interviewprep/questions/random.
   *   It returns a random question from the server.
   * @param bearerToken  Token in the form of a bearer token used to authenticate with the server.
   *    *
   * @return A random {@link Question} in the form of a ReactiveX {@link Single}.
   */
  @GET("questions/random")
  Single<Question> getRandomQuestion(@Header("Authorization") String bearerToken);
  Single<List<Question>> getRandomQuestions(@Query("quizlength") int quizlength,@Header("Authorization") String bearerToken);

  /**
   * This method defines the behavior of a GET request to the URL /interviewprep/questions/questionId.
   *   It returns a single question from the server.
   * @param bearerToken  Token in the form of a bearer token used to authenticate with the server.
   * @param questionId A question id in the form of a universally unique identifier.
   * @return A single {@link Question} in the form of a ReactiveX {@link Single}.
   */
  @GET("questions/{questionId}")
  Single<Question> getQuestion(@Path("questionId") UUID questionId,
      @Header("Authorization") String bearerToken);

  /**
   * This method defines the behavior of a PUT request to the URL /interviewprep/questions/questionId.
   *   It sends and returns an updated question from the server.
   * @param bearerToken  Token in the form of a bearer token used to authenticate with the server.
   * @param questionId A question id in the form of a universally unique identifier.
   * @param question The updated {@link Question} object.
   * @return An updated {@link Question} in the form of a ReactiveX {@link Single}.
   */
  @PUT("questions/{questionId}")
  Single<Question> updateQuestion(@Path("questionId") UUID questionId, @Body Question question,
      @Header("Authorization") String bearerToken);

  /**
   * This method defines the behavior of a POST request to the URL /interviewprep/questions/.
   *   It sends a newly created question to the server.
   * @param bearerToken  Token in the form of a bearer token used to authenticate with the server.
   * @param question The newly created {@link Question} object.
   * @return The newly created {@link Question} in the form of a ReactiveX {@link Single}.
   */
  @POST("questions")
  Single<Question> createQuestion(@Body Question question,
      @Header("Authorization") String bearerToken);

  /**
   * This method defines the behavior of a DELETE request to the URL /interviewprep/questions/questionId.
   *   It deletes a specified question from the server.
   * @param bearerToken  Token in the form of a bearer token used to authenticate with the server.
   * @param questionId A question id in the form of a universally unique identifier.
   * @return A ReactiveX {@link Completable}.
   */
  @DELETE("questions/{questionId}")
  Completable deleteQuestion(@Path("questionId") UUID questionId,
      @Header("Authorization") String bearerToken);


  @GET("questions/{questionId}/answers")
  Single<List<History>> getHistories(@Path("questionId") UUID questionId, @Header("Authorization") String bearerToken);

  @POST("questions/{questionId}/answers")
  Single<History> createHistory(@Body History history, @Path("questionId") UUID questionId,
      @Header("Authorization") String bearerToken);

  @DELETE("questions/{questionId}/answers/{answerId}")
  Completable deleteHistory(@Path("questionId") UUID questionId, @Path("answerId") UUID answerId,
      @Header("Authorization") String bearerToken);


  /**
   * Returns a single instance of the class instance holder.
   * @return A single instance of the class instance holder.
   */
  static WebServiceProxy getInstance() {
    return InstanceHolder.INSTANCE;
  }

  /**
   * This class creates an instance of Web Proxy Server that creates a JSON object out of the question object
   * and logs HTTP traffic and builds the actual HTTP requests.
   */
  class InstanceHolder {

    private static final WebServiceProxy INSTANCE;

    /**
     * Creates a JSON object out of the question object and logs HTTP traffic and builds the actual HTTP requests.
     */
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
