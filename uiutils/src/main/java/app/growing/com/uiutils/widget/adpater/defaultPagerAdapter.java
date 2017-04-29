package app.growing.com.uiutils.widget.adpater;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by 明正 on 2017/4/29.
 */

public abstract class defaultPagerAdapter<T> extends PagerAdapter {
    public ArrayList<T> mList;
    public Context mContext;

    public defaultPagerAdapter(ArrayList<T> list, Context context) {
        mList = list;
        mContext = context;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mList == null ? 0 : Integer.MAX_VALUE;
    }

    /**
     * Determines whether a page View is associated with a specific key object
     * as returned by {@link #instantiateItem(ViewGroup, int)}. This method is
     * required for a PagerAdapter to function properly.
     *
     * @param view   Page View to check for association with <code>object</code>
     * @param object Object to check for association with <code>view</code>
     * @return true if <code>view</code> is associated with the key object <code>object</code>
     */
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }
}
