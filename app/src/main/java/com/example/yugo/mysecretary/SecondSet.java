package com.example.yugo.mysecretary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

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
        if (sleeptimeDBs != null && sleeptimeDBs.size() != 0) {
            SleeptimeDB tmp0 = sleeptimeDBs.get(0);
            SleeptimeDB tmp1 = sleeptimeDBs.get(1);
            if (tmp0.getTime() != null) {
                Date jikan = tmp0.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String s = sdf.format(jikan);
                TextView textView = (TextView) findViewById(R.id.textView17);
                textView.setText(s + "分");
            } else {
                TextView textView = (TextView) findViewById(R.id.textView17);
                textView.setText("00:00");
            }
            if (tmp1.getTime() != null) {
                Date jikan = tmp1.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                String s = sdf.format(jikan);
                TextView textView = (TextView) findViewById(R.id.textView19);
                textView.setText(s + "分");
            } else {
                TextView textView = (TextView) findViewById(R.id.textView19);
                textView.setText("00:00");
            }
        } else {
            TextView textView0 = (TextView) findViewById(R.id.textView17);
            TextView textView1 = (TextView) findViewById(R.id.textView19);

            textView0.setText("00:00");
            textView1.setText("00:00");

        }

    }

    //起床就寝時間の保存
    public void SaveSleep_onClick (View v) throws ParseException {
        EditText editText = (EditText)findViewById(R.id.editText9);
        Spinner spinner = (Spinner)findViewById(R.id.spinner3);
        int idx = spinner.getSelectedItemPosition();
        SleeptimeDB sleeptimeDB = new SleeptimeDB();

        if(sleeptimeDBs == null || sleeptimeDBs.size() == 0){
            sleeptimeDB.setId(0);
            sleeptimeDB.setTitle("起床");
            createSleeptimeDBs(sleeptimeDB);
            sleeptimeDB.setId(1);
            sleeptimeDB.setTitle("就寝");
            createSleeptimeDBs(sleeptimeDB);
        }

        String string = editText.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try{
            Date formatdate = sdf.parse(string);
            TextView textView = null;
            if (idx == 0){
                sleeptimeDB.setTime(formatdate);
                sleeptimeDB.setId(0);
                sleeptimeDB.setTitle("起床");
                textView = (TextView)findViewById(R.id.textView17);
            }else if(idx == 1){
                sleeptimeDB.setTime(formatdate);
                sleeptimeDB.setId(1);
                sleeptimeDB.setTitle("就寝");
                textView = (TextView)findViewById(R.id.textView19);
            }else;
            updateSleeptimeDBs(sleeptimeDB);
            textView.setText(string);
        }catch (ParseException e){

        }
        editText.getEditableText().clear();
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
        EditText editTitle = (EditText)findViewById(R.id.editText5);
        EditText editStart = (EditText)findViewById(R.id.editText6);
        EditText editFinish = (EditText)findViewById(R.id.editText8);

        String title = editTitle.getText().toString();
        String string1 =editStart.getText().toString();
        String string2 =editFinish.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        try{
            Date formatdate1 = sdf.parse(string1);
            Date formatdate2 = sdf.parse(string2);
            addSecondTimepointsDBs(title,formatdate1,formatdate2);
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

