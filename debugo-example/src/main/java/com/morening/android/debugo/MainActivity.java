package com.morening.android.debugo;

import android.app.Activity;
import android.os.Bundle;

import com.morening.debugo.debugo_annotations.Debugo;

public class MainActivity extends Activity {

    @Override
    @Debugo
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getStaff();
    }

    @Override
    @Debugo
    protected void onStart() {
        super.onStart();
    }

    @Override
    @Debugo
    protected void onResume() {
        super.onResume();
    }

    @Override
    @Debugo
    protected void onPause() {
        super.onPause();
    }

    @Override
    @Debugo
    protected void onStop() {
        super.onStop();
    }

    @Override
    @Debugo
    protected void onDestroy() {
        super.onDestroy();
    }

    @Debugo
    private Staff getStaff(){

        return new Staff("Tom", 21);
    }

    static class Staff{
        String name;
        int age;

        public Staff(String name, int age){
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return String.format("Name: %s, Age: %s", name, age);
        }
    }
}
