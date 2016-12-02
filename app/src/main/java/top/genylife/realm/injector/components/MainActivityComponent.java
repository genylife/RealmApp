package top.genylife.realm.injector.components;

import android.app.Activity;

import dagger.Component;
import top.genylife.realm.injector.PerActivity;
import top.genylife.realm.injector.module.MainActivityModule;
import top.genylife.realm.mvp.main.MainActivity;

/**
 * Created by wanqi on 2016/12/1.
 *
 * @since 1.0.0
 */
@PerActivity
@Component(dependencies = AppComponent.class, modules = {MainActivityModule.class})
public interface MainActivityComponent {

    void inject(MainActivity mainActivity);

    Activity activity();

}
