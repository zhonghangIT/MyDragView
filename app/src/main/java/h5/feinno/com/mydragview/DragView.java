package h5.feinno.com.mydragview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;

/**
 * <pre>
 *     author : zhonghang
 *     e-mail : zhonghang@feinno.com
 *     time   : 2017/06/21 15:14
 *     desc   : 用于拖动放大缩小的父控件
 *     version: 1.0
 * </pre>
 */
public class DragView extends View {
    /**
     * 是否为选中状态
     */
    private boolean isChecked;
    /**
     * 绘制选中线的宽度
     */
    private int mLineWidth;
    /**
     * 绘制线的长度
     */
    private int mLineLong;
    /**
     * 绘制线的画笔
     */
    private Paint mPaintLine;
    /**
     * 绘制选中线的颜色
     */
    private int mColorLine;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 控件的宽度
     */
    private int mWidth;
    /**
     * 控件的高度
     */
    private int mHeight;

    /**
     * 绘制背景色
     */
    private Paint mPaintBackground;
    private Paint mPaintBorder;
    /**
     * 添加手势
     */
    private ScaleGestureDetector scaleGestureDetector;
    /**
     * 放大缩小的类型，触控点分为4个角以及4条边，值是mTopLeft，mTopRight等
     */
    private int mScaleType = -1;
    /**
     * 放大缩小的速率，防止闪动
     */
    private static final int mSpeed = 15;

    /**
     * 左上角
     */
    public static final int TOP_LEFT = 1;
    /**
     * 右上角
     */
    public static final int TOP_RIGHT = 2;
    /**
     * 左下角
     */
    public static final int BOTTOM_LEFT = 3;
    /**
     * 右下角
     */
    public static final int BOTTOM_RIGHT = 4;
    /**
     * 上边
     */
    public static final int TOP_BORD = 5;
    /**
     * 左边
     */
    public static final int LEFT_BORD = 6;
    /**
     * 下边
     */
    public static final int BOTTOM_BORD = 7;
    /**
     * 右边
     */
    public static final int RIGHT_BORD = 8;

    /**
     * 左上角是否响应
     */
    public boolean mTopLeftStatus = true;
    /**
     * 右上角是否响应
     */
    public boolean mTopRightStatus = true;
    /**
     * 左下角是否响应
     */
    public boolean mBottomLeftStatus = true;
    /**
     * 右下角是否响应
     */
    public boolean mBottomRightStatus = true;
    /**
     * 上边是否响应
     */
    public boolean mTopBordStatus = true;
    /**
     * 左边是否响应
     */
    public boolean mLeftBordStatus = true;
    /**
     * 下边是否响应
     */
    public boolean mBottomBordStatus = true;
    /**
     * 右边是否响应
     */
    public boolean mRightBordStatus = true;

    public DragView(Context context) {
        super(context);
        setup(context);
    }

