package top.genylife.realm.mvp.main;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.Log;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.genylife.realm.R;
import top.genylife.realm.databinding.ActivityMainBinding;
import top.genylife.realm.injector.components.DaggerMainActivityComponent;
import top.genylife.realm.injector.module.MainActivityModule;
import top.genylife.realm.listener.OnLoadMoreListener;
import top.genylife.realm.mvp.App;
import top.genylife.realm.mvp.base.BaseActivity;
import top.genylife.realm.mvp.model.News;
import top.genylife.realm.retrofit.MyRetrofit;
import top.genylife.realm.retrofit.api.GetNews;
import top.genylife.realm.util.HandlerHtml;

public class MainActivity extends BaseActivity {

    @Inject
    ActivityMainBinding mBinding;

    @Inject
    ActivityMainVM mActivityMainVM;

    public static class MyHandler extends Handler {

        private WeakReference<MainActivity> mActivity;

        public MyHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if(mActivity.get() != null) {
                if(msg.obj instanceof News) {
                    ((mActivity.get().mActivityMainVM.adapter)).addNews((News) msg.obj);
                }
                if(msg.what == -1) {
                    Toast.makeText(mActivity.get(), "已没有更多文章了！", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Inject
    Handler mHandler;

    @Inject
    HandlerHtml mHtmlHandler;

    @Inject
    App mApp;

    Callback<String> mStringCallback = new Callback<String>() {
        @Override
        public void onResponse(Call<String> call, final Response<String> response) {
            mHtmlHandler.asyncHandlerHtml(response.body());
        }

        @Override
        public void onFailure(Call<String> call, Throwable t) {
            Log.e("tag", t.toString());
        }
    };

    private void initToolbar() {
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
        mBinding.navigationView.setItemIconTintList(null);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainActivityComponent.builder()
                .appComponent(getAppComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);

        initToolbar();
        mBinding.setVm(mActivityMainVM);


        mActivityMainVM.scrollListener = new OnLoadMoreListener() {
            @Override
            public void onLoadMore(int itemCount) {
                int page = (itemCount - 1) / 15 + 2;
                MyRetrofit.getInstance()
                        .getService(GetNews.class)
                        .getNews(page)
                        .enqueue(mStringCallback);
            }
        };

        MyRetrofit.getInstance()
                .getService(GetNews.class)
                .getNews()
                .enqueue(mStringCallback);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
