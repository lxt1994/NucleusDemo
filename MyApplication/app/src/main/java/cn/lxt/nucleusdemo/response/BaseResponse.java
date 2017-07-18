package cn.lxt.nucleusdemo.response;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class BaseResponse {
    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
