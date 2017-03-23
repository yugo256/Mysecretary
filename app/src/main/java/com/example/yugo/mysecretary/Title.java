package com.example.yugo.mysecretary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class Title extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        final TextView textView = (TextView)findViewById(R.id.textView1);


    }
    //ボタンクリック時に呼び出されるメソッド//
    public void SendtoKalender_onClick(View v){
        //Kalendarへのインテントを作成//
        Intent i = new Intent(this, Calender.class);
        //アクティビティを起動//
        startActivity(i);

    }
    //ボタンクリック時に呼び出されるメソッド//
    public void Sendtofirstset_onClick(View v){
        //firstsetへのインテントを作成//
        Intent firstset = new Intent(this, FirstSet.class);
        //アクティビティを起動
        startActivity(firstset);

    }
}