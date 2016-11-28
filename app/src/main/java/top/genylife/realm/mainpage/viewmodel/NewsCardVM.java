package top.genylife.realm.mainpage.viewmodel;

/**
 * Created by wanqi on 2016/11/25.
 *
 * @since 1.0.0
 */
public class NewsCardVM {

    public String author;
    public String title;
    public String content;
    public String time;

    public static NewsCardVM.Builder Builder() {
        return new Builder();
    }

    public static final class Builder {

        NewsCardVM mCard = new NewsCardVM();

        public Builder title(String title) {
            mCard.title = title;
            return this;
        }

        public Builder author(String author) {
            mCard.author = author;
            return this;
        }

        public Builder content(String content) {
            mCard.content = content;
            return this;
        }

        public Builder time(String time) {
            mCard.time = time;
            return this;
        }

        public NewsCardVM build() {
            return mCard;
        }
    }
}
