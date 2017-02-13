package top.genylife.realm.function.main;

/**
 * Created by wanqi on 2017/2/13.
 *
 * @since 1.0.0
 */

public enum Menu {
    PRODUCT_PALTFORM("https://realm.io/cn/products/realm-mobile-platform/", 0),
    PRODUCT_DATABASE("https://realm.io/cn/products/realm-mobile-database/", 1),
    PRICE("https://realm.io/cn/pricing/", 2),
    DOCS("https://realm.io/cn/docs/", 3),
    CONTACT("https://realm.io/cn/contact/", 4),
    NEWS("", 5);


    private String mValue;
    private int mIndex;

    Menu(String value, int index) {
        mValue = value;
        mIndex = index;
    }

    public String getValue() {
        return mValue;
    }

    public int index() {
        return mIndex;
    }
}
