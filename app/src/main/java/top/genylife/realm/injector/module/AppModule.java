package top.genylife.realm.injector.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import top.genylife.realm.function.App;

/**
 * Created by wanqi on 2016/11/29.
 *
 * @since 1.0.0
 */
@Module
public class AppModule {

    private final App application;

    public AppModule(App application) {
        this.application = application;
    }

    @Provides
    @Singleton
    App provideApplicationContext() {
        return this.application;
    }




}

