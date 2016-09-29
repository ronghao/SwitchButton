package com.haohao.switchbuttonsimple;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.haohao.switchbutton.SwitchButton;


/* *
* name:主界面
*/
public class MainActivity extends FragmentActivity {
    private SwitchButton mSwitchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mian);
        init();
    }

    private void init() {
        mSwitchButton = (SwitchButton) findViewById(R.id.switchbutton);
        mSwitchButton.setOnSwitchListener(new SwitchButton.IButtonClickListener() {
            @Override
            public void click(int status) {
                Toast.makeText(MainActivity.this, status == 1 ? "开启" : "关闭", Toast.LENGTH_SHORT);
            }
        });
    }


}
