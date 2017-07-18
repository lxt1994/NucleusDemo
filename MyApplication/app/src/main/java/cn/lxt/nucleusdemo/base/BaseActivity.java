package cn.lxt.nucleusdemo.base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import nucleus5.view.NucleusAppCompatActivity;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public abstract class BaseActivity<P extends BasePresenter> extends NucleusAppCompatActivity<P> {
    private ProgressDialog progressDialog;
    private boolean destroyed = false;
    private boolean finishOnCancel = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        destroyed = false;
        initView();
        initData();
    }

    protected abstract int getLayout();

    protected abstract void initView();

    protected abstract void initData();

    public void showProgressDialog(boolean flag, String msg, boolean cancelable){
        if(destroyed || isFinishing())
            return;
        if(flag) {
            if(progressDialog == null) {
                progressDialog = ProgressDialog.show(this, "", msg, false);
                progressDialog.setCancelable(cancelable);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        if(finishOnCancel)
                            finish();
                    }
                });
            }
            progressDialog.setMessage(msg);
            if(!progressDialog.isShowing())
                progressDialog.show();
        }
        else {
            if(progressDialog != null && progressDialog.isShowing()){
                progressDialog.dismiss();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyed = true;
        if(progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }

    @Override
    public boolean isDestroyed() {
        return destroyed;
    }

    public void setFinishOnCancel(boolean finishOnCancel) {
        this.finishOnCancel = finishOnCancel;
    }
}
