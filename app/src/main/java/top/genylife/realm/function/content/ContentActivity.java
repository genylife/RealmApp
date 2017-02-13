package top.genylife.realm.function.content;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import top.genylife.realm.R;
import top.genylife.realm.function.base.BaseActivity;

/**
 * Created by wanqi on 2016/12/13.
 *
 * @since 1.0.0
 */

public class ContentActivity extends BaseActivity {

    WebView mContentView;
    Toolbar mToolbar;

    private static final String HTML_WARP = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "    <meta charset=\"utf-8\">\n" +
            "    <meta name=\"content-type\" content=\"text/html; charset=utf-8\">\n" +
            "\t<meta content=\"IE=edge,chrome=1\" http-equiv=\"X-UA-Compatible\">\n" +
            "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
            "\t<link href=\"https://realm.io/assets/css/site.css\" rel=\"stylesheet\">\n" +
            "\t<link href=\"https://realm.io/assets/css/vendors/bootstrap.css\" rel=\"stylesheet\">\n" +
            "\t<title>123</title>\n" +
            "</head>\n" +
            "<body>\n" +
            "<div class=\"container\">" +
            "%s" +
            "</div>\n" +
            "</body>\n" +
            "</html>";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        mContentView = (WebView) findViewById(R.id.webview);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Intent intent = getIntent();
        int index = intent.getIntExtra("FUNCTION", -1);
        String content = intent.getStringExtra("CONTENT");
        String title = intent.getStringExtra("TITLE");
        setTitle(title);
        if(index == -1) {
            //TODO：出错处理
        } else {
            if(index == 5) {
                content = String.format(HTML_WARP, content);
                mContentView.loadDataWithBaseURL(null, content, "text/html", "UTF-8", null);
            } else {
                mContentView.loadUrl(content);
            }
        }

        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }*/
    }
}
