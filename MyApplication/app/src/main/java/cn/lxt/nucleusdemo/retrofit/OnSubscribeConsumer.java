package cn.lxt.nucleusdemo.retrofit;

import android.content.Context;
import android.text.TextUtils;

import cn.lxt.nucleusdemo.base.BaseActivity;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class OnSubscribeConsumer implements Consumer<Disposable> {
    private Context context;
    private String msg;
    private boolean cancelbale = false;

    public OnSubscribeConsumer(Context context, String msg) {
        this.context = context;
        this.msg = msg;
    }

    public OnSubscribeConsumer(Context context, String msg, boolean cancelbale) {
        this.context = context;
        this.msg = msg;
        this.cancelbale = cancelbale;
    }

    @Override
    public void accept(@NonNull Disposable o) throws Exception {
        if (context != null && context instanceof BaseActivity) {
            if (!TextUtils.isEmpty(msg)) {
                ((BaseActivity) context).showProgressDialog(true, msg, cancelbale);
            }
        }
    }
}
