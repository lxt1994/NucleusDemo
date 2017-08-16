package cn.lxt.nucleusdemo.utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;

public class ActivityUtils {

    private static ArrayList<Fragment> cacheFragments = new ArrayList<>();
    private static Fragment preFragment;

    /**
     * 添加显示隐藏fragment
     *
     * @param fragmentManager
     * @param fragment
     * @param frameId
     */
    public static void addAndShowHideFragment(@NonNull FragmentManager fragmentManager, @NonNull Fragment fragment, int frameId) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!cacheFragments.contains(fragment)) {
            transaction.add(frameId, fragment);
            cacheFragments.add(fragment);
        } else {
            transaction.show(fragment);
        }
        if (preFragment != null) {
            transaction.hide(preFragment);
        }
        transaction.commitAllowingStateLoss();
        //记录上一个Fragment
        preFragment = fragment;
    }

    /**
     * 开启一个activity
     *
     * @param context
     * @param clazz
     */
    public static void startActivity(Context context, Class clazz) {
        Intent intent = new Intent(context, clazz);
        context.startActivity(intent);
    }

    /**
     * 添加回退栈的replace方法
     *
     * @param fragmentManager
     * @param frameId
     * @param fragment
     */
    public static void replaceAndAddToBackStackFragment(FragmentManager fragmentManager, int frameId, Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
