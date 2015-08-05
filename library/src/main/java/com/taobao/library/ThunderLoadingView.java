package com.taobao.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by rowandjj on 15/8/5.
 */
public class ThunderLoadingView extends View {

    private Path mThunderPath;

    private Paint mPaint;

    /**闪电的背景色*/
    private int mBackgroundColor = 0xfff96c0e;
    /**闪电的覆盖色*/
    private int mCoverColor = 0xff2d6de1;
    /**圆角矩形的背景色*/
    private int mViewBackground = Color.WHITE;

    private final float DEFAULT_WIDTH = dp2px(40);
    private final float DEFAULT_HEIGHT = dp2px(60);
    private final float DEFAULT_VIEW_SIZE = dp2px(70);


    /**闪电的宽高*/
    private float mDefaultWidth = DEFAULT_WIDTH;
    private float mDefaultHeight = DEFAULT_HEIGHT;
    /**闪电后面的圆角矩形的宽高*/
    private float mViewMinWidth = DEFAULT_VIEW_SIZE;
    private float mViewMinHeight = DEFAULT_VIEW_SIZE;

    private int mScanTop,mScanBottom;

    private RectF mBounds;

    private Size mSize = Size.MEDIUM;

    private Runnable mRunnable;

    private int mGap = 10;


    public enum Size{
        SMALL,MEDIUM,LARGE
    }

    public ThunderLoadingView(Context context) {
        super(context);
        init();
    }

    public ThunderLoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        parseAttributes(context, attrs);

