package cn.lxt.nucleusdemo.retrofit;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLSocketFactory;

import cn.lxt.nucleusdemo.R;
import cn.lxt.nucleusdemo.constants.IpConstants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by CN-11 on 2017/5/2.
 */

public class HttpUtil {
    private static Retrofit retrofit;
    //SSL证书
    private static int[] certs = new int[]{R.raw.server};

    public static Retrofit getRetrofit(Context context) {
        if (retrofit == null) {
            Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
            retrofitBuilder.baseUrl(IpConstants.BASE_URL);
            retrofitBuilder.addConverterFactory(ScalarsConverterFactory.create());
            retrofitBuilder.addConverterFactory(GsonConverterFactory.create());
            retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
            clientBuilder.cookieJar(new CookiesManager(context));
            SSLSocketFactory sslSocketFactory = null;
            try {
                sslSocketFactory = HttpsFactroy.getSSLSocketFactory(context, certs);
            } catch (Exception e) {
                sslSocketFactory = null;
            }
            if (sslSocketFactory != null) {
                sslSocketFactory.getDefaultCipherSuites();
                clientBuilder.sslSocketFactory(sslSocketFactory);
            }
            clientBuilder.connectTimeout(30, TimeUnit.SECONDS);
            clientBuilder.readTimeout(60, TimeUnit.SECONDS);
            retrofitBuilder.client(clientBuilder.build());
            retrofit = retrofitBuilder.build();
        }
        return retrofit;
    }
}