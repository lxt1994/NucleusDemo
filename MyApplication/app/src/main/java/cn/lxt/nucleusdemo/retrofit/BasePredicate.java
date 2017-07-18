package cn.lxt.nucleusdemo.retrofit;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import cn.lxt.nucleusdemo.response.BaseResponse;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Predicate;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class BasePredicate<R extends BaseResponse> implements Predicate<R> {
    private Context context;
    private String error;
    private boolean finishOnError = false;

    public BasePredicate(Context context, @NonNull String error) {
        this.context = context;
        this.error = error;
    }

    public BasePredicate(Context context, String error, boolean finishOnError) {
        this.context = context;
        this.error = error;
        this.finishOnError = finishOnError;
    }

    @Override
    public boolean test(@NonNull final R r) throws Exception {
        boolean result = false;
        if(r != null){
            if(r.getCode() == 0){
                result = true;
            }
            else if(r.getCode() == 23003){
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //这边可以自己做一些本地化操作
                        if(finishOnError){
                            ((Activity) context).finish();
                        }
                    }
                });
            }
            else {
                if(!TextUtils.isEmpty(error))
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, !TextUtils.isEmpty(r.getMsg()) ? r.getMsg() : error, Toast.LENGTH_SHORT);
                        }
                    });
                if(finishOnError){
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ((Activity) context).finish();
                        }
                    });
                }
            }
        }
        else {
            if(!TextUtils.isEmpty(error))
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, r != null && !TextUtils.isEmpty(r.getMsg()) ? r.getMsg() : error, Toast.LENGTH_SHORT);
                    }
                });
            if(finishOnError){
                ((Activity)context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ((Activity) context).finish();
                    }
                });
            }
        }
        return result;
    }
}
