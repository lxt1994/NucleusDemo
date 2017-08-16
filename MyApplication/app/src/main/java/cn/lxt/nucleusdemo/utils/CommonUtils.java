package cn.lxt.nucleusdemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by 李晓通 on 2016/11/13.
 * 作为一个通用的工具类,功能如下注释
 */
public class CommonUtils {
    private static ThreadPoolExecutor mPoolExecutor = (ThreadPoolExecutor) Executors.newCachedThreadPool();

    /**
     * 1.把耗时操作(任务)放到线程池(子线程)里面执行
     */
    public static void runInThread(Runnable task) {
        mPoolExecutor.execute(task);
    }

    /**
     * 2.创建公用的handler
     */
    private static Handler sHandler = new Handler();

    public static Handler getHandler() {
        return sHandler;
    }

    /**
     * 3.把任务放到主线程中执行
     */
    public static void myRunOnUIThread(Runnable task) {
        sHandler.post(task);
    }

    /**
     * 4.在任意线程弹吐司
     */
    public static void showToastAnywhere(final Context context, final String text) {
        myRunOnUIThread(new Runnable() {
            @Override
            public void run() {
                showToast(context, text);
            }
        });
    }

    /**
     * 5.dp转px,在代码中要指定控件大小时使用,主要用于屏幕适配
     */
    public static float dpToPx(Context context, float dp) {
        return TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    /**
     * 6.sp转px
     */
    public static float spToPx(Context context, float sp) {
        return TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    /**
     * 7.判断是否安装了某个app
     */
    private boolean isAvilible(String packageName, Context context) {
        //获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        //获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        //用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<>();
        //从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        //判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 8.静态吐司
     */
    private static Toast toast;

    public static void showToast(Context context, String content) {
        showToast(context, content, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String content, int duration) {
        showToast(context, content, duration, Gravity.BOTTOM);
    }

    public static void showToast(Context context, String content, int duration, int gravity) {
        if (toast == null) {
            toast = Toast.makeText(context, content, duration);
        } else {
            toast.setText(content);
        }
        toast.setGravity(gravity, 0, 0);
        toast.show();
    }

    /**
     * 9.显示软键盘
     *
     * @param context
     * @param view
     */
    public static void showSoftKeyboard(Context context, View view) {
        try {
            ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(view, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 10.隐藏软键盘
     *
     * @param context
     */
    public static void hideSoftKeyboard(Context context, View view) {
        try {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0); // 强制隐藏键盘
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager input = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            if (activity.getCurrentFocus() != null) {
                input.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 11.获取AndroidManifest.xml里的version
     *
     * @param context
     * @return
     */
    public static String getPackageVersionName(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return packInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getPackageVersionCode(Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
            return packInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    /**
     * 12.比较版本号
     *
     * @param newVersion
     * @param currVersion
     * @return
     */
    public static boolean compareVersion(String newVersion, String currVersion) {
        if (TextUtils.isEmpty(newVersion) || TextUtils.isEmpty(currVersion))
            return false;
        newVersion = newVersion.replace(".", "");
        currVersion = currVersion.replace(".", "");
        try {
            if (Integer.parseInt(newVersion) > Integer.parseInt(currVersion)) {
                return true;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 13.判断手机上是否有安装能否打开给定Uri的app
     *
     * @param context
     * @param uri
     * @return
     */
    public static boolean canOpenAppUri(Context context, String uri) {
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse(uri));
            ActivityInfo info = intent.resolveActivityInfo(context.getPackageManager(), 0);
            return info != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 14.判断能否使用某个app
     *
     * @param context
     * @param packageName
     * @param minAppVersion
     * @return
     */
    public static boolean canUseApp(Context context, String packageName, String minAppVersion) {
        PackageManager manager = context.getPackageManager();
        String appVersion = null;// 设备当前安装支付宝版本名
        try {
            PackageInfo info = manager.getPackageInfo(packageName, 0);
            appVersion = info.versionName; // 版本名
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        minAppVersion = minAppVersion.replace(".", "");
        appVersion = appVersion.replace(".", "");
        if (Integer.parseInt(appVersion) >= Integer.parseInt(minAppVersion)) {
            return true;
        }
        return false;
    }

    /**
     * 15.传入某app的uri,打开某个app
     *
     * @param context
     * @param uri
     */
    public static void openAppUrl(Context context, String uri) {
        try {
            Intent intent = new Intent();
            intent.setData(Uri.parse(uri));
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 16.判断是否有活动的网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static int NET_TYPE_UNKNOWN = 0;
    public static int NET_TYPE_WIFI = 1;
    public static int NET_TYPE_MOBILE = 2;

    /**
     * 17.目前联网类型
     *
     * @param context
     * @return 0:未知,
     * 1：手机，
     * 2：wifi
     */
    public static int getNetworkType(Context context) {
        ConnectivityManager mgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = mgr.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return NET_TYPE_UNKNOWN;
        }
        int type = activeNetworkInfo.getType();
        if (type == ConnectivityManager.TYPE_WIFI)
            return NET_TYPE_WIFI;
        else
            return NET_TYPE_MOBILE;
    }

    /**
     * 18.获取网络类型名称
     *
     * @param ctx
     * @return
     */
    public static String getNetworkTypeName(Context ctx) {
        ConnectivityManager mgr = (ConnectivityManager) ctx
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = mgr.getActiveNetworkInfo();
        if (activeNetworkInfo == null) {
            return null;
        }
        String extraInfo = activeNetworkInfo.getExtraInfo();
        if (extraInfo != null && extraInfo.length() > 0) {
            return extraInfo;
        }
        return activeNetworkInfo.getTypeName();
    }

    /**
     * 19.获取屏幕宽度
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    private static DecimalFormat mDecimalFormat;

    private static DecimalFormat getInstance() {
        if (mDecimalFormat == null) {
            mDecimalFormat = new DecimalFormat("#.##");
        }
        return mDecimalFormat;
    }

    /**
     * 20.传入double只获取其小数点后2位
     */
    public static String formatDouble(double number) {
        return getInstance().format(number);
    }

    public static String getRate(double number) {
        return getInstance().format(number * 100);
    }

    /**
     * 21.把分转换成元若能整除则保留小数点后一位，若不能整除则保留小数点后两位
     */
    public static String pointToUnit(double number) {
        double newNum = number / 100;
        if (number % 100 == 0) {
            return newNum + "0";
        } else {
            return formatDouble(newNum);
        }
    }

    /**
     * 若能整除则转换为int，若不能整除则保留小数点后两位
     */
    public static String doubleToInt(double number) {
        if (number == 0) {
            return "0";
        } else {
            double newNum = number / 100;
            if (number % 100 == 0) {
                return (int) (number / 100) + "";
            } else {
                return formatDouble(newNum);
            }
        }
    }

    /**
     * 22.获取TextView或者EditText上的数据
     */
    public static String getString(TextView textView) {
        return textView.getText().toString().trim();
    }

    public static String getString(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * 判断是否为空，并传入吐司
     *
     * @param context
     * @param str
     * @param toast
     * @return
     */
    public static boolean isEmpty(Context context, String str, String toast) {
        if (TextUtils.isEmpty(str)) {
            CommonUtils.showToast(context, toast);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 金额输入框中的内容限制（最大：小数点前五位，小数点后2位）
     *
     * @param edt
     */
    public static void judgeNumber(Editable edt) {

        String temp = edt.toString();
        int posDot = temp.indexOf(".");//返回指定字符在此字符串中第一次出现处的索引
        if (posDot <= 0) {//不包含小数点
            if (temp.length() <= 5) {
                return;//小于五位数直接返回
            } else {
                edt.delete(5, 6);//大于五位数就删掉第六位（只会保留五位）
                return;
            }
        }
        if (temp.length() - posDot - 1 > 2)//如果包含小数点
        {
            edt.delete(posDot + 3, posDot + 4);//删除小数点后的第三位
        }
    }
}
