package com.xinlan.bubble;

import com.xinlan.view.MainView;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;

public class MainActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(new MainView(this));
    }
}
