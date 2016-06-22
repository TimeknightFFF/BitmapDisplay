package com.lyun.pullrefreshlistview;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import com.lyun.bitmapdisplay.R;


public class PullToRefreshScrollView extends PullToRefreshBase<ScrollView> {

	public PullToRefreshScrollView(Context context) {
		super(context);

		/**
		 * Added so that by default, Pull-to-Refresh refreshes the page
		 */
//		setOnRefreshListener(defaultOnRefreshListener);
	}


	public PullToRefreshScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);

		/**
		 * Added so that by default, Pull-to-Refresh refreshes the page
		 */
//		setOnRefreshListener(defaultOnRefreshListener);
	}

	@Override
	protected ScrollView createRefreshableView(Context context, AttributeSet attrs) {
		ScrollView scrollView = new ScrollView(context, attrs);
		scrollView.setId(R.id.scrollview);
		return scrollView;
	}

	@Override
	protected boolean isReadyForPullDown() {
		return mRefreshableView.getScrollY() == 0;
	}

	@Override
	protected boolean isReadyForPullUp() {
		ScrollView view = getRefreshableView();
		int off=view.getScrollY()+view.getHeight()-view.getChildAt(0).getHeight();
		if(off==0){
			return true;
		}else{
			return false;
		}
       
	}
	
	public void setOnSingleClick(OnSingleClick onSingleClick){
	    super.setOnSingleClick(onSingleClick);
	}
}
