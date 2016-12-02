package top.genylife.realm.mvp.base;

import android.support.v7.app.AppCompatActivity;

import top.genylife.realm.injector.components.AppComponent;
import top.genylife.realm.mvp.App;

/**
 * Created by wanqi on 2016/12/1.
 *
 * @since 1.0.0
 */

public class BaseActivity extends AppCompatActivity {

    protected AppComponent getAppComponent() {
        return ((App) getApplication()).getAppComponent();
    }

}
