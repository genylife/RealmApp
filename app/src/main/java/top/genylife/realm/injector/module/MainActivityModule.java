package top.genylife.realm.injector.module;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;

import dagger.Module;
import dagger.Provides;
import top.genylife.realm.R;
import top.genylife.realm.databinding.ActivityMainBinding;
import top.genylife.realm.injector.PerActivity;
import top.genylife.realm.mvp.main.MainActivity;

/**
 * Created by wanqi on 2016/12/1.
 *
 * @since 1.0.0
 */
@PerActivity
@Module
public class MainActivityModule {

    private final MainActivity mMainActivity;

    public MainActivityModule(MainActivity activity) {
        mMainActivity = activity;
    }

    @Provides
    Activity provideActivity() {
        return mMainActivity;
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager() {
        return new LinearLayoutManager(mMainActivity,LinearLayoutManager.VERTICAL, false);
    }

    @Provides
    Handler provideHandler() {
        return new MainActivity.MyHandler(mMainActivity);
    }

    @Provides
    ActivityMainBinding provideActivityMainBinding() {
        return DataBindingUtil.setContentView(mMainActivity, R.layout.activity_main);
    }
}
