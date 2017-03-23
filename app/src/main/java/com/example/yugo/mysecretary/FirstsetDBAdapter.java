package com.example.yugo.mysecretary;

import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import io.realm.RealmBaseAdapter;
import io.realm.RealmResults;

/**
 * Created by yugo on 2017/03/21.
 */

public class FirstsetDBAdapter extends RealmBaseAdapter<FirstsetDB> {

    private static class ViewHolder {
        TextView date;
        TextView title;
    }

    public FirstsetDBAdapter(Context context, RealmResults<FirstsetDB> realmResults, boolean automaticUpdate) {
        super(context, realmResults, automaticUpdate);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            convertView = inflater.inflate(
                    android.R.layout.simple_list_item_2, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.date =
                    (TextView) convertView.findViewById(android.R.id.text1);
            viewHolder.title =
                    (TextView) convertView.findViewById(android.R.id.text2);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        FirstsetDB firstsetDB = realmResults.get(position);
        return null;

    }
}
