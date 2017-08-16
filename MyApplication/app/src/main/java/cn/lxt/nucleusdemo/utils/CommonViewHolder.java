package cn.lxt.nucleusdemo.utils;

import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李晓通 on 2016/11/7.
 */
public class CommonViewHolder {

    public final View convertView ;
    private Map<Integer,View> widgets = new HashMap<>();

    private CommonViewHolder(View convertView){
        this.convertView = convertView;
    }

    public static CommonViewHolder getCommonViewHolder(View convertView,int resId,ViewGroup parent){
        if (convertView == null){
            convertView = View.inflate(parent.getContext(),resId,null);
            CommonViewHolder holder = new CommonViewHolder(convertView);
            convertView.setTag(holder);
        }
        return (CommonViewHolder) convertView.getTag();
    }

    public View getView(int id){
        if (widgets.get(id) == null){
            widgets.put(id,convertView.findViewById(id));
        }
        return widgets.get(id);
    }
}
