package h5.feinno.com.mydragview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * <pre>
 *     author : zhonghang
 *     e-mail : zhonghang@feinno.com
 *     time   : 2017/06/21 15:14
 *     desc   : 用于拖动放大缩小的控件,不是一个View,只是一个承载绘制的内容的实体，负责绘制其中的内容，不可以单独使用，必须在DragViewContainer中使用
 *     @see DragViewContainer
 *     version: 1.0
 * </pre>
 */
public abstract class DragView {
    /**
     * 组件的绘制方法
     *
     * @param canvas 绘制时的画布，由DragViewContainer传递过来
     */
    public abstract void draw(Canvas canvas);

    /**
     *
     * @return
     */
    public abstract  boolean contains(PointF pointF);
}
