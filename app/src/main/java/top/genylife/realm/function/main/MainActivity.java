package top.genylife.realm.function.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.lang.ref.WeakReference;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Actions;
import rx.internal.util.ActionSubscriber;
import rx.schedulers.Schedulers;
import top.genylife.realm.R;
import top.genylife.realm.databinding.ActivityMainBinding;
import top.genylife.realm.function.App;
import top.genylife.realm.function.base.BaseActivity;
import top.genylife.realm.function.content.ContentActivity;
import top.genylife.realm.function.model.News;
import top.genylife.realm.injector.components.DaggerMainActivityComponent;
import top.genylife.realm.injector.module.MainActivityModule;
import top.genylife.realm.listener.OnLoadMoreListener;
import top.genylife.realm.listener.OnRecyclerViewItemClick;
import top.genylife.realm.retrofit.MyRetrofit;
import top.genylife.realm.retrofit.api.GetContent;
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

    /**
     * 初始化Toolbar,DrawLayout,设置侧滑菜单点击事件
     */
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
        mBinding.navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.product_platform:
                        jumpToContentActivity(Menu.PRODUCT_PALTFORM, null, "realm mobile platform");
                        break;
                    case R.id.product_database:
                        jumpToContentActivity(Menu.PRODUCT_DATABASE, null, "realm mobile database");
                        break;
                    case R.id.news:
                        break;
                    case R.id.docs:
                        jumpToContentActivity(Menu.DOCS, null, "文档");
                        break;
                    case R.id.price:
                        jumpToContentActivity(Menu.PRICE, null, "价格");
                        break;
                    case R.id.contact:
                        jumpToContentActivity(Menu.CONTACT, null, "联系我们");
                        break;
                }
                mBinding.drawLayout.closeDrawers();
                return true;
            }
        });
    }

    /**
     * 跳转到内容界面
     *
     * @param menu
     * @param newsContent
     */
    private void jumpToContentActivity(Menu menu, @Nullable String newsContent, String title) {
        Intent intent = new Intent(MainActivity.this, ContentActivity.class);
        intent.putExtra("FUNCTION", menu.index());
        intent.putExtra("TITLE", title);
        if(menu.index() == Menu.NEWS.index()) {
            intent.putExtra("CONTENT", newsContent);
        } else {
            intent.putExtra("CONTENT", menu.getValue());
        }
        startActivity(intent);
    }

    /**
     * 初始化注入器，设置Item点击事件，获取第一页得数据
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerMainActivityComponent.builder()
                .appComponent(getAppComponent())
                .mainActivityModule(new MainActivityModule(this))
                .build()
                .inject(this);

        initToolbar();
        mActivityMainVM.adapter.setOnItemClick(new OnRecyclerViewItemClick() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, final View view, final RecyclerView.ViewHolder holder) {
                String url = ((NewsViewHolder) holder).getNews().url();
                int from = url.indexOf("/", 5) + 1;
                int to = url.lastIndexOf("/");
                MyRetrofit.getInstance()
                        .getService(GetContent.class)
                        .getContent(url.substring(from, to))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Action1<String>() {
                                    @Override
                                    public void call(String s) {
                                        String title = ((NewsViewHolder) holder).getNews().title();
                                        jumpToContentActivity(Menu.NEWS, HandlerHtml.handlerContentHtml(s), title);
                                    }
                                },
                                new Action1<Throwable>() {
                                    @Override
                                    public void call(Throwable throwable) {
                                        Toast.makeText(view.getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
            }
        });
        mBinding.setVm(mActivityMainVM);

        final ActionSubscriber<String> subscriber = new ActionSubscriber<>(
                new Action1<String>() {
                    @Override
                    public void call(String s) {
                        mHtmlHandler.handlerNewsHtml(s);
                    }
                },
                new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                }, Actions.empty());


        mActivityMainVM.scrollListener = new OnLoadMoreListener() {
            @Override
            public void onLoadMore(int itemCount) {
                int page = (itemCount - 1) / 15 + 2;
                getNews(page).subscribe(subscriber);
            }
        };

        getNews(1).subscribe(subscriber);
    }

    /**
     * 根据page获取该页得内容
     *
     * @param page
     * @return
     */
    private Observable<String> getNews(int page) {
        Observable<String> observable;
        GetNews service = MyRetrofit.getInstance().getService(GetNews.class);
        if(page < 2) observable = service.getNews();
        else observable = service.getNews(page);
        return observable.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread());
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
