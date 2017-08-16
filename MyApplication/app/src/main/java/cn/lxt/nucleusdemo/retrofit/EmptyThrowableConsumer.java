package cn.lxt.nucleusdemo.retrofit;

import android.content.Context;
import android.widget.Toast;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * Created by CN-11 on 2017/7/7.
 */

public class EmptyThrowableConsumer implements Consumer<Throwable> {

    private Context context;

    public EmptyThrowableConsumer() {
    }


    public EmptyThrowableConsumer(Context context) {
        this.context = context;
    }

    @Override
    public void accept(@NonNull Throwable throwable) throws Exception {
        if (context != null) {
            Toast.makeText(context, "网络异常", Toast.LENGTH_LONG).show();
        }
    }
}
