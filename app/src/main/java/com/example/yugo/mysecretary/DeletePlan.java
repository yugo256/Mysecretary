package com.example.yugo.mysecretary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by kousei on 2017/03/24.
 */

public class DeletePlan extends AppCompatActivity {
    ListView listView;

    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dereteplan);

        listView = (ListView)findViewById(R.id.select);

        Realm realm = Realm.getInstance(this);
        RealmResults<SecondTimeintervalsDB> secondTimeintervalsDBs = realm.where(SecondTimeintervalsDB.class).findAll();
        RealmResults<SecondTimepointsDB> secondTimepointsDBs = realm.where(SecondTimepointsDB.class).findAll();

    }
}
