package top.genylife.realm.function.base;

import android.support.v7.app.AppCompatActivity;

import top.genylife.realm.injector.components.AppComponent;
import top.genylife.realm.function.App;

/**
 * Created by wanqi on 2016/12/1.
 *
 * @since 1.0.0
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }

}
