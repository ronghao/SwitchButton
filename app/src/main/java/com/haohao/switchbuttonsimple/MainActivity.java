package com.haohao.switchbuttonsimple;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;
import com.haohao.switchbutton.SwitchButton;

/**
 * 主界面
 *
 * @author haohao(ronghao3508@gmail.com) on 2018/5/1 15:10
 * @version v1.0
 */
public class MainActivity extends FragmentActivity {
    private SwitchButton mSwitchButton;
    private TextView mSwitchText;

    private SwitchButton mSwitchButton1;
    private TextView mSwitchText1;

    private SwitchButton mSwitchButton2;
    private TextView mSwitchText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian);
        init();
    }

    private void init() {
        mSwitchButton = (SwitchButton) findViewById(R.id.switchbutton);
        mSwitchButton.setStatusImmediately(true);
        mSwitchButton.setOnSwitchChangeListener(new SwitchButton.OnSwitchChangeListener() {
            @Override
            public void onSwitch(int status) {
                mSwitchText.setText(String.valueOf("状态:" + getStateStr(status)));
            }
        });
        mSwitchText = (TextView) findViewById(R.id.switchbutton_text);
        mSwitchText.setText(String.valueOf("状态:" + getStateStr(mSwitchButton.getSwitchStatus())));

        mSwitchButton1 = (SwitchButton) findViewById(R.id.switchbutton1);
        mSwitchButton1.setStatusImmediately(true);
        mSwitchButton1.setOnSwitchChangeListener(new SwitchButton.OnSwitchChangeListener() {
            @Override
            public void onSwitch(int status) {
                mSwitchText1.setText(String.valueOf("状态:" + getStateStr(status)));
                mSwitchButton2.setSwitchEnabled(status == 1);
                mSwitchText2.setText(
                        String.valueOf("状态:" + getStateStr(mSwitchButton2.getSwitchStatus())));
            }
        });
        mSwitchText1 = (TextView) findViewById(R.id.switchbutton1_text);
        mSwitchText1.setText(String.valueOf("状态:" + getStateStr(mSwitchButton.getSwitchStatus())));

        mSwitchButton2 = (SwitchButton) findViewById(R.id.switchbutton2);
        mSwitchButton2.setSwitchEnabled(false);
        mSwitchButton2.setStatusImmediately(true);
        mSwitchButton2.setOnSwitchChangeListener(new SwitchButton.OnSwitchChangeListener() {
            @Override
            public void onSwitch(int status) {
                mSwitchText2.setText(String.valueOf("状态:" + getStateStr(status)));
            }
        });

        mSwitchText2 = (TextView) findViewById(R.id.switchbutton2_text);
        mSwitchText2.setText(String.valueOf("状态:" + getStateStr(mSwitchButton2.getSwitchStatus())));
    }

    private String getStateStr(int status) {
        switch (status) {
            case SwitchButton.SWITCH_ON:
                return "开启";
            case SwitchButton.SWITCH_OFF:
                return "关闭";
            case SwitchButton.SWITCH_UNENABLE:
                return "禁用";
            default:
        }
        return "";
    }
}
