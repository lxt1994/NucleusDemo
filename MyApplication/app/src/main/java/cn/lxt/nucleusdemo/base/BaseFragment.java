package cn.lxt.nucleusdemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nucleus5.view.NucleusFragment;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public abstract class BaseFragment<P extends BasePresenter> extends NucleusFragment<P> {
    private View mView;
    protected Context context;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getChildResource(), container, false);
        }
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(mView);
        initData();
    }

    protected abstract int getChildResource();

    protected abstract void initView(View view);

    protected abstract void initData();
}
