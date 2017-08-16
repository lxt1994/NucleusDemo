package cn.lxt.nucleusdemo.retrofit;

import android.content.Context;

import cn.lxt.nucleusdemo.base.BaseActivity;
import io.reactivex.functions.Action;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class FinallyAction implements Action {
    private Context context;

    public FinallyAction(Context context) {
        this.context = context;
    }

    @Override
    public void run() throws Exception {
        if (context != null && context instanceof BaseActivity) {
            ((BaseActivity) context).showProgressDialog(false, "", false);
        }
    }
}
