package cn.lxt.nucleusdemo.retrofit;

import android.content.Context;

import cn.lxt.nucleusdemo.response.BaseResponse;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class LoadingTransformer<T extends BaseResponse> implements ObservableTransformer<T, T> {
    private Context context;
    private String msg;
    private boolean cancelbale = false;

    public LoadingTransformer(Context context, String msg, boolean cancelbale) {
        this.context = context;
        this.msg = msg;
        this.cancelbale = cancelbale;
    }

    @Override
    public ObservableSource<T> apply(@NonNull Observable<T> upstream) {
        return upstream.doOnSubscribe(new OnSubscribeConsumer(context, msg, cancelbale))
                .subscribeOn(AndroidSchedulers.mainThread())
                .doFinally(new FinallyAction(context))
                .subscribeOn(AndroidSchedulers.mainThread());
    }
}