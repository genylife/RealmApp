package top.genylife.realm.retrofit.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by wanqi on 2016/12/9.
 *
 * @since 1.0.0
 */

public interface GetContent {

    @GET("news/{url}")
    Observable<String> getContent(@Path("url") String url);

}
