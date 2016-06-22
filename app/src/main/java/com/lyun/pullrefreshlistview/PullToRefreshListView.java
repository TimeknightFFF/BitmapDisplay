/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.lyun.pullrefreshlistview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.lyun.bitmapdisplay.R;
import com.lyun.pullrefreshlistview.internal.EmptyViewMethodAccessor;
import com.lyun.pullrefreshlistview.internal.LoadingLayout;


public class PullToRefreshListView extends PullToRefreshAdapterViewBase<ListView> {

    private LoadingLayout mHeaderLoadingView;
    private LoadingLayout mFooterLoadingView;

    private FrameLayout mLvFooterLoadingFrame;

    public PullToRefreshListView(Context context) {
        super(context);
        setDisableScrollingWhileRefreshing(false);
    }

    public PullToRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setDisableScrollingWhileRefreshing(false);
    }

    public PullToRefreshListView(Context context, Mode mode) {
        super(context, mode);
        setDisableScrollingWhileRefreshing(false);
    }

    public void setLastUpdatedLabel(CharSequence label) {
        super.setLastUpdatedLabel(label);

        if (null != mHeaderLoadingView) {
            mHeaderLoadingView.setSubHeaderText(label);
        }
        if (null != mFooterLoadingView) {
            mFooterLoadingView.setSubHeaderText(label);
        }
    }

    @Override
    public ContextMenuInfo getContextMenuInfo() {
        return ((InternalListView) getRefreshableView()).getContextMenuInfo();
    }

    public void setPullLabel(String pullLabel, Mode mode) {
        super.setPullLabel(pullLabel, mode);

        if (null != mHeaderLoadingView && mode.canPullDown()) {
            mHeaderLoadingView.setPullLabel(pullLabel);
        }
        if (null != mFooterLoadingView && mode.canPullUp()) {
            mFooterLoadingView.setPullLabel(pullLabel);
        }
    }

    public void setRefreshingLabel(String refreshingLabel, Mode mode) {
        super.setRefreshingLabel(refreshingLabel, mode);

        if (null != mHeaderLoadingView && mode.canPullDown()) {
            mHeaderLoadingView.setRefreshingLabel(refreshingLabel);
        }
        if (null != mFooterLoadingView && mode.canPullUp()) {
            mFooterLoadingView.setRefreshingLabel(refreshingLabel);
        }
    }

    public void setReleaseLabel(String releaseLabel, Mode mode) {
        super.setReleaseLabel(releaseLabel, mode);

        if (null != mHeaderLoadingView && mode.canPullDown()) {
            mHeaderLoadingView.setReleaseLabel(releaseLabel);
        }
        if (null != mFooterLoadingView && mode.canPullUp()) {
            mFooterLoadingView.setReleaseLabel(releaseLabel);
        }
    }

    @Override
    protected final ListView createRefreshableView(Context context, AttributeSet attrs) {
        ListView lv = new InternalListView(context, attrs);

        // Get Styles from attrs
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.PullToRefresh);

        // Create Loading Views ready for use later
        FrameLayout frame = new FrameLayout(context);
        mHeaderLoadingView = new LoadingLayout(context, Mode.PULL_DOWN_TO_REFRESH, a);
        frame.addView(mHeaderLoadingView, FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        mHeaderLoadingView.setVisibility(View.GONE);
        lv.addHeaderView(frame, null, false);

        mLvFooterLoadingFrame = new FrameLayout(context);
        mFooterLoadingView = new LoadingLayout(context, Mode.PULL_UP_TO_REFRESH, a);
        mLvFooterLoadingFrame.addView(mFooterLoadingView, FrameLayout.LayoutParams.FILL_PARENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        mFooterLoadingView.setVisibility(View.GONE);

        a.recycle();

        // Set it to this so it can be used in ListActivity/ListFragment
        lv.setId(android.R.id.list);
        return lv;
    }

    protected int getNumberInternalFooterViews() {
        return null != mFooterLoadingView ? 1 : 0;
    }

    protected int getNumberInternalHeaderViews() {
        return null != mHeaderLoadingView ? 1 : 0;
    }

    public void setRefreshingHeader() {
        setRefreshingHeaderInternal();
    }

    public void setRefreshingHeader(boolean doScroll) {
        setRefreshingInternal(doScroll);
        if (mOnRefreshListener2 != null) {
            if (mCurrentMode == Mode.PULL_DOWN_TO_REFRESH) {
                mOnRefreshListener2.onPullDownToRefresh();
            } else if (mCurrentMode == Mode.PULL_UP_TO_REFRESH) {
                mOnRefreshListener2.onPullUpToRefresh();
            }
        }
    }

    @Override
    protected void resetHeader() {

        // If we're not showing the Refreshing view, or the list is empty, then
        // the header/footer views won't show so we use the
        // normal method
        ListAdapter adapter = mRefreshableView.getAdapter();
        if (!getShowViewWhileRefreshing() || null == adapter) {
            super.resetHeader();
            return;
        }

        LoadingLayout originalLoadingLayout;
        LoadingLayout listViewLoadingLayout;

        int scrollToHeight = getHeaderHeight();
        int selection;
        boolean scroll;

        switch (getCurrentMode()) {
            case PULL_UP_TO_REFRESH:
                originalLoadingLayout = getFooterLayout();
                listViewLoadingLayout = mFooterLoadingView;
                selection = mRefreshableView.getCount() - 1;
                scroll = mRefreshableView.getLastVisiblePosition() == selection;
                break;
            case PULL_DOWN_TO_REFRESH:
            default:
                originalLoadingLayout = getHeaderLayout();
                listViewLoadingLayout = mHeaderLoadingView;
                scrollToHeight *= -1;
                selection = 0;
                scroll = mRefreshableView.getFirstVisiblePosition() == selection;
                break;
        }

        // Set our Original View to Visible
        originalLoadingLayout.setVisibility(View.VISIBLE);

        /**
         * Scroll so the View is at the same Y as the ListView header/footer,
         * but only scroll if we've pulled to refresh and it's positioned
         * correctly
         */
        if (scroll && getState() != MANUAL_REFRESHING) {
            mRefreshableView.setSelection(selection);
            setHeaderScroll(scrollToHeight);
        }

        // Hide the ListView Header/Footer
        listViewLoadingLayout.setVisibility(View.GONE);

        super.resetHeader();
    }

    protected void setHeaderScroll(int y) {
        super.setHeaderScroll(y);

        if (getHeaderLayout().getVisibility() != View.VISIBLE && mHeaderLoadingView.getVisibility() == View.VISIBLE) {
            onPullYListener(y - getHeaderHeight());
        } else {
            onPullYListener(y);
        }
    }

    @Override
    protected void setRefreshingInternal(boolean doScroll) {

        // If we're not showing the Refreshing view, or the list is empty, then
        // the header/footer views won't show so we use the
        // normal method
        ListAdapter adapter = mRefreshableView.getAdapter();
        if (!getShowViewWhileRefreshing() || null == adapter || adapter.isEmpty()) {
            super.setRefreshingInternal(doScroll);
            return;
        }

        super.setRefreshingInternal(false);

        final LoadingLayout originalLoadingLayout, listViewLoadingLayout;
        final int selection, scrollToY;

        switch (getCurrentMode()) {
            case PULL_UP_TO_REFRESH:
                originalLoadingLayout = getFooterLayout();
                listViewLoadingLayout = mFooterLoadingView;
                selection = mRefreshableView.getCount() - 1;
                scrollToY = getScrollY() - getHeaderHeight();
                break;
            case PULL_DOWN_TO_REFRESH:
            default:
                originalLoadingLayout = getHeaderLayout();
                listViewLoadingLayout = mHeaderLoadingView;
                selection = 0;
                scrollToY = getScrollY() + getHeaderHeight();
                break;
        }

        // Hide our original Loading View
        originalLoadingLayout.setVisibility(View.INVISIBLE);

        // Show the ListView Loading View and set it to refresh
        listViewLoadingLayout.setVisibility(View.VISIBLE);
        listViewLoadingLayout.refreshing();

        if (doScroll) {
            // We scroll slightly so that the ListView's header/footer is at the
            // same Y position as our normal header/footer
            setHeaderScroll(scrollToY);
        }

        if (doScroll) {
            // Make sure the ListView is scrolled to show the loading
            // header/footer
            mRefreshableView.setSelection(selection);

            // Smooth scroll as normal
            smoothScrollTo(0);
        }
    }

    public void removeFootView() {
        if (getRefreshableView().getFooterViewsCount() > 0) {
            getRefreshableView().removeFooterView(mLvFooterLoadingFrame);
        }
    }

    class InternalListView extends ListView implements EmptyViewMethodAccessor {

        private boolean mAddedLvFooter = false;

        public InternalListView(Context context, AttributeSet attrs) {
            super(context, attrs);
            // should set by list view itself.
            // setHeaderDividersEnabled(false);
            // setFooterDividersEnabled(false);
        }

        @Override
        public void draw(Canvas canvas) {
            /**
             * This is a bit hacky, but ListView has got a bug in it when using
             * Header/Footer Views and the list is empty. This masks the issue
             * so that it doesn't cause an FC. See Issue #66.
             */
            try {
                super.draw(canvas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public ContextMenuInfo getContextMenuInfo() {
            return super.getContextMenuInfo();
        }

        @Override
        public void setAdapter(ListAdapter adapter) {
            // Add the Footer View at the last possible moment
            if (!mAddedLvFooter) {
                addFooterView(mLvFooterLoadingFrame, null, false);
                mAddedLvFooter = true;
            }

            super.setAdapter(adapter);
        }

        @Override
        public void setEmptyView(View emptyView) {
            PullToRefreshListView.this.setEmptyView(emptyView);
        }

        @Override
        public void setEmptyViewInternal(View emptyView) {
            super.setEmptyView(emptyView);
        }

        private float xDistance, yDistance, lastX, lastY;

        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    xDistance = yDistance = 0f;
                    lastX = ev.getX();
                    lastY = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    final float curX = ev.getX();
                    final float curY = ev.getY();
                    xDistance += Math.abs(curX - lastX);
                    yDistance += Math.abs(curY - lastY);
                    lastX = curX;
                    lastY = curY;
                    if (xDistance > yDistance)
                        return false;
            }

            return super.onInterceptTouchEvent(ev);
        }
    }

}
