package cn.lxt.nucleusdemo.retrofit;

import android.content.Context;

import java.util.List;

import cn.lxt.nucleusdemo.constants.IpConstants;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by CN-11 on 2017/5/2.
 */

public class CookiesManager implements CookieJar {
    private PersistentCookieStore cookieStore;

    public CookiesManager(Context context) {
        if (cookieStore == null)
            cookieStore = new PersistentCookieStore(context);
    }

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (url.toString().contains("/user/login"))
            if (cookies != null && cookies.size() > 0) {
                for (Cookie item : cookies) {
                    cookieStore.add(url, item);
                }
            }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }

    public boolean updateCookie() {
        List<Cookie> cookies = cookieStore.get(HttpUrl.parse(IpConstants.BASE_URL));
        if (cookies == null || cookies.isEmpty()) {
            return true;
        }
        for (Cookie cookie : cookies) {
            if (System.currentTimeMillis() - cookie.expiresAt() >= 0) {
                return true;
            }
        }
        return false;
    }
}
