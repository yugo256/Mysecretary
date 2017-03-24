package com.example.yugo.mysecretary;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by yugo on 2017/03/24.
 */

public class SecondTimepointsDB extends RealmObject {
    @PrimaryKey
    private long id;
    private Date intervals;
    private String title;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getIntervals() {
        return intervals;
    }

    public void setIntervals(Date intervals) {
        this.intervals = intervals;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
