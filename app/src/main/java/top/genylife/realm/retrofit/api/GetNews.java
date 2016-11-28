package top.genylife.realm.retrofit.api;

import android.support.annotation.IntRange;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by wanqi on 2016/11/25.
 *
 * @since 1.0.0
 */

public interface GetNews {

    @GET("news")
    Call<String> getNews();

    @GET("news/cn/page/{page}")
    Call<String> getNews(@IntRange(from = 2, to = 10) @Path("page") int page);
}