        init();
    }

    private void parseAttributes(Context context,AttributeSet attrs) {
        //暂时只有一个自定义属性，大小。 至于color那些可以自行添加
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ThunderLoadingView);
        int size = array.getInt(R.styleable.ThunderLoadingView_thunder_size,0);
        switch (size){
            case 0:
                mSize = Size.SMALL;
                break;
            case 1:
                mSize = Size.MEDIUM;
                break;
            case 2:
                mSize = Size.LARGE;
                break;
        }
        array.recycle();
    }

    boolean flag = false;
    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setStyle(Paint.Style.FILL);
        initConfig();
        startAnim();
    }

    private void initConfig() {

        switch (mSize){
            case LARGE:
                mViewMinWidth = mViewMinHeight = DEFAULT_VIEW_SIZE;
                mDefaultWidth =  DEFAULT_WIDTH;
                mDefaultHeight = DEFAULT_HEIGHT;
                mGap = 7;
                break;
            case MEDIUM:
                mViewMinWidth = mViewMinHeight = DEFAULT_VIEW_SIZE*3/4;
                mDefaultWidth =  DEFAULT_WIDTH*3/4;
                mDefaultHeight = DEFAULT_HEIGHT*3/4;
                mGap = 5;
                break;
            case SMALL:
                mViewMinWidth = mViewMinHeight =DEFAULT_VIEW_SIZE/2;
                mDefaultWidth = DEFAULT_WIDTH/2;
                mDefaultHeight = DEFAULT_HEIGHT/2;
                mGap = 3;
                break;
        }

    }

    private void startAnim() {
        mRunnable = new AnimRunnable();
        post(mRunnable);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getHandler().removeCallbacks(mRunnable);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //这里的参数w h 值得是当前的getWidth/getHeight返回值，对于我们并没有用

        initPath();

        if(getWidth() < (int)mViewMinWidth || getHeight() < (int)mViewMinHeight){//注意类型保持一致
            throw new RuntimeException("we suggest you use wrap_content for best performance");
        }
        //圆角矩形的边界
        mBounds = new RectF(getPaddingLeft(), getPaddingTop(),mViewMinWidth+getPaddingLeft(), mViewMinHeight+getPaddingTop());//0 0 70*3 70*3
    }

    private void initPath() {
        switch (mSize){
            case LARGE:
                largeThunder();
                break;
            case MEDIUM:
                mediumThunder();
                break;
            case SMALL:
                smallThunder();
                break;
        }
    }


    //TODO 此处可以优化，通过thunder(ratio)方法控制thunder大小即可，不用写三个函数
    private void smallThunder(){
        mThunderPath = new Path();
        mThunderPath.moveTo(dp2px(35) / 2 + getPaddingLeft(), 0f + getPaddingTop());
        mThunderPath.lineTo(0f + getPaddingLeft(), dp2px(35) / 2 + getPaddingTop());
        mThunderPath.lineTo(dp2px(17.5f) / 2 + getPaddingLeft(), dp2px(35) / 2 + getPaddingTop());
        mThunderPath.lineTo(dp2px(5f)/2+getPaddingLeft(), dp2px(60)/2+getPaddingTop());
        mThunderPath.lineTo(dp2px(40)/2+getPaddingLeft(), dp2px(25f)/2+getPaddingTop());
        mThunderPath.lineTo(dp2px(22.5f) / 2 + getPaddingLeft(), dp2px(25f) / 2 + getPaddingTop());
        mThunderPath.close();
    }

    private void mediumThunder(){
        mThunderPath = new Path();
        mThunderPath.moveTo(dp2px(35)*3/4 + getPaddingLeft(), 0f + getPaddingTop());
        mThunderPath.lineTo(0f + getPaddingLeft(), dp2px(35)*3/4 + getPaddingTop());
        mThunderPath.lineTo(dp2px(17.5f)*3/4+getPaddingLeft(), dp2px(35)*3/4+getPaddingTop());
        mThunderPath.lineTo(dp2px(5f)*3/4+getPaddingLeft(), dp2px(60)*3/4+getPaddingTop());
        mThunderPath.lineTo(dp2px(40)*3/4+getPaddingLeft(), dp2px(25f)*3/4+getPaddingTop());
        mThunderPath.lineTo(dp2px(22.5f)*3/4 + getPaddingLeft(), dp2px(25f)*3/4 + getPaddingTop());
        mThunderPath.close();
    }

    private void largeThunder(){
        mThunderPath = new Path();
        mThunderPath.moveTo(dp2px(35) + getPaddingLeft(), 0f + getPaddingTop());//35 0
        mThunderPath.lineTo(0f + getPaddingLeft(), dp2px(35) + getPaddingTop());//0 35
        mThunderPath.lineTo(dp2px(17.5f)+getPaddingLeft(), dp2px(35)+getPaddingTop());//17.5 35
        mThunderPath.lineTo(dp2px(5f)+getPaddingLeft(), dp2px(60)+getPaddingTop());//5 60
        mThunderPath.lineTo(dp2px(40)+getPaddingLeft(), dp2px(25f)+getPaddingTop());//40 25
        mThunderPath.lineTo(dp2px(22.5f) + getPaddingLeft(), dp2px(25f) + getPaddingTop());//22.5 25
        mThunderPath.close();
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //需要计算自己实际需要的宽高
        //需要把padding考虑进来
        //需要考虑父容器的测量规则

        int width,height;

        width = (int)mViewMinWidth+getPaddingLeft()+getPaddingRight();
        height = (int)mViewMinHeight+getPaddingTop()+getPaddingBottom();

        setMeasuredDimension(getMeasuredSize(widthMeasureSpec, width), getMeasuredSize(heightMeasureSpec, height));
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(mViewBackground);
        //如果xml中设置layout_width/layout_height大于默认宽高，那么居中(不允许小于默认宽高)
        if(getWidth()-getPaddingLeft()-getPaddingRight() > (int)mViewMinWidth || getHeight()-getPaddingTop()-getPaddingBottom() > (int)mViewMinHeight){
           canvas.translate((getWidth()-mViewMinWidth)/2.0f,(getHeight()-mViewMinHeight)/2.0f);
        }
        //画圆角矩形
        canvas.drawRoundRect(mBounds, dp2px(5), dp2px(5), mPaint);
        //平移到圆角矩形中心点，画闪电
        canvas.translate((mViewMinWidth - mDefaultWidth) / 2.0f, (mViewMinHeight - mDefaultHeight) / 2.0f);
        mPaint.setColor(mBackgroundColor);

        canvas.drawPath(mThunderPath, mPaint);
        mPaint.setColor(mCoverColor);
        //通过clicpRect的方式控制可绘制区域(在外界看来好像有闪动的动画效果)
        canvas.clipRect(getPaddingLeft(), mScanTop + getPaddingTop(), mDefaultWidth + getPaddingLeft(), mScanBottom + getPaddingTop());
        canvas.drawPath(mThunderPath, mPaint);
    }


    private float dp2px(float dp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dp,getResources().getDisplayMetrics());
    }

    private int getMeasuredSize(int measureSpec,int desiredSize){

        int result;

        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);

        switch (mode){
            case MeasureSpec.EXACTLY:
                result = size;
                break;
            default:
                result = desiredSize;
                if(mode == MeasureSpec.AT_MOST)
                    result = Math.min(result,size);
                break;
        }

        return result;

    }

    public void setSize(Size size){
        this.mSize = size;
        initConfig();
    }

    class AnimRunnable implements Runnable{
        @Override
        public void run() {
            if (!flag) {
                mScanBottom += mGap;
                if (mScanBottom >= mDefaultHeight) {
                    mScanBottom = (int) mDefaultHeight;
                    flag = true;
                }
                postInvalidate();
                post(this);
            } else {
                mScanTop += mGap;
                if (mScanTop >= mDefaultHeight) {
                    mScanTop = mScanBottom = 0;
                    flag = false;
                    postInvalidate();
                    postDelayed(this, 700);
                } else {
                    postInvalidate();
                    post(this);
                }
            }
        }
    }

}
















