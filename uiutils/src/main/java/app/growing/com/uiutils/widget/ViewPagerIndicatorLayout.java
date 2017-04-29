package app.growing.com.uiutils.widget;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import app.growing.com.uiutils.R;

/**
 * Created by 明正 on 2017/4/29.
 */

public class ViewPagerIndicatorLayout<T> extends LinearLayout {
    private Context context;
    private ViewPager mVp;
    private TextView mTxtBannerInfor;
    private LinearLayout mLiAddLine;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private ArrayList<T> mList;

    public ViewPagerIndicatorLayout(Context context) {
        this(context, null);
        this.context = context;
    }

    public ViewPagerIndicatorLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        this.context = context;
    }

    public ViewPagerIndicatorLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init() {
        //设置布局方向
        setOrientation(LinearLayout.VERTICAL);
        //加载View显示xml
        View view = View.inflate(context, R.layout.activity_vp_indicator, null);
        if (view != null) {
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, getWindowHeight() / 4);
            addView(view, lp);
            /*加载控件*/
            mVp = (ViewPager) view.findViewById(R.id.vp);
            mTxtBannerInfor = (TextView) view.findViewById(R.id.txt_banner_infor);
            mLiAddLine = (LinearLayout) view.findViewById(R.id.ll_add_line);
            initListencer();
        }
    }


    private void initListencer() {
        if (mVp != null) {

            /*touch事件处理*/
            mVp.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {
                    return onVpTouch(view, event);
                }
            });

            /*添加监听*/
            if (mOnPageChangeListener != null) {
                mVp.addOnPageChangeListener(mOnPageChangeListener);
            } else {
                mVp.addOnPageChangeListener(mPageChangeListener);
            }
        }
    }

    public int getWindowHeight() {
        WindowManager win = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = win.getDefaultDisplay().getHeight();
        return height;
    }

    /**
     * 添加视图控制器--adapter
     *
     * @param adapter
     */
    public void setAdapter(PagerAdapter adapter, ArrayList<T> list) {
        if (adapter != null && mVp != null && list != null && list.size() > 0 && mLiAddLine != null) {
            mVp.setAdapter(adapter);
            mVp.setCurrentItem(4000 * list.size());
            mLiAddLine.removeAllViews();
            LayoutParams lp = new LayoutParams(12, 12);
            lp.gravity = Gravity.LEFT | Gravity.CENTER;
            lp.setMargins(5, 0, 0, 0);
            for (int i = 0; i < list.size(); i++) {
                TextView txt = new TextView(context);
                txt.setGravity(Gravity.CENTER);
                txt.setBackgroundResource(R.drawable.shape_indicator_bg);
                mLiAddLine.addView(txt, lp);
            }

            if (mHandler != null) {
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageAtTime(0, 5000);
            }
        }
    }

    /**
     * 添加View Pager改变监听实体
     *
     * @param onPageChangeListener
     */
    public void addOnpagerChangeListencer(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mOnPageChangeListener = onPageChangeListener;
    }


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    int item = mVp.getCurrentItem();
                    item++;
                    mVp.setCurrentItem(item);
                    mHandler.removeMessages(0);
                    mHandler.sendEmptyMessageAtTime(0,5000);
                    break;
            }
        }
    };

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (mList != null && mList.size() > 0) {
                position = position % mList.size();
                for (int i = 0; i < mLiAddLine.getChildCount(); i++) {
                    if (position == i) {
                        TextView txt = (TextView) mLiAddLine.getChildAt(i);
                        txt.setBackgroundResource(R.drawable.shape_indicator_se_bg);
                    } else {
                        TextView txt = (TextView) mLiAddLine.getChildAt(i);
                        txt.setBackgroundResource(R.drawable.shape_indicator_bg);
                    }
                }
                mHandler.removeMessages(0);
                mHandler.sendEmptyMessageAtTime(0, 5000);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private boolean onVpTouch(View view, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return false;
            case MotionEvent.ACTION_MOVE:
                mHandler.removeMessages(0);
                return true;
            case MotionEvent.ACTION_UP:
                mHandler.sendEmptyMessageAtTime(0, 5000);
                return false;
            default:
                return false;
        }
    }
}
