package com.max.emptylayout;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends ListActivity {
    private EmptyLayout mEmptyLayout; //
    private ArrayAdapter<String> mAdapter;
    private View.OnClickListener mErrorButtonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "点击了刷新", Toast.LENGTH_SHORT)
                    .show();
        }
    };
    private View.OnClickListener mFriendEmptyButtonClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivity.this, "点击了邀请", Toast.LENGTH_SHORT)
                    .show();
        }
    };
    private EmptyLinearLayout mEmpty; //自定义linearlayout布局


    static final String[] MOVIES = new String[] { "item", "item", "item",
            "item", "item", "item", "item", "item", "item", };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        mEmpty = (EmptyLinearLayout) findViewById(R.id.empty_layout);
//        mEmpty.setErrorType(EmptyLinearLayout.NODATA);
        // 初始化空布局
        mEmptyLayout = new EmptyLayout(this, getListView());
        mEmptyLayout.setErrorButtonClickListener(mErrorButtonClickListener);
        mEmptyLayout
                .setFriendEmptyButtonClickListener(mFriendEmptyButtonClickListener);
        populateList();
    }

    // 显示空
    public void onShowEmpty(View view) {
        mAdapter.clear();
        mEmptyLayout.showEmpty();

    }

    // 显示加载
    public void onShowLoading(View view) {
        mAdapter.clear();
        mEmptyLayout.showLoading();
    }

    // 显示错误
    public void onShowError(View view) {
        mAdapter.clear();
        mEmptyLayout.showError();
    }

    // 显示朋友为空
    public void onShowFriendEmpty(View view) {
        mAdapter.clear();
        mEmptyLayout.showFriendEmpty();
    }

    // 显示list
    public void onShowList(View view) {
        populateList();
    }

    private void populateList() {
        ArrayList<String> list = new ArrayList<String>();
        list.addAll(Arrays.asList(MOVIES));
        mAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        setListAdapter(mAdapter);
    }
}
