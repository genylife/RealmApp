package top.genylife.realm.util;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.inject.Inject;

import top.genylife.realm.function.model.News;

/**
 * Created by wanqi on 2016/12/1.
 *
 * @since 1.0.0
 */
public class HandlerHtml {

    Handler mHandler;

    @Inject
    public HandlerHtml(Handler handler) {
        mHandler = handler;
    }

    public void handlerNewsHtml(final String html) {
        if(html == null) {
            mHandler.sendEmptyMessage(-1);
            return;
        }
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("#columns > figure");
        if(elements.size() == 0) {
            mHandler.sendEmptyMessage(-1);
            return;
        }
        for (Element element : elements) {
            String url = element.select("div.article-block>a").attr("href");
            String title = element.select("h5.news-headline.col-xs-12").html();
            String content = element.select("span.summary.light-text.col-xs-12").html();
            String author = element.select("a.twitter-handle").html();
            if(TextUtils.isEmpty(author)) {
                author = element.select("span.meta-info").html();
            }
            String time = element.select("span.article-date").html();
            Message message = Message.obtain();
            message.obj = News.builder()
                    .time(time)
                    .title(title)
                    .content(content)
                    .author(author)
                    .url(url)
                    .build();
            mHandler.sendMessage(message);
        }
    }

    public static String handlerContentHtml(final String html) {
        if(html == null) {
            return "html content is null";
        }
        Document document = Jsoup.parse(html);
        Elements elements = document.select("div.container");
        elements.select("div.related-articles").remove();
        return elements.html();
    }
}
