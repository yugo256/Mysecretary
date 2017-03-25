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
//SecondTimeintervalsDBのAdapter
public class STIDB_Adapter extends RealmBaseAdapter<SecondTimeintervalsDB> {

    private static  class ViewHolder {
        TextView date;
        TextView title;
    }

    public STIDB_Adapter(Context context, RealmResults<SecondTimeintervalsDB> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){
            convertView = inflater.inflate(android.R.layout.simple_list_item_2, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.date = (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.title = (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        SecondTimeintervalsDB secondTimeintervalsDB = realmResults.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String formatDate = sdf.format(secondTimeintervalsDB.getIntervals());
        viewHolder.date.setText(formatDate);
        viewHolder.title.setText(secondTimeintervalsDB.getTitle());
        return convertView;
    }
}
