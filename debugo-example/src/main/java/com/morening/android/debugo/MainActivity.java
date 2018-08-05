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
    }

    @Override
    @Sequence
    protected void onStart() {
        super.onStart();
    }

    @Override
    @Sequence
    protected void onResume() {
        super.onResume();
    }

    @Override
    @Sequence
    protected void onPause() {
        super.onPause();
    }

    @Override
    @Sequence
    protected void onStop() {
        super.onStop();
    }

    @Override
    @Sequence
    protected void onDestroy() {
        super.onDestroy();
    }
}
