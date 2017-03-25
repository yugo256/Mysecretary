package com.example.yugo.mysecretary;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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
        RealmResults<SecondTimepointsDB> secondTimepointsDBs = realm.where(SecondTimepointsDB.class).findAll();
        STPDB_Adapter adapter = new STPDB_Adapter(this, secondTimepointsDBs, true);
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
        RealmResults<SecondTimepointsDB> query = realm.where(SecondTimepointsDB.class)
                .equalTo("id" , id)
                .findAll();

        realm.beginTransaction();
        query.clear();
        realm.commitTransaction();

    }

}
