package top.genylife.realm.function;

import android.app.Application;

import top.genylife.realm.injector.components.AppComponent;
import top.genylife.realm.injector.components.DaggerAppComponent;
import top.genylife.realm.injector.module.AppModule;

/**
 * Created by wanqi on 2016/11/29.
 *
 * @since 1.0.0
 */

public class App extends Application {

    private AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
