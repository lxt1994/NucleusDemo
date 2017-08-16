package cn.lxt.nucleusdemo.view.activity;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.lxt.nucleusdemo.R;
import cn.lxt.nucleusdemo.base.BaseActivity;
import cn.lxt.nucleusdemo.bean.TestBean;
import cn.lxt.nucleusdemo.utils.CommonViewHolder;

/**
 * Created by Administrator on 2017/7/24 0024.
 */

public class TestActivity extends BaseActivity {

    private ListView mListView;
    private RecyclerView mRecyclerView;
    private MyRecyclerAdapter mMRecyclerAdapter;
    private List<List<TestBean>> mLists;
    private MyListAdapter mListAdapter;

    @Override
    protected int getLayout() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        mListView = (ListView) findViewById(R.id.listview);
        initListView();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        initRecyclerView();
    }

    private void initRecyclerView() {
        mLists = new ArrayList<>();
        List<TestBean> list0 = new ArrayList<>();
        list0.add(new TestBean("1"));
        mLists.add(list0);
        List<TestBean> list1 = new ArrayList<>();
        list1.add(new TestBean("1"));
        list1.add(new TestBean("2"));
        mLists.add(list1);
        List<TestBean> list2 = new ArrayList<>();
        list2.add(new TestBean("1"));
        list2.add(new TestBean("2"));
        list2.add(new TestBean("3"));
        mLists.add(list2);
        List<TestBean> list3 = new ArrayList<>();
        list3.add(new TestBean("1"));
        list3.add(new TestBean("2"));
        list3.add(new TestBean("3"));
        list3.add(new TestBean("4"));
        mLists.add(list3);
        List<TestBean> list4 = new ArrayList<>();
        list4.add(new TestBean("1"));
        list4.add(new TestBean("2"));
        list4.add(new TestBean("3"));
        list4.add(new TestBean("4"));
        list4.add(new TestBean("5"));
        mLists.add(list4);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false));
        mMRecyclerAdapter = new MyRecyclerAdapter(null);
        mRecyclerView.setAdapter(mMRecyclerAdapter);
        refreshRecyclerData(0);
    }

    private void initListView() {
        mListAdapter = new MyListAdapter(Arrays.asList(new String[]{"第一条", "第二条", "第三条", "第四条", "第五条"}));
        mListView.setAdapter(mListAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                refreshRecyclerData(position);
                mListAdapter.setPosition(position);
            }
        });
    }

    private void refreshRecyclerData(int position) {
        List<TestBean> testBeen = mLists.get(position);
        mMRecyclerAdapter.setNewData(testBeen);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initClick() {

    }

    private class MyListAdapter extends BaseAdapter {
        private final List<String> stringList;
        private int preposition = 0;

        public MyListAdapter(List<String> stringList) {
            this.stringList = stringList;
        }

        @Override
        public int getCount() {
            return stringList == null ? 0 : stringList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CommonViewHolder commonViewHolder = CommonViewHolder.getCommonViewHolder(convertView, R.layout.item_listview, parent);
            TextView textView = (TextView) commonViewHolder.getView(R.id.tv);
            textView.setText(stringList.get(position));
            textView.setBackgroundColor(ContextCompat.getColor(TestActivity.this, preposition == position ? R.color.colorAccent : R.color.colorPrimaryDark));
            return commonViewHolder.convertView;
        }

        public void setPosition(int position) {
            preposition = position;
            notifyDataSetChanged();
        }
    }

    private class MyRecyclerAdapter extends BaseQuickAdapter<TestBean, BaseViewHolder> {

        public MyRecyclerAdapter(@Nullable List<TestBean> data) {
            super(R.layout.item_recyclerview, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, TestBean item) {
            helper.setText(R.id.tv, item.id);
        }
    }
}
