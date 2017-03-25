package com.example.yugo.mysecretary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;

public class DeletePlan2 extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_plan2);

        listView = (ListView)findViewById(R.id.select);

        Realm realm = Realm.getInstance(this);
        RealmResults<SecondTimeintervalsDB> secondTimeintervalsDBs = realm.where(SecondTimeintervalsDB.class).findAll();
        STIDB_Adapter adapter =new STIDB_Adapter(this, secondTimeintervalsDBs, true);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView list = (ListView)parent;
                long idx = list.getItemIdAtPosition(position);
                deleteTestData(idx);

            }
        });
    }

    private void deleteTestData(long id) {
        Realm realm = Realm.getInstance(this);
        RealmResults<SecondTimeintervalsDB> query = realm.where(SecondTimeintervalsDB.class)
                .equalTo("id" , id)
                .findAll();

        realm.beginTransaction();
        query.clear();
        realm.commitTransaction();

    }

}
