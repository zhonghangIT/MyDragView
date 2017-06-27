package h5.feinno.com.mydragview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * <pre>
 *     author : zhonghang
 *     e-mail : zhonghang@feinno.com
 *     time   : 2017/06/21 09:13
 *     desc   : 用于存放拖动的组件的容器
 *     version: 1.0
 * </pre>
 */


public class DragViewContainer extends ViewGroup {
    /**
     * 用于控制容器内的控件的拖动
     */
    private ViewDragHelper mViewDragHelper;
    /**
     * 用于设置拖动的灵敏度
     */
    private float mSensitivity = 1.0f;

    public DragViewContainer(Context context) {
        super(context);
        setup();
    }

    public DragViewContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public DragViewContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DragViewContainer(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup();
    }

    private void setup() {
        mViewDragHelper = ViewDragHelper.create(this, mSensitivity, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                //此处返回值表示可以拖动的控件
                if (child instanceof DragView) {
                    if (((DragView) child).isScale()) {
                        return false;
                    }
                }
                return true;
            }

            @Override
            public int getViewHorizontalDragRange(View child) {
                return getMeasuredWidth() - child.getMeasuredWidth();
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public int clampViewPositionHorizontal(View child, int left, int dx) {
//                拖动控件的横向的left值
                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - child.getWidth() - leftBound;
                final int newLeft = Math.min(Math.max(left, leftBound), rightBound);
                return newLeft;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
//                拖动控件的上下值
                final int topBound = getPaddingTop();
                final int bottomBound = getHeight() - child.getHeight() - topBound;
                final int newTop = Math.min(Math.max(top, topBound), bottomBound);
                return newTop;
            }

        });
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            int width = child.getMeasuredWidth();
            int height = child.getMeasuredHeight();
            ViewGroup.LayoutParams params= child.getLayoutParams();
            child.layout(child.getLeft(), child.getTop(), child.getLeft() + width, height + child.getTop());
//            child.layout((int) child.getX(), (int) child.getY(), (int) (child.getX() + width), (int) (height + child.getY()));
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //对子控件的大小进行测量
        measureChildren(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return super.onTouchEvent(event);
    }
}
