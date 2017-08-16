package cn.lxt.nucleusdemo.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.SimpleDateFormat;

/**
 * Created by Administrator on 2017/5/25 0025.
 */

public class DateFormatUtils {

    public static String formatDateToYearMonthDay(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return simpleDateFormat.format(time);
    }

    /**
     * 毫秒值向上取整拿到天数
     *
     * @param time
     * @return
     */
    public static long msToDay(long time) {
        Double times = Double.valueOf(time + "");
        double days = Math.ceil(times / 1000 / 60 / 60 / 24);
        return (long) days;
    }

    public static long dayToMs(long day) {
        return day * 24 * 60 * 60 * 1000;
    }

    /**
     * 当时间<=1的时候开始倒计时
     */

    public static void autoCountDownTime(final String head, long time, final TextView textView, final String type) {
        CountDownTimer countDownTimer = new CountDownTimer(time, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished <= 86400000) {
                    textView.setText(head + formatToHHMMSS(millisUntilFinished));
                } else {
                    textView.setText(head + msToDay(millisUntilFinished) + type);
                }
            }

            @Override
            public void onFinish() {
                textView.setText(head + "0");
            }
        };
        countDownTimer.start();
    }

    public static String msToHour(double time) {
        return CommonUtils.formatDouble(time / 1000 / 60 / 60);
    }

    public static String formatDateToYYYYMMDDHHMMSS(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return simpleDateFormat.format(time);
    }

    public static String formatToHHMMSS(long mss) {
        long hours = (mss % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (mss % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (mss % (1000 * 60)) / 1000;
        return hours + "时" + minutes + "分" + seconds + "秒";
    }
}
