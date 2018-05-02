package com.haohao.switchbutton;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

/**
 * 滑动按钮
 *
 * @author haohao(ronghao3508@gmail.com) on 2016/5/1 12:33
 * @version v1.0
 */
public class SwitchButton extends View {

    //关闭状态
    public static final int SWITCH_OFF = 0;
    //打开状态
    public static final int SWITCH_ON = 1;
    //滚动状态
    public static final int SWITCH_SCROLING = 2;
    //不可用状态
    public static final int SWITCH_UNENABLE = 3;
    //当前状态
    private int switchStatus = SWITCH_OFF;

    //开关状态图
    private Bitmap mSwitchBg, mSwitch_thumb;

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

    private boolean isChange = false;
    private long TIMEOUT = 100;
    private boolean isTouchMove;//手按下移动
    private boolean isStartAnimation;//手抬起后播放动画
    private float MOVESTAP = 2f;

    private OnSwitchChangeListener mListener;
    private boolean isImmediately = true;
    private int switchBg = R.drawable.switch_bg;
    private int switchCursor = R.drawable.switch_white;
    private boolean isEnable = true;

    public SwitchButton(Context context) {
        this(context, null);
    }

    public SwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init();
    }

    public SwitchButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initAttrs(attrs);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {//方法是用来测量控价所占的大小
        setMeasuredDimension(mBgWidth, mBgHeight);//0 0自己定义控件的大小

        //在onMeasure(int, int)中，必须调用setMeasuredDimension(int width, int height)
        // 来存储测量得到的宽度和高度值，如果没有这么去做会触发异常IllegalStateException。
    }

    private void initAttrs(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.switchbutton);
            switchBg = a.getResourceId(R.styleable.switchbutton_switchbackground,
                    R.drawable.switch_bg);
            switchCursor =
                    a.getResourceId(R.styleable.switchbutton_switchcursor, R.drawable.switch_white);
            a.recycle();
        }
    }

    //初始化三幅图片
    private void init() {
        Resources res = getResources();
        mSwitchBg = BitmapFactory.decodeResource(res, switchBg);
        mSwitch_thumb = BitmapFactory.decodeResource(res, switchCursor);

        mBgWidth = mSwitchBg.getWidth();//背景宽度
        mBgHeight = mSwitchBg.getHeight();//背景高度
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
        if (!isEnable) return;
        isImmediately = false;
        switchStatus = (on ? SWITCH_ON : SWITCH_OFF);
        mMoveX = mBgWidth - mThumbWidth;
        if (switchStatus == 0) {
            isStartAnimation = true;
        }

        postInvalidate();//更新界面，重新绘制界面
    }

    /**
     * 设置开关的状态,第一次启动，快速打开无动画
     *
     * @param on 是否打开开关 打开为true 关闭为false
     */
    public void setStatusImmediately(boolean on) {
        if (!isEnable) return;
        isImmediately = true;
        switchStatus = (on ? SWITCH_ON : SWITCH_OFF);
        mMoveX = mBgWidth - mThumbWidth;
        if (switchStatus == SWITCH_OFF) {
            isStartAnimation = true;
        }

        postInvalidate();//更新界面，重新绘制界面
    }

    /**
     * 获取开关当前状态
     *
     * @return 0关闭 1打开 2不可用
     */
    public int getSwitchStatus() {
        if (!isEnable) {
            return SWITCH_UNENABLE;
        }
        return switchStatus;
    }

    /**
     * 设置事件监听
     *
     * @param mListener 事件监听接口
     */
    public void setOnSwitchChangeListener(OnSwitchChangeListener mListener) {
        this.mListener = mListener;
    }

    /**
     * 获取是否可用
     *
     * @return 可选择为true 不可选择为false
     */
    public boolean getSwitchEnabled() {
        return this.isEnable;
    }

    /**
     * 设置是否可用
     *
     * @param isEnable 可选择为true 不可选择为false
     */
    public void setSwitchEnabled(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public void toggle() {
        setStatus(getSwitchStatus() == SWITCH_OFF);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnable) return true;
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                isTouchMove = true;
                attemptClaimDrag();
                mSrcX = (int) event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                mDstX = event.getX();
                if (mSrcX == mDstX) {
                    return true;
                }
                mMoveX += mDstX - mSrcX;
                mSrcX = mDstX;
                break;
            case MotionEvent.ACTION_UP:
                isTouchMove = false;
                long time = event.getEventTime() - event.getDownTime();
                isStartAnimation = true;
                if (time < TIMEOUT) {
                    switchStatus = (switchStatus == SWITCH_OFF ? SWITCH_ON : SWITCH_OFF);
                } else {
                    if (event.getX() < mBgWidth / 2) {
                        switchStatus = SWITCH_OFF;
                    } else {
                        switchStatus = SWITCH_ON;
                    }
                }
                if (mListener != null) {
                    mListener.onSwitch(switchStatus);
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
        if (!isTouchMove) {
            if (!isImmediately && isStartAnimation) {
                if (switchStatus == SWITCH_OFF) {
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
                if (switchStatus == SWITCH_OFF) {
                    mMoveX = mMinMoveWidth;
                    isStartAnimation = false;
                } else {
                    mMoveX = mMaxMoveWidth;
                    isStartAnimation = false;
                }
                isImmediately = false;
                postInvalidate();
            }
        }

        //绘制底部图片
        if (isEnable) {
            ColorMatrix cm = new ColorMatrix();//颜色矩阵
            cm.setSaturation((mMoveX - mMinMoveWidth) / (mMaxMoveWidth - mMinMoveWidth));
            ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
            mPaintBg.setColorFilter(f);
            canvas.drawBitmap(mSwitchBg, 0, 0, mPaintBg);
            //Log.i("mMoveX", mMoveX + "");
            canvas.drawBitmap(mSwitch_thumb, mMoveX, mPadding, mPaint);
        } else {
            ColorMatrix cm = new ColorMatrix();//颜色矩阵
            cm.setSaturation(0.01f);
            ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
            mPaintBg.setColorFilter(f);
            mPaintBg.setAntiAlias(true);//边缘平滑处理
            canvas.drawBitmap(mSwitchBg, 0, 0, mPaintBg);
            //Log.i("mMoveX", mMoveX + "");
            canvas.drawBitmap(mSwitch_thumb, mMoveX, mPadding, mPaintBg);
        }
    }

    //阻止父组件对事件的响应，父组件不能响应 action包括action_up action_down action_move
    private void attemptClaimDrag() {
        ViewParent mParent = getParent();
        if (mParent != null) {
            mParent.requestDisallowInterceptTouchEvent(true);
        }
    }

    public interface OnSwitchChangeListener {
        /**
         * 状态变化监听
         *
         * @param status 1（SWITCH_ON）开启 0     （SWITCH_OFF）关闭
         */
        void onSwitch(int status);
    }
}