package cn.lxt.nucleusdemo.api;

import cn.lxt.nucleusdemo.response.LoginResponse;
import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public interface Service {

    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginResponse> login(@Field("mobile") String mobile, @Field("loginPassword") String loginPassword);

}
