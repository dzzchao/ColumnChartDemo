package com.dzzchao.columnchartdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 圆柱图
 * Created by Hankkin on 16/12/11.
 */
public class ColumnChartView extends View {

    private static final String LOGTAG = "SingleView";
    private Paint mPaint, mChartPaint;
    private Rect mBound;
    private int mHeight;
    private int mStartWidth;
    private int mChartWidth;
    private int lineColor, leftColor, lefrColorBottom;
    private List<Float> list = new ArrayList<>();
    private RectF rectF;

    public void setList(List<Float> list) {
        this.list = list;
    }

    public ColumnChartView(Context context) {
        this(context, null);
    }

    public ColumnChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColumnChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ColumnChartView, defStyleAttr, 0);
        int n = array.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                case R.styleable.ColumnChartView_xycolor:
                    lineColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.ColumnChartView_color:
                    leftColor = array.getColor(attr, Color.BLACK);
                    break;
                case R.styleable.ColumnChartView_colorBottom:
                    lefrColorBottom = array.getColor(attr, Color.BLACK);
                    break;
                default:
                    bringToFront();
            }
        }
        array.recycle();

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mBound = new Rect();
        mChartPaint = new Paint();
        mChartPaint.setAntiAlias(true);
        rectF = new RectF();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));

        mHeight = getHeight();
        //距离最左边的距离，一般在xml中会配置，所以直接设置为0
        mStartWidth = 0;
        //每个圆柱的宽度，假如需要7个圆柱，就需要 2 * 7 - 1 = 13 个空隙，也就是一个圆柱的宽度是 View 的宽度 / 13
        mChartWidth = getWidth() / 13;
    }


    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            mStartWidth = getWidth() / 13;
            mChartWidth = getWidth() / 13;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d(LOGTAG, "onDraw()");
        super.onDraw(canvas);
        mPaint.setColor(lineColor);
        for (int i = 0; i < 7; i++) {
            //画数字
            mPaint.setTextSize(35);
            mPaint.setTextAlign(Paint.Align.CENTER);
            //计算文字宽度
            mPaint.getTextBounds("0" + String.valueOf(i + 1), 0, String.valueOf(i).length(), mBound);
            canvas.drawText("0" + String.valueOf(i + 1), mStartWidth + mChartWidth / 2, mHeight - 60 + mBound.height() / 2, mPaint);
            int size = (mHeight - 100) / 100;
            //画圆柱
            mChartPaint.setStyle(Paint.Style.FILL);
            if (list.size() > 0) {
                //渐变
                LinearGradient lg = new LinearGradient(mChartWidth / 2,
                        mHeight - 100,
                        mChartWidth / 2,
                        mHeight - 100 - list.get(i) * size,
                        lefrColorBottom,
                        leftColor,
                        Shader.TileMode.CLAMP);
                mChartPaint.setShader(lg);
                //画柱状图
                rectF.left = mStartWidth;
                rectF.right = mStartWidth + mChartWidth;
                rectF.bottom = mHeight - 100;
                rectF.top = mHeight - 100 - list.get(i) * size;
                canvas.drawRoundRect(rectF, mChartWidth / 2, mChartWidth / 2, mChartPaint);
                mStartWidth += mChartWidth * 2;
            }
        }
    }
}