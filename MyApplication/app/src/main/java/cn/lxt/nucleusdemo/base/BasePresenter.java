package cn.lxt.nucleusdemo.base;

import android.content.Context;

import java.util.Map;

import cn.lxt.nucleusdemo.retrofit.RequestUtil;
import nucleus5.presenter.RxPresenter;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class BasePresenter<VIEW> extends RxPresenter<VIEW> {
    protected Context context;
    private Map<String, Object> head;

    public Map<String, Object> getHead() {
        head = new RequestUtil().getHead(context);
        return head;
    }
}
