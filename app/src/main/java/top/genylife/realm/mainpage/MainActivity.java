package top.genylife.realm.mainpage;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.genylife.realm.OnLoadMoreListener;
import top.genylife.realm.R;
import top.genylife.realm.databinding.ActivityMainBinding;
import top.genylife.realm.mainpage.model.News;
import top.genylife.realm.mainpage.viewmodel.ActivityMainVM;
import top.genylife.realm.retrofit.MyRetrofit;
import top.genylife.realm.retrofit.api.GetNews;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mBinding;

    ActivityMainVM mActivityMainVM;

    static class MyHandler extends Handler {

        private WeakReference<MainActivity> mActivity;

        MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if(mActivity.get() != null) {
                if(msg.obj instanceof News) {
                    ((NewsAdapter) (mActivity.get().mActivityMainVM.adapter)).addNews((News) msg.obj);
                }
                if(msg.what == -1) {
                    Toast.makeText(mActivity.get(), "已没有更多文章了！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mHandler = new MyHandler(this);
        setSupportActionBar(mBinding.includeContent.toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mBinding.drawLayout,
                mBinding.includeContent.toolbar,
                R.string.drawer_open,
                R.string.drawer_close);
        toggle.syncState();
        mBinding.drawLayout.addDrawerListener(toggle);
        mActivityMainVM = new ActivityMainVM();
        mActivityMainVM.adapter = new NewsAdapter();
        mActivityMainVM.layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mBinding.setVm(mActivityMainVM);


        mActivityMainVM.scrollListener = new OnLoadMoreListener() {
            @Override
            public void onLoadMore(int itemCount) {
                int page = (itemCount - 1) / 15 + 2;
                MyRetrofit.getInstance()
                        .getService(GetNews.class)
                        .getNews(page)
                        .enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, final Response<String> response) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        handleHtml(response.body());
                                    }
                                }).start();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Log.e("tag", t.toString());
                            }
                        });
            }
        };

        mBinding.navigationView.setItemIconTintList(null);

        MyRetrofit.getInstance()
                .getService(GetNews.class)
                .getNews()
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, final Response<String> response) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                handleHtml(response.body());
                            }
                        }).start();
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("tag", t.toString());
                    }
                });
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }


    private void handleHtml(String html) {
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
            String title = element.select("h5.news-headline:nth-child(1)").html();
            String content = element.select("div.excerpt > span.summary").html();
            String author = element.select("div.excerpt > div.info > span.meta-info>a").html();
            if(TextUtils.isEmpty(author)) {
                author = element.select("div.excerpt > div.info > span.meta-info").html();
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
}
