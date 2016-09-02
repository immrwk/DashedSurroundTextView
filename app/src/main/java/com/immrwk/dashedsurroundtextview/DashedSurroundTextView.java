package com.immrwk.dashedsurroundtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2016/9/2 0002.
 */


public class DashedSurroundTextView extends View {

    /**
     * mTextColor  文字颜色   mBorderColor 虚线边界颜色  mBorderWidth虚线边界宽度
     * mTextSizen  文字大小   mText  文字内容
     */
    private int mTextColor;
    private int mBorderColor;
    private float mBorderWidth;
    private float mTextSize;
    private String mText;

    private float start_x = 0;
    private float start_y = 0;
    private float padding = 5;

    private float baseLineLong = 10;
    private float radiusX = 20;
    private float radiusY = 20;

    public DashedSurroundTextView(Context context) {
        super(context);
    }

    public DashedSurroundTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //获取自定义参数
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.DashedSurroundTextView);
        //虚线border颜色
        mBorderColor = typedArray.getColor(R.styleable.DashedSurroundTextView_border_color, Color.argb(0, 0, 0, 0));
        //虚线border宽度
        mBorderWidth = typedArray.getDimension(R.styleable.DashedSurroundTextView_border_width, 0);
        //字体颜色
        mTextColor = typedArray.getColor(R.styleable.DashedSurroundTextView_textColor, Color.argb(0, 0, 0, 0));
        //字体大小
        mTextSize = typedArray.getDimension(R.styleable.DashedSurroundTextView_textSize, 0);
        //显示文字
        mText = typedArray.getString(R.styleable.DashedSurroundTextView_text);
    }

    public DashedSurroundTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

//    public DashedSurroundTextview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //创建文字画笔
        Paint textP = new Paint();
        textP.setColor(mTextColor);
        textP.setStyle(Paint.Style.FILL);
        textP.setTextSize(mTextSize);

        //StrokeWidth无需设置
        //textP.setStrokeWidth();

        //计算文字所占位置的大小
        Rect bounds = new Rect();
        textP.getTextBounds(mText, 0, mText.length(), bounds);
        canvas.drawText(mText, start_x, start_y + bounds.height() - padding, textP);

        //创建边界虚线画笔
        Paint borderP = new Paint();
        borderP.setColor(mBorderColor);
        borderP.setStyle(Paint.Style.FILL_AND_STROKE);
        borderP.setStrokeWidth(mBorderWidth);
        borderP.setAntiAlias(true);

        //画外面虚线边界
        RectF oval3 = new RectF(start_x, start_y, start_x + bounds.width() + padding * 2, start_y + bounds.height() + padding * 2);
        PathEffect effects = new DashPathEffect(new float[]{baseLineLong, baseLineLong}, 0);
        borderP.setPathEffect(effects);
        canvas.drawRoundRect(oval3, radiusX, radiusY, borderP);
    }


    //解决wrap_content问题
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int desiredWidth = 500;
        int desiredHeight = 500;

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        //Measure Width
        if (widthMode == MeasureSpec.EXACTLY) {
            //Must be this size
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            width = Math.min(desiredWidth, widthSize);
        } else {
            //Be whatever you want
            width = desiredWidth;
        }

        //Measure Height
        if (heightMode == MeasureSpec.EXACTLY) {
            //Must be this size
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            height = Math.min(desiredHeight, heightSize);
        } else {
            //Be whatever you want
            height = desiredHeight;
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height);
    }

}

