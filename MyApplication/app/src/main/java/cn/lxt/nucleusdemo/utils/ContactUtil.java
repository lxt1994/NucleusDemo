package cn.lxt.nucleusdemo.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/4 0004.
 */

public class ContactUtil {
    /**
     * 获取联系人数据
     *
     * @param context
     * @return
     */
    public static List<ContactBean> getAllContacts(Context context) {
        List<ContactBean> list = new ArrayList<>();
        // 获取解析者
        ContentResolver resolver = context.getContentResolver();
        // 访问地址
        Uri raw_contacts = Uri.parse("content://com.android.contacts/raw_contacts");
        Uri data = Uri.parse("content://com.android.contacts/data");
        // 查询语句
        // select contact_id from raw_contacts;//1 2 3 4
        // select mimetype,data1 from view_data where raw_contact_id=3;
        // Cursor cursor=resolver.query(访问地址, 返回字段 null代表全部, where 语句, 参数, 排序)
        Cursor cursor = resolver.query(raw_contacts, new String[]{"contact_id"}, null, null, null);

        while (cursor.moveToNext()) {
            // getColumnIndex根据名称查列号
            String id = cursor.getString(cursor.getColumnIndex("contact_id"));
            if (!TextUtils.isEmpty(id)) {
                // 创建实例
                ContactBean info = new ContactBean();
                info.id = id;
                Cursor item = resolver.query(data, new String[]{"mimetype", "data1"}, "raw_contact_id=?", new String[]{id}, null);

                while (item.moveToNext()) {
                    String mimetype = item.getString(item.getColumnIndex("mimetype"));
                    String data1 = item.getString(item.getColumnIndex("data1"));
                    if ("vnd.android.cursor.item/name".equals(mimetype)) {
                        info.name = data1;
                    } else if ("vnd.android.cursor.item/phone_v2".equals(mimetype) || "vnd.android.cursor.item/vnd.com.tencent.mobileqq.voicecall.profile".equals(mimetype)) {
                        info.phoneNum = data1;
                    }
                }
                item.close();
                // 添加集合
                if (!TextUtils.isEmpty(info.name) && !TextUtils.isEmpty(info.phoneNum))
                    list.add(info);
            }
        }

        cursor.close();
        return list;
    }

    private static class ContactBean {
        public String id;
        public String phoneNum;
        public String name;

        public ContactBean() {}
    }
}
