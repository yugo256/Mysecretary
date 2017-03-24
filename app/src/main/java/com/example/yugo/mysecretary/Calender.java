package com.example.yugo.mysecretary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Calender extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);
    }
    //ボタンクリック時に呼び出されるメソッド//
    public void Sendtoschedule_onClick(View v){
        //scheduleへのインテントを作成
        Intent schedule = new Intent(this, Schedule.class);
        //アクティビティを起動
        startActivity(schedule);

    }
    //ボタンクリックく時に呼び出されるメソッド//
    public void SendtoSecondset_onClick(View v){
        //Secondsetへのインテントを作成
        Intent Secondset = new Intent(this, SecondSet.class);
        //アクティビティを起動
        startActivity(Secondset);

    }
}
