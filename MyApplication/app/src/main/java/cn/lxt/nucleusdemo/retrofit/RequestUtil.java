package cn.lxt.nucleusdemo.retrofit;

import android.content.Context;
import android.support.v4.util.ArrayMap;

import java.util.Map;

/**
 * Created by CN-11 on 2017/6/22.
 */

public class RequestUtil {
    public Map<String, Object> getHead(Context context) {
        Map<String, Object> head = new ArrayMap<String, Object>();
        head.put("token", "");
        return head;
    }
}
