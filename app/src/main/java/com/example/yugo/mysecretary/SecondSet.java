package com.example.yugo.mysecretary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

import static com.example.yugo.mysecretary.R.id.textView;

public class SecondSet extends AppCompatActivity {
    RealmResults<SecondTimepointsDB> secondTimepointsDBs = null;
    RealmResults<SecondTimeintervalsDB> secondTimeintervalsDBs = null;
    RealmResults<SleeptimeDB> sleeptimeDBs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondset);
        secondTimepointsDBs = getSecondTimepointsDBs();
        secondTimeintervalsDBs = getSecondTimeintervalsDBs();
        sleeptimeDBs = getSleeptimeDBs();

        //起床・就寝時間の表示
        if(sleeptimeDBs != null && sleeptimeDBs.size() != 0) {
            SleeptimeDB tmp0 = sleeptimeDBs.get(0);
            SleeptimeDB tmp1 = sleeptimeDBs.get(1);
            if (tmp0.getTime() != null) {
                Date jikan = tmp0.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("mm");
                String s = sdf.format(jikan);
                TextView textView = (TextView) findViewById(R.id.textView17);
                textView.setText(s + "分");
            } else {
                TextView textView = (TextView) findViewById(R.id.textView17);
                textView.setText(00 + "分");
            }
            if (tmp1.getTime() != null) {
                Date jikan = tmp1.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("mm");
                String s = sdf.format(jikan);
                TextView textView = (TextView) findViewById(R.id.textView19);
                textView.setText(s + "分");
            } else {
                TextView textView = (TextView) findViewById(R.id.textView19);
                textView.setText(00 + "分");
            }
        }else{
            TextView textView0 = (TextView) findViewById(R.id.textView17);
            TextView textView1 = (TextView) findViewById(R.id.textView19);

            textView0.setText(00 + "分");
            textView1.setText(00 + "分");

        }



    }

    private RealmResults<SecondTimepointsDB> getSecondTimepointsDBs(){

        Realm realm = Realm.getInstance(this);
        RealmQuery<SecondTimepointsDB> query = realm.where(SecondTimepointsDB.class);

        return query.findAll();

    }

    private RealmResults<SecondTimeintervalsDB> getSecondTimeintervalsDBs(){
        Realm realm = Realm.getInstance(this);
        RealmQuery<SecondTimeintervalsDB> query = realm.where(SecondTimeintervalsDB.class);
        return query.findAll();
    }

    private RealmResults<SleeptimeDB> getSleeptimeDBs() {
        Realm realm = Realm.getInstance(this);
        RealmQuery<SleeptimeDB> query = realm.where(SleeptimeDB.class);
        return query.findAll();
    }

    //ボタンクリック時に呼び出されるメソッド//
     public void SendtoListView_onClick(View v){
        //ListViewへのインテントを作成//
        Intent ListView = new Intent(this, ListView.class);
        //アクティビティを起動
        startActivity(ListView);
    }


}

