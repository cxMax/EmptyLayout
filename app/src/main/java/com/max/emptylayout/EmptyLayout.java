package com.max.emptylayout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

/**
 * 自定义viewgroup,包含4种状态 1.显示自定义加载progressbar; 2.显示没有数据 3.显示没有好友数据 4.显示连接错误
 *
 * @author max
 *
 */
public class EmptyLayout {

	// 4种状态
	public static final int EMPTY = 1; // 没数据
	public static final int LOADING = 2; // 等待
	public static final int ERROR = 3; // 错误
	public static final int FRIEND_EMPTY = 4; // 好友无数据
	// 4种状态对应的view
	private ViewGroup mLoadingView; // 等待
	private ViewGroup mEmptyView; // 没数据
	private ViewGroup mErrorView;// 错误
	private ViewGroup mFriendEmptyView; // 无好友数据
	// 2个button的点击事件
	private View.OnClickListener mErrorButtonClickListener;
	private View.OnClickListener mFriendEmptyButtonClickListener;
	// 2gebutton对应的id
	private int mErrorButtonId = R.id.error_button;
	private int mFriendEmptyButtonId = R.id.empty_button;
	/* ==============构造函数参数================ */
	// 上下文
	private Context mContext;
	// 对外的listview
	private ListView mListView;
	private LayoutInflater mInflater;

	/* ==============默认的参数================ */
	private int mEmptyType = LOADING;
	private boolean mViewsAdded = false; //是否子view加入到父布局

	/* ==============get/set方法================ */
	public ViewGroup getLoadingView() {
		return mLoadingView;
	}

	public void setLoadingView(ViewGroup LoadingView) {
		this.mLoadingView = LoadingView;
	}

	// 设置res布局文件
	public void setLoadingViewRes(int res) {
		this.mLoadingView = (ViewGroup) mInflater.inflate(res, null);
	}

	public ViewGroup getEmptyView() {
		return mEmptyView;
	}

	public void setEmptyView(ViewGroup EmptyView) {
		this.mEmptyView = EmptyView;
	}

	// 设置res布局文件
	public void setEmptyViewRes(int res) {
		this.mEmptyView = (ViewGroup) mInflater.inflate(res, null);
	}

	public ViewGroup getErrorView() {
		return mErrorView;
	}

	public void setErrorView(ViewGroup ErrorView) {
		this.mErrorView = ErrorView;
	}

	// 设置res布局文件
	public void setErrorViewRes(int res) {
		this.mErrorView = (ViewGroup) mInflater.inflate(res, null);
	}

	public ViewGroup getFriendEmptyView() {
		return mFriendEmptyView;
	}

	public void setFriendEmptyView(ViewGroup FriendEmptyView) {
		this.mFriendEmptyView = FriendEmptyView;
	}

	// 设置res布局文件
	public void setFriendEmptyViewRes(int res) {
		this.mFriendEmptyView = (ViewGroup) mInflater.inflate(res, null);
	}

	public ListView getListView() {
		return mListView;
	}

	public void setListView(ListView listView) {
		this.mListView = listView;
	}

	public int getEmptyType() {
		return mEmptyType;
	}

	/**对外的方法,设置显示的哪种状态的空布局**/
	public void setEmptyType(int emptyType) {
		this.mEmptyType = emptyType;
		changeEmptyType();
	}

	public View.OnClickListener getErrorButtonClickListener() {
		return mErrorButtonClickListener;
	}

	public void setErrorButtonClickListener(
			View.OnClickListener errorButtonClickListener) {
		this.mErrorButtonClickListener = errorButtonClickListener;
	}

	public View.OnClickListener getFriendEmptyButtonClickListener() {
		return mFriendEmptyButtonClickListener;
	}

	public void setFriendEmptyButtonClickListener(
			View.OnClickListener friendEmptyButtonClickListener) {
		this.mFriendEmptyButtonClickListener = friendEmptyButtonClickListener;
	}

