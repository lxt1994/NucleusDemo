package cn.lxt.nucleusdemo.response;

import cn.lxt.nucleusdemo.bean.LoginBean;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class LoginResponse extends BaseResponse {
    private LoginBean data;

    public LoginBean getData() {
        return data;
    }

    public void setData(LoginBean data) {
        this.data = data;
    }
}
