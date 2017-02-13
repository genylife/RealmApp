package top.genylife.realm.function.model;

import com.google.auto.value.AutoValue;

import java.io.Serializable;

import top.genylife.realm.Transform;
import top.genylife.realm.function.main.NewsCardVM;

/**
 * Created by wanqi on 2016/11/26.
 *
 * @since 1.0.0
 */
@AutoValue
public abstract class News implements Transform<NewsCardVM>,Serializable {

    public abstract String author();

    public abstract String title();

    public abstract String content();

    public abstract String time();

    public abstract String url();

    public static News.Builder builder() {
        return new AutoValue_News.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {

        public abstract News.Builder author(String author);

        public abstract News.Builder title(String title);

        public abstract News.Builder content(String content);

        public abstract News.Builder time(String time);

        public abstract News.Builder url(String url);

        public abstract News build();
    }

    @Override
    public NewsCardVM transform() {
        return NewsCardVM.Builder()
                .author(author())
                .content(content())
                .time(time())
                .title(title())
                .build();
    }
}
