package h5.feinno.com.mydragview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.os.Build;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     author : zhonghang
 *     e-mail : zhonghang@feinno.com
 *     time   : 2017/06/21 09:13
 *     desc   : 用于存放可以拖动的数据的容器，响应各种事件
 *     version: 1.0
 * </pre>
 */
public class DragViewContainer extends FrameLayout {
    /**
     * 注解的方式声明不同的触控事件
     */
    @IntDef({
            ActionMode.NONE, ActionMode.DRAG, ActionMode.ZOOM_WITH_TWO_FINGER, ActionMode.ICON, ActionMode.CLICK
    })
    @Retention(RetentionPolicy.SOURCE)
    public @interface ActionMode {
        int NONE = 0;
        int DRAG = 1;
        int ZOOM_WITH_TWO_FINGER = 2;
        int ICON = 3;
        int CLICK = 4;
    }

    @ActionMode
    private int mode = ActionMode.NONE;
    /**
     * 存放所有的拖动组件的集合
     */
    private List<DragView> mDragViewList;

    public List<DragView> getDragViewList() {
        return mDragViewList;
    }

    public DragViewContainer(Context context) {
        super(context);
        setUp();
    }

    public DragViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp();
    }

    public DragViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUp();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DragViewContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUp();
    }

    private void setUp() {
        //初始化存放拖动组件数据的集合
        if (mDragViewList == null) {
            mDragViewList = new ArrayList<>();
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        /**
         *  ViewGroup中绘制内容在此处进行绘制，和View的onDraw需要区别。
         *  如果有背景的话ViewGroup也会执行onDraw()方法，在onDraw方法中调用dispathDraw()方法
         */
        for (DragView dragView : mDragViewList) {
            dragView.draw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //是否处理事件
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //是否拦截事件的处理。点击到内部的拖动组件时拦截事件
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                ev.getX();
                ev.getY();
                if (findHandlingSticker(ev.getX(), ev.getY()) != null) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     *
     **/
    @Nullable
    protected DragView findHandlingSticker(float x, float y) {
        for (DragView dragView : mDragViewList) {
            if (dragView.contains(new PointF(x, y))) {
                return dragView;
            }
        }
        return null;
    }
}
