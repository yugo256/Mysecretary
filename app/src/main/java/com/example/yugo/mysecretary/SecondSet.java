package com.example.yugo.mysecretary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.app.TimePickerDialog;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

// import static com.example.yugo.mysecretary.R.id.textView;

import android.widget.TimePicker;

public class SecondSet extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
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
        if(sleeptimeDBs != null && sleeptimeDBs.size() >= 2) {
            SleeptimeDB tmp0 = sleeptimeDBs.get(0);
            SleeptimeDB tmp1 = sleeptimeDBs.get(1);
            if (tmp0.getTime() != null) {
                Date jikan = tmp0.getTime();
                setSleeptimeTextView(0, jikan);
            } else {
                TextView textView = (TextView) findViewById(R.id.textView17);
                textView.setText("未設定");
            }
            if (tmp1.getTime() != null) {
                Date jikan = tmp1.getTime();
                setSleeptimeTextView(1, jikan);
            } else {
                TextView textView = (TextView) findViewById(R.id.textView19);
                textView.setText("未設定");
            }
        }else{
            TextView textView0 = (TextView) findViewById(R.id.textView17);
            TextView textView1 = (TextView) findViewById(R.id.textView19);
            textView0.setText("未設定");
            textView1.setText("未設定");
        }

    }

    public void setSleeptimeTextView(int idx, Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("h:mm a");
        String s = sdf.format(date);
        if (idx==0) {
            TextView textView = (TextView) findViewById(R.id.textView17);
            textView.setText(s);
        }
        if (idx==1) {
            TextView textView = (TextView) findViewById(R.id.textView19);
            textView.setText(s);
        }
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Spinner spinner = (Spinner)findViewById(R.id.spinner3);
        TextView textView = (TextView)findViewById(R.id.textView4);

        int idx = spinner.getSelectedItemPosition();
        SleeptimeDB sleeptimeDB = new SleeptimeDB();

        Calendar sleeptime = Calendar.getInstance();
        sleeptime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        sleeptime.set(Calendar.MINUTE, minute);
        sleeptimeDB.setId(idx);
        sleeptimeDB.setTime(sleeptime.getTime());

        try {
            sleeptimeDBs.get(idx);
            updateSleepTime(sleeptimeDB);
        } catch (ArrayIndexOutOfBoundsException e) {
            createSleepTime(sleeptimeDB);
        }

        setSleeptimeTextView(idx, sleeptime.getTime());
    }

    private void updateSleepTime(SleeptimeDB db) {
        long idx = db.getId();
        Date date = db.getTime();
        Realm realm = Realm.getInstance(this);
        SleeptimeDB tmp = sleeptimeDBs.get((int)idx);
        realm.beginTransaction();
        tmp.setTime(date);
        realm.commitTransaction();
    }

    private void createSleepTime(SleeptimeDB db) {
        SleeptimeDB model = new SleeptimeDB();
        Realm realm = Realm.getInstance(this);
        realm.beginTransaction();
        realm.copyToRealm(db);
        realm.commitTransaction();
    }

    public void showTimePickerDialog(View v) {
        AppCompatDialogFragment newFragment = new TimePick();
        newFragment.show(getSupportFragmentManager(), "timePicker");
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

