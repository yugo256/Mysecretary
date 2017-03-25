package com.example.yugo.mysecretary;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by yugo on 2017/03/25.
 */
//SecondTimepointsDBのAdapter
public class STPDB_Adapter extends RealmBaseAdapter<SecondTimepointsDB> {

    private static  class ViewHolder {
        TextView date;
        TextView title;
    }

    public STPDB_Adapter(Context context, RealmResults<SecondTimepointsDB> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        STPDB_Adapter.ViewHolder viewHolder;

        if(convertView == null){
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            viewHolder = new STPDB_Adapter.ViewHolder();
            viewHolder.date = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.title = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (STPDB_Adapter.ViewHolder)convertView.getTag();
        }

        SecondTimepointsDB secondTimepointsDB = realmResults.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String start = sdf.format(secondTimepointsDB.getStart());
        String finish = sdf.format(secondTimepointsDB.getFinish());
        viewHolder.date.setText(start+"～"+finish);
        viewHolder.title.setText(secondTimepointsDB.getTitle());
        return convertView;
    }
}
