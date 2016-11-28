package top.genylife.realm.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import top.genylife.realm.retrofit.converter.StringConverterFactory;

/**
 * Created by wanqi on 2016/9/8.
 *
 * @since 1.0.0
 */
public class MyRetrofit {

    private static final String BASE_URL = "https://realm.io/cn/";

    private final Retrofit mRetrofit;

    public static MyRetrofit getInstance() {
        return instance;
    }

    private static MyRetrofit instance = new MyRetrofit();

    private MyRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(new StringConverterFactory())
                .build();
    }

    public <T> T getService(final Class<T> service) {
        return getInstance().mRetrofit.create(service);
    }

}
