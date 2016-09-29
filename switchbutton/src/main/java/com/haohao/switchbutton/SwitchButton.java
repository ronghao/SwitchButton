package com.haohao.switchbutton;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;


public class SwitchButton extends View {
    public static final String TAG = "SwitchButton1";

    public static final int SWITCH_OFF = 0;//关闭状态
    public static final int SWITCH_ON = 1;//打开状态
    public static final int SWITCH_SCROLING = 2;//滚动状态
    //开关状态图
    Bitmap mSwitch_off, mSwitch_on, mSwitch_thumb;
    private int mSwitchStatus = SWITCH_OFF;
    private float mSrcX = 0f, mDstX = 0f;
    private float mMoveX = 0f;
    private int mBgWidth = 0;
    private int mBgHeight = 0;
    private int mThumbWidth = 0;
    private int mThumbHeight = 0;
    private int mMinMoveWidth = 0;
    private int mMaxMoveWidth = 0;
    private int mPadding = 0;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
    private Paint mPaintBg = new Paint(Paint.ANTI_ALIAS_FLAG);
    private ViewParent mParent;
    private boolean isChange = false;
    private long TIMEOUT = 100;
    private boolean isStartAnimation;//是否启动动画，
    private float MOVESTAP = 2f;

    private IButtonClickListener mListener;
    private boolean isShowAnimation = true;

    public SwitchButton(Context context) {
        this(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {//方法是用来测量控价所占的大小
        setMeasuredDimension((int) mBgWidth, (int) (mBgHeight));//0 0自己定义控件的大小

        //在onMeasure(int, int)中，必须调用setMeasuredDimension(int width, int height)
        // 来存储测量得到的宽度和高度值，如果没有这么去做会触发异常IllegalStateException。
    }

    //初始化三幅图片
    private void init() {
        Resources res = getResources();
        mSwitch_off = BitmapFactory.decodeResource(res, R.drawable.switch_bg);
        mSwitch_on = BitmapFactory.decodeResource(res, R.drawable.switch_bg);
        mSwitch_thumb = BitmapFactory.decodeResource(res, R.drawable.switch_white);

        mBgWidth = mSwitch_on.getWidth();//背景宽度
        mBgHeight = mSwitch_on.getHeight();//背景高度
        mThumbWidth = mSwitch_thumb.getWidth();
        mThumbHeight = mSwitch_thumb.getHeight();

        mPadding = (mBgHeight - mThumbHeight) / 2;
        mMinMoveWidth = mPadding;
        mMaxMoveWidth = mBgWidth - mThumbWidth - mPadding;
    }

    /**
     * 设置开关的状态
     *
     * @param on 是否打开开关 打开为true 关闭为false
     */
    public void setStatus(boolean on) {
        isShowAnimation = true;
        mSwitchStatus = (on ? SWITCH_ON : SWITCH_OFF);
        mMoveX = mBgWidth - mThumbWidth;
        if (mSwitchStatus == 0) {
            isStartAnimation = true;
        }

        postInvalidate();//更新界面，重新绘制界面
    }

    /**
     * 设置开关的状态
     *
     * @param on 是否打开开关 打开为true 关闭为false
     */
    public void setStatusimmediately(boolean on) {
        isShowAnimation = false;
        mSwitchStatus = (on ? SWITCH_ON : SWITCH_OFF);
        mMoveX = mBgWidth - mThumbWidth;
        if (mSwitchStatus == 0) {
            isStartAnimation = true;
        }

        postInvalidate();//更新界面，重新绘制界面
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                attemptClaimDrag();
                Log.e("pos_down", "X:" + event.getX());
                mSrcX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                mDstX = event.getX();
                Log.e("pos_move", "X:" + (mSrcX - mDstX));
                if (mSrcX == mDstX)
                    return true;
                mMoveX += mDstX - mSrcX;
                mSrcX = mDstX;
                break;
            case MotionEvent.ACTION_UP:
                Log.e("pos_up", "X:" + event.getX());
                long time = event.getEventTime() - event.getDownTime();
                isStartAnimation = true;
                if (time < TIMEOUT) {
                    mSwitchStatus = (mSwitchStatus == SWITCH_OFF ? SWITCH_ON : SWITCH_OFF);
                } else {
                    if (event.getX() < mBgWidth / 2) {
                        mSwitchStatus = SWITCH_OFF;
                    } else {
                        mSwitchStatus = SWITCH_ON;
                    }
                }
                if (mListener != null) {
                    mListener.click(mSwitchStatus);
                }
                break;
            default:
                break;
        }
        postInvalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 绘制按钮
        mMoveX = Math.min(mMoveX, mMaxMoveWidth);
        mMoveX = Math.max(mMoveX, mMinMoveWidth);
        if (isShowAnimation && isStartAnimation) {
            canvas.save();
            Log.e("onDrawisStartAnimation", isStartAnimation + "");
            Log.e("onDrawisStSwitchStatus", mSwitchStatus + "");
            if (mSwitchStatus == SWITCH_OFF) {
                if (mMoveX > mMinMoveWidth) {
                    float i = (mMoveX - mMinMoveWidth) / 4;
                    float move = Math.max(i, MOVESTAP);
                    mMoveX -= move;
                } else {
                    mMoveX = mMinMoveWidth;
                    isStartAnimation = false;
                }
            } else {
                if (mMoveX < mMaxMoveWidth) {
                    float i = (mMaxMoveWidth - mMoveX) / 4;
                    float move = Math.max(i, MOVESTAP);
                    mMoveX += move;

                } else {
                    mMoveX = mMaxMoveWidth;
                    isStartAnimation = false;
                }
            }
            postInvalidate();
        } else {
            if (mSwitchStatus == SWITCH_OFF) {
                mMoveX = mMinMoveWidth;
                isStartAnimation = false;
            } else {
                mMoveX = mMaxMoveWidth;
                isStartAnimation = false;
            }
            postInvalidate();
        }

        //绘制底部图片

        ColorMatrix cm = new ColorMatrix();//颜色矩阵
        cm.setSaturation((mMoveX - mMinMoveWidth) / (mMaxMoveWidth - mMinMoveWidth));
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        mPaintBg.setColorFilter(f);
        canvas.drawBitmap(mSwitch_on, 0, 0, mPaintBg);

        canvas.drawBitmap(mSwitch_thumb, mMoveX, mPadding, mPaint);

        if (isShowAnimation && isStartAnimation){
            canvas.restore();
        }
    }

    //阻止父组件对事件的响应，父组件不能响应 action包括action_up action_down action_move
    private void attemptClaimDrag() {
        mParent = getParent();
        if (mParent != null) {
            mParent.requestDisallowInterceptTouchEvent(true);
        }
    }

    public int getmSwitchStatus() {
        return mSwitchStatus;
    }

    public void setOnSwitchListener(IButtonClickListener mListener) {
        this.mListener = mListener;
    }

    public interface IButtonClickListener {
        void click(int status);
    }
}