	/* ==============构造函数================ */
	public EmptyLayout(Context context) {
		super();
		this.mContext = context;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public EmptyLayout(Context context, ListView listView) {
		super();
		this.mContext = context;
		this.mListView = listView;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/* ==============对外的方法,来实现显示不同状态下的layout================ */
	public void showEmpty(){
		this.mEmptyType = EMPTY;
		changeEmptyType();
	}
	public void showLoading() {
		this.mEmptyType = LOADING;
		changeEmptyType();
	}
	public void showError() {
		this.mEmptyType = ERROR;
		changeEmptyType();
	}
	public void showFriendEmpty() {
		this.mEmptyType = FRIEND_EMPTY;
		changeEmptyType();
	}

	//根据传过来的参数,设定显示的view
	private void changeEmptyType() {
		//先初始化各个状态下的布局文件
		setDefaultValues();
		//把已经初始化的view加到父布局里面去
		if (!mViewsAdded) {
			//设置属性
			RelativeLayout.LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
			lp.addRule(RelativeLayout.CENTER_VERTICAL);
			RelativeLayout rl = new RelativeLayout(mContext);
			rl.setLayoutParams(lp);
			//添加到父布局
			if (mEmptyView != null) {
				rl.addView(mEmptyView);
			}
			if (mErrorView != null) {
				rl.addView(mErrorView);
			}
			if (mFriendEmptyView != null) {
				rl.addView(mFriendEmptyView);
			}
			if (mLoadingView != null) {
				rl.addView(mLoadingView);
			}
			mViewsAdded = true;
			ViewGroup parent = (ViewGroup) mListView.getParent();
			parent.addView(rl);
			mListView.setEmptyView(rl);
		}
		//根据传过来的type,设定要显示哪个view
		//因为之前是全部加入了父布局,所以这边不要的全部隐藏
		if (mListView != null) {
			switch (mEmptyType) {
				case EMPTY:
					if (mEmptyView != null) {
						mEmptyView.setVisibility(View.VISIBLE);
					}
					if (mErrorView != null) {
						mErrorView.setVisibility(View.GONE);
					}
					if (mLoadingView != null) {
						mLoadingView.setVisibility(View.GONE);
					}
					if (mFriendEmptyView != null) {
						mFriendEmptyView.setVisibility(View.GONE);
					}
					break;
				case ERROR:
					if (mEmptyView != null) {
						mEmptyView.setVisibility(View.GONE);
					}
					if (mErrorView != null) {
						mErrorView.setVisibility(View.VISIBLE);
					}
					if (mLoadingView != null) {
						mLoadingView.setVisibility(View.GONE);
					}
					if (mFriendEmptyView != null) {
						mFriendEmptyView.setVisibility(View.GONE);
					}
					break;
				case FRIEND_EMPTY:
					if (mEmptyView != null) {
						mEmptyView.setVisibility(View.GONE);
					}
					if (mErrorView != null) {
						mErrorView.setVisibility(View.GONE);
					}
					if (mLoadingView != null) {
						mLoadingView.setVisibility(View.GONE);
					}
					if (mFriendEmptyView != null) {
						mFriendEmptyView.setVisibility(View.VISIBLE);
					}
					break;
				case LOADING:
					if (mEmptyView != null) {
						mEmptyView.setVisibility(View.GONE);
					}
					if (mErrorView != null) {
						mErrorView.setVisibility(View.GONE);
					}
					if (mLoadingView != null) {
						mLoadingView.setVisibility(View.VISIBLE);
					}
					if (mFriendEmptyView != null) {
						mFriendEmptyView.setVisibility(View.GONE);
					}
					break;
				default:
					break;
			}
		}
	}

	/**先初始化各个状态下的布局文件**/
	private void setDefaultValues() {
		//初始化mLoadingView
		if (mLoadingView == null) {
			mLoadingView = (ViewGroup) mInflater.inflate(R.layout.view_loading, null);
		}
		if (mEmptyView == null) {
			mEmptyView = (ViewGroup) mInflater.inflate(R.layout.view_empty, null);
		}
		if (mErrorView == null) {
			mErrorView = (ViewGroup) mInflater.inflate(R.layout.view_error, null);
			//初始化里面的button
			if (mErrorButtonId > 0 && mErrorButtonClickListener != null) {
				View errorViewButton = mErrorView.findViewById(R.id.error_button);
				if (errorViewButton != null) {
					errorViewButton.setOnClickListener(mErrorButtonClickListener);
				}
			}
		}
		if (mFriendEmptyView == null) {
			mFriendEmptyView = (ViewGroup) mInflater.inflate(R.layout.view_friend_empty, null);
			//初始化里面的button
			if (mFriendEmptyButtonId > 0 && mFriendEmptyButtonClickListener != null) {
				View errorViewButton = mFriendEmptyView.findViewById(R.id.empty_button);
				if (errorViewButton != null) {
					errorViewButton.setOnClickListener(mFriendEmptyButtonClickListener);
				}
			}
		}

	}



}
