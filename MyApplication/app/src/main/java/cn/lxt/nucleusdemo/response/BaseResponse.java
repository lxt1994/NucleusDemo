package cn.lxt.nucleusdemo.response;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class BaseResponse {
    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