    public DragView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setup(context);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup(context);

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public DragView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup(context);
    }

    /**
     * 初始化设置
     */

    private void setup(Context context) {
        mContext = context;
        mColorLine = Color.RED;
        mLineWidth = DensityUtil.dip2px(mContext, 5);
        mLineLong = DensityUtil.dip2px(mContext, 20);
        initPaint();
        scaleGestureDetector = new ScaleGestureDetector(mContext, new ScaleGestureDetector.OnScaleGestureListener() {
            @Override
            public boolean onScale(ScaleGestureDetector detector) {

                return false;
            }

            @Override
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                return false;
            }

            @Override
            public void onScaleEnd(ScaleGestureDetector detector) {

            }
        });
    }

    /**
     * 初始化画笔的属性
     */
    private void initPaint() {
        mPaintLine = new Paint();
        mPaintLine.setColor(mColorLine);
        mPaintLine.setStrokeWidth(mLineWidth);
        mPaintLine.setAntiAlias(true);
        mPaintBackground = new Paint();
        mPaintBackground.setColor(Color.GRAY);
        DashPathEffect pathEffect = new DashPathEffect(new float[]{1, 2}, 1);
        mPaintBorder = new Paint();
        mPaintBorder.setStrokeWidth(5);
        mPaintBorder.setAntiAlias(true);
        mPaintBorder.setPathEffect(pathEffect);
        mPaintBorder.setStyle(Paint.Style.STROKE);
        mPaintBorder.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
//        setClipBounds(new Rect(-mLineLong, -mLineLong, mWidth + mLineLong, mHeight + mLineLong));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(new RectF(0, 0, mWidth, mHeight), mLineLong, mLineLong, mPaintBorder);
        canvas.drawRect(mLineLong, mLineLong, mWidth - mLineLong, mHeight - mLineLong, mPaintBackground);
//        drawTopLeft(canvas);
//        drawTopRight(canvas);
//        drawBottomLeft(canvas);
//        drawBottomRight(canvas);
//        canvas.drawCircle(mWidth / 2, mLineLong, mLineLong, mPaintLine);
//        canvas.drawCircle(mWidth / 2, mHeight - mLineLong, mLineLong, mPaintLine);
//        canvas.drawCircle(mLineLong, mHeight / 2, mLineLong, mPaintLine);
//        canvas.drawCircle(mWidth - mLineLong, mHeight / 2, mLineLong, mPaintLine);
    }

    /**
     * 绘制右下角
     *
     * @param canvas
     */
    private void drawBottomRight(Canvas canvas) {
        canvas.drawLine(mWidth, mHeight, mWidth, mHeight - mLineLong, mPaintLine);
        canvas.drawLine(mWidth, mHeight, mWidth - mLineLong, mHeight, mPaintLine);
    }

    /**
     * 绘制左下角
     *
     * @param canvas
     */
    private void drawBottomLeft(Canvas canvas) {
        canvas.drawLine(0, mHeight, 0, mHeight - mLineLong, mPaintLine);
        canvas.drawLine(0, mHeight, mLineLong, mHeight, mPaintLine);
    }

    /**
     * 绘制右上角
     *
     * @param canvas
     */
    private void drawTopRight(Canvas canvas) {
        canvas.drawLine(mWidth, 0, mWidth - mLineLong, 0, mPaintLine);
        canvas.drawLine(mWidth, 0, mWidth, mLineLong, mPaintLine);
    }

    /**
     * 绘制左上角
     *
     * @param canvas
     */
    private void drawTopLeft(Canvas canvas) {
        canvas.drawLine(0, 0, mLineLong, 0, mPaintLine);
        canvas.drawLine(0, 0, 0, mLineLong, mPaintLine);
    }

    private float newX;
    private float newY;
    private float oldX;
    private float oldY;
    /**
     * 确定该控件是否能够进行放大缩小
     */
    private boolean scale;

    public boolean isScale() {
        return scale;
    }

    public void setScale(boolean scale) {
        this.scale = scale;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //八个范围返回true
                newX = event.getX();
                newY = event.getY();
                mScaleType = getScaleType(event);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.d("========", "=========" + scale + mScaleType);
                if (mScaleType != -1) {
                    oldX = newX;
                    oldY = newY;
                    newX = event.getX();
                    newY = event.getY();
                    scale((int) (newX - oldX), (int) (newY - oldY));
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //多指操作开始。
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mScaleType = -1;
                scale = false;
                break;
            default:
                break;
        }
        if (mScaleType != -1) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 用于设置四个角和四条边的拖动变化
     *
     * @param distanceX x轴移动的距离
     * @param distanceY y轴移动的距离
     */
    private void scale(int distanceX, int distanceY) {
        if (distanceX > 0) {
            distanceX = distanceX > mSpeed ? mSpeed : distanceX;
        } else {
            distanceX = distanceX < -mSpeed ? -mSpeed : distanceX;
        }
        if (distanceY > 0) {
            distanceY = distanceY > mSpeed ? mSpeed : distanceY;
        } else {
            distanceY = distanceY < -mSpeed ? -mSpeed : distanceY;
        }
        ViewGroup.LayoutParams params = getLayoutParams();
        switch (mScaleType) {
            case TOP_LEFT:
                Log.d("---------", "----------------");
                params.width -= distanceX;
                params.height -= distanceY;
                setLayoutParams(params);
                offsetLeftAndRight(distanceX);
                offsetTopAndBottom(distanceY);
                break;
            case TOP_RIGHT:
                params.width += distanceX;
                params.height -= distanceY;
                setLayoutParams(params);
                offsetTopAndBottom(distanceY);
                break;
            case BOTTOM_LEFT:
                params.width -= distanceX;
                params.height += distanceY;
                setLayoutParams(params);
                offsetLeftAndRight(distanceX);
                break;
            case BOTTOM_RIGHT:
                params.width += distanceX;
                params.height += distanceY;
                setLayoutParams(params);
                break;
            case TOP_BORD:
                params.height -= distanceY;
                setLayoutParams(params);
                offsetTopAndBottom(distanceY);
                break;
            case BOTTOM_BORD:
                params.height += distanceY;
                setLayoutParams(params);
                break;
            case LEFT_BORD:
                params.width -= distanceX;
                setLayoutParams(params);
                offsetLeftAndRight(distanceX);
                break;
            case RIGHT_BORD:
                params.width += distanceX;
                setLayoutParams(params);
                break;
        }
    }

    /**
     * 根据触控点确认哪个点响应该事件,在点击事件的ACTION_DOWN时确定响应的点
     *
     * @param event 触控时的事件
     * @return 返回的响应事件的类型, 主要是mTopLeft等，返回-1代表无此响应
     */
    private int getScaleType(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        //左上角的标准
        if (mTopLeftStatus && x > 0 && x < mLineLong && y > 0 && y < mLineLong) {
            scale = true;
            return TOP_LEFT;
        }
        ViewGroup.LayoutParams params = getLayoutParams();
        //得到控件当前的宽高，根据宽高计算响应的位置
        int width = params.width;
        int height = params.height;
        //右上角标准
        if (mTopRightStatus && x > width - mLineLong && x < width && y > 0 && y < mLineLong) {
            scale = true;
            return TOP_RIGHT;
        }
        //左下角
        if (mBottomLeftStatus && x > 0 && x < mLineLong && y > height - mLineLong && y < height) {
            scale = true;
            return BOTTOM_LEFT;
        }
        //右下角
        if (mBottomRightStatus && x > width - mLineLong && x < width && y > height - mLineLong && y < height) {
            scale = true;
            return BOTTOM_RIGHT;
        }
        //上边
        if (mTopBordStatus && x > width / 2 - mLineLong / 2 && x < width / 2 + mLineLong / 2 && y > 0 && y < mLineLong) {
            scale = true;
            return TOP_BORD;
        }
        //下边
        if (mBottomBordStatus && x > width / 2 - mLineLong / 2 && x < width / 2 + mLineLong / 2 && y > height - mLineLong && y < height) {
            scale = true;
            return BOTTOM_BORD;
        }
        //左边
        if (mLeftBordStatus && x > 0 && x < mLineLong && y > height / 2 - mLineLong / 2 && y < height / 2 + mLineLong / 2) {
            scale = true;
            return LEFT_BORD;
        }
        //右边
        if (mRightBordStatus && x > width - mLineLong && x < width && y > height / 2 - mLineLong / 2 && y < height / 2 + mLineLong / 2) {
            scale = true;
            return RIGHT_BORD;
        }
        return -1;
    }
}
