package cn.lxt.nucleusdemo.view.activity;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cn.lxt.nucleusdemo.R;
import cn.lxt.nucleusdemo.base.BaseActivity;
import cn.lxt.nucleusdemo.bean.LoginBean;
import cn.lxt.nucleusdemo.presenter.MainActivityPresenter;
import nucleus5.factory.RequiresPresenter;

@RequiresPresenter(MainActivityPresenter.class)
public class MainActivity extends BaseActivity<MainActivityPresenter> implements View.OnClickListener {
    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                getPresenter().login(this, "13477041611", "lxt123");
                break;
        }
    }

    public void loginFailed(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void loginSuccess(LoginBean loginBean) {
        Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();
    }
}
