package cn.lxt.nucleusdemo.view.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.lxt.nucleusdemo.R;
import cn.lxt.nucleusdemo.base.BaseActivity;
import cn.lxt.nucleusdemo.bean.LoginBean;
import cn.lxt.nucleusdemo.presenter.MainActivityPresenter;
import cn.lxt.nucleusdemo.utils.ClickUtils;
import nucleus5.factory.RequiresPresenter;

@RequiresPresenter(MainActivityPresenter.class)
public class MainActivity extends BaseActivity<MainActivityPresenter> implements ClickUtils.NoFastClickListener {

    private Button mButton;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mButton = (Button) findViewById(R.id.button);
        startActivity(new Intent(this, TestActivity.class));
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initClick() {
        ClickUtils.setNoFastClickListener(mButton, this);
    }

    public void loginFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void loginSuccess(LoginBean loginBean) {
        Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNoFastClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                getPresenter().login(this, "13477041611", "lxt123");
                break;
        }
    }
}
