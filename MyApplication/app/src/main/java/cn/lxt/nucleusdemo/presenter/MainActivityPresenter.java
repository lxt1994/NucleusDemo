package cn.lxt.nucleusdemo.presenter;

import android.os.Bundle;

import java.util.HashMap;

import cn.lxt.nucleusdemo.api.Service;
import cn.lxt.nucleusdemo.base.BasePresenter;
import cn.lxt.nucleusdemo.bean.LoginBean;
import cn.lxt.nucleusdemo.response.LoginResponse;
import cn.lxt.nucleusdemo.retrofit.BasePredicate;
import cn.lxt.nucleusdemo.retrofit.HttpUtil;
import cn.lxt.nucleusdemo.retrofit.LoadingTransformer;
import cn.lxt.nucleusdemo.view.activity.MainActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import nucleus5.presenter.Factory;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class MainActivityPresenter extends BasePresenter<MainActivity> {
    private final int REQUEST_USER_LOGIN_ID = 0;
    private String name, psw;

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableLatestCache(REQUEST_USER_LOGIN_ID, new Factory<Observable<LoginBean>>() {
            @Override
            public Observable<LoginBean> create() {
                //这里用自己的rxjava+retrofit做联网请求和一些逻辑处理
                HashMap<String, String> map = new HashMap<>();
                map.put("mobile", name);
                map.put("loginPassword", psw);
                return HttpUtil.getRetrofit(context).create(Service.class).Login(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .compose(new LoadingTransformer<LoginResponse>(context, "正在登录，请稍候。。。", true))
                        .filter(new BasePredicate<LoginResponse>(context, "登录失败"))
                        .map(new Function<LoginResponse, LoginBean>() {
                            @Override
                            public LoginBean apply(@NonNull LoginResponse loginResponse) throws Exception {return loginResponse.getData();
                            }
                        });
            }
        }, new BiConsumer<MainActivity, LoginBean>() {
            @Override
            public void accept(MainActivity mainActivity, LoginBean loginBean) throws Exception {
                //请求成功，返回数据
                mainActivity.loginSuccess(loginBean);
                stop(REQUEST_USER_LOGIN_ID);
            }
        }, new BiConsumer<MainActivity, Throwable>() {
            @Override
            public void accept(MainActivity mainActivity, Throwable throwable) throws Exception {
                //请求失败，走的onError方法
                mainActivity.loginFailed(throwable.getMessage());
                stop(REQUEST_USER_LOGIN_ID);
            }
        });
    }

    public void login(MainActivity mainActivity, String s, String lxt123) {
        this.context = mainActivity;
        this.name = s;
        this.psw = lxt123;
        start(REQUEST_USER_LOGIN_ID);
    }
}
