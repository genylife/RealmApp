package top.genylife.realm.injector.components;

import javax.inject.Singleton;

import dagger.Component;
import top.genylife.realm.injector.module.AppModule;
import top.genylife.realm.mvp.App;

/**
 * Created by wanqi on 2016/11/29.
 *
 * @since 1.0.0
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {

    App app();
}
