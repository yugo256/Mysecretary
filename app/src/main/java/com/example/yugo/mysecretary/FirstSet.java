package com.example.yugo.mysecretary;

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

import static com.example.yugo.mysecretary.R.id.textView11;

public class FirstSet extends AppCompatActivity {
    RealmResults<FirstsetDB> results = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstset);
        results = getTestData();

//初期情報の表示
        if(results != null && results.size() != 0) {
            FirstsetDB tmp0 = results.get(0);
            FirstsetDB tmp1 = results.get(1);
            FirstsetDB tmp2 = results.get(2);
            FirstsetDB tmp3 = results.get(3);
            if (tmp0.getDate() != null) {
                Date jikan0 = tmp0.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("mm");
                String s0 = sdf.format(jikan0);
                TextView textView0 = (TextView) findViewById(R.id.textView9);
                textView0.setText(s0 + "分");
            }else {
                TextView textView0 = (TextView) findViewById(R.id.textView9);
                textView0.setText( 00 + "分");
            }
            if (tmp1.getDate() != null) {
                Date jikan1 = tmp1.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("mm");
                String s1 = sdf.format(jikan1);
                TextView textView1 = (TextView) findViewById(textView11);
                textView1.setText(s1 + "分");
            }else{
                TextView textView1 = (TextView) findViewById(textView11);
                textView1.setText( 00 + "分");
            }
            if (tmp2.getDate() != null) {
                Date jikan2 = tmp2.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("mm");
                String s2 = sdf.format(jikan2);
                TextView textView2 = (TextView) findViewById(R.id.textView13);
                textView2.setText(s2 + "分");
            }else{
                TextView textView2 = (TextView) findViewById(R.id.textView13);
                textView2.setText( 00 + "分");
            }
            if (tmp3.getDate() != null) {
                Date jikan3 = tmp3.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("mm");
                String s3 = sdf.format(jikan3);
                TextView textView3 = (TextView) findViewById(R.id.textView15);
                textView3.setText(s3 + "分");
            }else{
                TextView textView3 = (TextView) findViewById(R.id.textView15);
                textView3.setText( 00 + "分");
            }
        }else{

            TextView textView0 = (TextView) findViewById(R.id.textView9);
            TextView textView1 = (TextView) findViewById(R.id.textView11);
            TextView textView2 = (TextView) findViewById(R.id.textView13);
            TextView textView3 = (TextView) findViewById(R.id.textView15);

            textView0.setText( 00 + "分");
            textView1.setText( 00 + "分");
            textView2.setText( 00 + "分");
            textView3.setText (00 + "分");

        }

    }
    //ボタンクリック時に呼び出されるメソッド
    public void TitleBack_onClick(View v){
        EditText editText = (EditText)findViewById(R.id.editText);
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        int idx = spinner.getSelectedItemPosition();
        FirstsetDB firstsetDB = new FirstsetDB();

        if(results == null || results.size() == 0){
            //results = new  RealmResults<FirstsetDB>();
            firstsetDB.setId(0);
            firstsetDB.setTitle("朝食");
            updateTestData(firstsetDB);
            firstsetDB.setId(1);
            firstsetDB.setTitle("昼食");
            updateTestData(firstsetDB);
            firstsetDB.setId(2);
            firstsetDB.setTitle("夕食");
            updateTestData(firstsetDB);
            firstsetDB.setId(3);
            firstsetDB.setTitle("風呂");
            updateTestData(firstsetDB);

        }else{
        }

        String string = editText.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("mm");
        try{
            Date formatdate =sdf.parse(string);
            TextView textView;
            switch (idx){
                case 0:
                    firstsetDB.setDate(formatdate);
                    firstsetDB.setId(0);
                    firstsetDB.setTitle("朝食");
                    textView = (TextView)findViewById(R.id.textView9);
                    break;
                case 1:
                    firstsetDB.setDate(formatdate);
                    firstsetDB.setId(1);
                    firstsetDB.setTitle("昼食");
                    textView = (TextView)findViewById(textView11);
                    break;
                case 2:
                    firstsetDB.setDate(formatdate);
                    firstsetDB.setId(2);
                    firstsetDB.setTitle("夕食");
                    textView = (TextView)findViewById(R.id.textView13);
                    break;
                case 3:
                    firstsetDB.setDate(formatdate);
                    firstsetDB.setId(3);
                    firstsetDB.setTitle("風呂");
                    textView = (TextView)findViewById(R.id.textView15);
                    break;
                default:
                    textView = null;
                    break;
            }
            updateTestData(firstsetDB);
            textView.setText(string+"分");
        }catch (ParseException e){

        }
        editText.getEditableText().clear();

    }
    private RealmResults<FirstsetDB> getTestData(){

        Realm realm = Realm.getInstance(this);
        RealmQuery<FirstsetDB> query = realm.where(FirstsetDB.class);

        return query.findAll();

    }

    private void updateTestData(FirstsetDB db) {
        long idx = db.getId();
        Date date = db.getDate();
        String title = db.getTitle();

        Realm realm = Realm.getInstance(this);
        FirstsetDB tmp = results.get((int)idx);

        realm.beginTransaction();
        tmp.setDate(date);
        realm.commitTransaction();

    }
    private void addTestdataBestVer(long id, Date date, String title) {

        FirstsetDB model = new FirstsetDB();
        model.setId(id);
        model.setDate(date);
        model.setTitle(title);


        addTestDataBestVer(model);
    }
    private void addTestDataBestVer(FirstsetDB model) {

        Realm realm = Realm.getInstance(this);

        realm.beginTransaction();
        realm.copyToRealm(model);
        realm.commitTransaction();
    }
}
