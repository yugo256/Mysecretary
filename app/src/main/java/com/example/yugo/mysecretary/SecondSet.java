package com.example.yugo.mysecretary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

// import static com.example.yugo.mysecretary.R.id.textView;

import android.widget.TimePicker;
import android.widget.DatePicker;

public class SecondSet extends AppCompatActivity implements
        TimePickerDialog.OnTimeSetListener,
        DatePickerDialog.OnDateSetListener {

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
                Date jikan = tmp0.getTime()
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
        } else {
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


    
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        TextView textView = (TextView) findViewById(R.id.textView4);
        textView.setText( String.valueOf(year)  + "/ " + String.valueOf(monthOfYear + 1) + "/ " + String.valueOf(dayOfMonth) );

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


    public void showDatePickerDialog(View v) {
        AppCompatDialogFragment newFragment = new DatePick();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }


    public void SaveIntervals_onClick (View v){
        EditText editTitle = (EditText)findViewById(R.id.editText2);
        EditText editIntervals = (EditText)findViewById(R.id.editText3);

        String title = editTitle.getText().toString();
        String string =editIntervals.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try{
            Date formatdate = sdf.parse(string);
            addSecondTimeintervalsDBs(title,formatdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void SavePoints_onClick (View v){
        Calendar fd1 = Calendar.getInstance();
        Calendar fd2 = Calendar.getInstance();
        TextView intervalDate = (TextView)findViewById(R.id.textView4);
        EditText editTitle = (EditText)findViewById(R.id.editText5);
        EditText editStart = (EditText)findViewById(R.id.editText6);
        EditText editFinish = (EditText)findViewById(R.id.editText8);
        Calendar tmpfd1Date = Calendar.getInstance();
        Calendar tmpfd2Date = Calendar.getInstance();

        SimpleDateFormat tmpSdf = new SimpleDateFormat("HH:mm");
        try {
            Date tmp1 = tmpSdf.parse(editStart.getText().toString());
            Date tmp2 = tmpSdf.parse(editFinish.getText().toString());
            tmpfd1Date.setTime(tmp1);
            tmpfd2Date.setTime(tmp2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String title = editTitle.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        try{
            Date formatdate1 = sdf.parse(intervalDate.getText().toString());
            Date formatdate2 = sdf.parse(intervalDate.getText().toString());
            fd1.setTime(formatdate1);
            fd1.set(Calendar.HOUR_OF_DAY, tmpfd1Date.get(Calendar.HOUR_OF_DAY));
            fd1.set(Calendar.MINUTE, tmpfd1Date.get(Calendar.MINUTE));
            fd2.setTime(formatdate2);
            fd2.set(Calendar.HOUR_OF_DAY, tmpfd2Date.get(Calendar.HOUR_OF_DAY));
            fd2.set(Calendar.MINUTE, tmpfd2Date.get(Calendar.MINUTE));
            addSecondTimepointsDBs(title, fd1.getTime(), fd2.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    //データベースの情報を取得する関数
    private RealmResults<SecondTimepointsDB> getSecondTimepointsDBs(){
        Realm realm = Realm.getInstance(this);
        RealmQuery<SecondTimepointsDB> query = realm.where(SecondTimepointsDB.class);
        return query.findAll();
    }

    //情報の追加
    private void addSecondTimepointsDBs(String title, Date start, Date finish) {
        Realm realm = Realm.getInstance(this);
        realm.beginTransaction();
        Number maxId = realm.where(SecondTimeintervalsDB.class).max("id");
        long nextId = 0;
        if(maxId != null) nextId = maxId.longValue() + 1;
        SecondTimepointsDB tmp = realm.createObject(SecondTimepointsDB.class);
        tmp.setId(nextId);
        tmp.setStart(start);
        tmp.setFinish(finish);
        tmp.setTitle(title);
        realm.commitTransaction();
    }
    /*データベースに代入
    private void createSecondTimepointsDBs(SecondTimepointsDB db) {
        SecondTimepointsDB model = new SecondTimepointsDB();
        Realm realm = Realm.getInstance(this);
        realm.beginTransaction();
        realm.copyToRealm(db);
        realm.commitTransaction();
    }*/

    private RealmResults<SecondTimeintervalsDB> getSecondTimeintervalsDBs(){
        Realm realm = Realm.getInstance(this);
        RealmQuery<SecondTimeintervalsDB> query = realm.where(SecondTimeintervalsDB.class);
        return query.findAll();
    }

    //情報の追加
    private void addSecondTimeintervalsDBs(String title, Date date) {
        Realm realm = Realm.getInstance(this);
        realm.beginTransaction();
        Number maxId = realm.where(SecondTimeintervalsDB.class).max("id");
        long nextId = 0;
        if(maxId != null) nextId = maxId.longValue() + 1;
        SecondTimeintervalsDB tmp = realm.createObject(SecondTimeintervalsDB.class);
        tmp.setId(nextId);
        tmp.setIntervals(date);
        tmp.setTitle(title);
        realm.commitTransaction();
    }

    /*private void createSecondTimeintervalsDBs(SecondTimeintervalsDB db) {
        SecondTimeintervalsDB model = new SecondTimeintervalsDB();
        Realm realm = Realm.getInstance(this);
        realm.beginTransaction();
        realm.copyToRealm(db);
        realm.commitTransaction();
    }*/

    private RealmResults<SleeptimeDB> getSleeptimeDBs() {
        Realm realm = Realm.getInstance(this);
        RealmQuery<SleeptimeDB> query = realm.where(SleeptimeDB.class);
        return query.findAll();
    }

    private void updateSleeptimeDBs(SleeptimeDB db) {
        long idx = db.getId();
        Date date = db.getTime();
        String title = db.getTitle();

        Realm realm = Realm.getInstance(this);
        SleeptimeDB tmp = sleeptimeDBs.get((int)idx);
        realm.beginTransaction();
        tmp.setTime(date);
        tmp.setTitle(title);
        realm.commitTransaction();
    }

    private void createSleeptimeDBs(SleeptimeDB db) {
        SleeptimeDB model = new SleeptimeDB();
        Realm realm = Realm.getInstance(this);
        realm.beginTransaction();
        realm.copyToRealm(db);
        realm.commitTransaction();
    }

    //ボタンクリック時に呼び出されるメソッド//
     public void SendtoDeletePlan_onClick(View v){
        //ListViewへのインテントを作成//
        Intent deleteplan = new Intent(this, DeletePlan.class);
        //アクティビティを起動
        startActivity(deleteplan);
    }

    public void SendtoDeletePlan2_onClick(View v){
        Intent deleteplan = new Intent(this, DeletePlan2.class);
        startActivity(deleteplan);
    }

}

