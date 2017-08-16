package cn.lxt.nucleusdemo.utils;

import android.os.CountDownTimer;
import android.widget.TextView;

import static cn.lxt.nucleusdemo.utils.DateFormatUtils.formatToHHMMSS;
import static cn.lxt.nucleusdemo.utils.DateFormatUtils.msToDay;

/**
 * Created by Administrator on 2017/7/5 0005.
 */

public class CountDownTimerUtils {

    public static void setCountDown(final String head, final String type, final TextView textView, long allTime) {
        if (textView.getTag() == null) {
            CountDownTimer countDownTimer = new CountDownTimer(allTime, 1000) {

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
            textView.setTag(countDownTimer);
        } else {
            CountDownTimer countDownTimer = (CountDownTimer) textView.getTag();
            countDownTimer.start();
        }
    }
}
