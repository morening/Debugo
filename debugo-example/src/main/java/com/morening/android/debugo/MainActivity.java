package com.morening.android.debugo;

import android.app.Activity;
import android.os.Bundle;

import com.morening.debugo.debugo_annotations.Debugo;

@Debugo
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        returnVoid();
        returnString("Debugo");
        returnStaff("Jack", 21);
        try {
            returnThrowable();
        }catch (Exception e){

        }
    }

    private void returnVoid(){
    }

    private String returnString(String str){
        return String.format("Hello [%s]!", str);
    }

    private Staff returnStaff(String name, int age){

        return new Staff(name, age);
    }

    private void returnThrowable(){
        int wrong = 1 / 0;
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
