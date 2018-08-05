package com.morening.android.debugo;

import android.app.Activity;
import android.os.Bundle;

import com.morening.debugo.debugo_annotations.Sequence;

public class MainActivity extends Activity {

    @Override
    @Sequence
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initAllViews();
    }

    @Sequence
    private void initAllViews(){

    }
}
