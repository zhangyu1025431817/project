package com.fangzhipro.app.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.fangzhipro.app.bean.City;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * author zaaach on 2016/1/26.
 */
public class DBManager {
    private static final String DB_NAME = "fang_zhi.db";
    private static int DB_VERSION = 1;
    private SQLiteDatabase db;
    private MyDbHelper dbHelper;

    public DBManager(Context context) {
        dbHelper = new MyDbHelper(context, DB_NAME, null, DB_VERSION);
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        db.close();
        dbHelper.close();
    }

    /**
     * 读取所有城市
     *
     * @return
     */
    public List<City> getAllCities() {
        Cursor cursor = db.rawQuery("select * from " + MyDbHelper.TABLE_NAME, null);
        List<City> result = new ArrayList<>();
        City city;
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(City.ID));
            String level = cursor.getString(cursor.getColumnIndex(City.LEVEL));
            String name = cursor.getString(cursor.getColumnIndex(City.C_NAME));
            String pinyin = cursor.getString(cursor.getColumnIndex(City.E_NAME));
            city = new City(id, level, pinyin, name);
            result.add(city);
        }
        cursor.close();
        Collections.sort(result, new CityComparator());
        return result;
    }

    /**
     * 通过名字或者拼音搜索
     *
     * @param keyword
     * @return
     */
    public List<City> searchCity(final String keyword) {
        Cursor cursor = db.rawQuery("select * from " + MyDbHelper.TABLE_NAME + " where "+City.C_NAME+" like \"%" + keyword
                + "%\" or "+City.E_NAME+" like \"%" + keyword + "%\"", null);
        List<City> result = new ArrayList<>();
        City city;
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(City.ID));
            String level = cursor.getString(cursor.getColumnIndex(City.LEVEL));
            String name = cursor.getString(cursor.getColumnIndex(City.C_NAME));
            String pinyin = cursor.getString(cursor.getColumnIndex(City.E_NAME));
            city = new City(id, level, pinyin, name);
            result.add(city);
        }
        cursor.close();
        Collections.sort(result, new CityComparator());
        return result;
    }

    /**
     * 批量插入
     *
     * @param list
     * @return
     */
    public boolean insertBySql(List<City> list) {
        if (null == list || list.size() <= 0) {
            return false;
        }
        try {
            String sql = "insert into " + MyDbHelper.TABLE_NAME + "("
                    + City.ID + ","// 城市编码
                    + City.LEVEL + ","// 城市级别
                    + City.E_NAME + ","// 城市拼音
                    + City.C_NAME // 城市名称
                    + ") " + "values(?,?,?,?)";
            SQLiteStatement stat = db.compileStatement(sql);
            db.beginTransaction();
            for (City city : list) {
                stat.bindString(1, city.getId());
                stat.bindString(2, city.getLevel());
                stat.bindString(3, city.getArea_ename());
                stat.bindString(4, city.getArea_cname());
                long result = stat.executeInsert();
                if (result < 0) {
                    return false;
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (null != db) {
                    db.endTransaction();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void clearTable() {
        db.execSQL("DELETE FROM " + MyDbHelper.TABLE_NAME);
    }

    /**
     * a-z排序
     */
    private class CityComparator implements Comparator<City> {
        @Override
        public int compare(City lhs, City rhs) {
            String a = lhs.getArea_ename();
            String b = rhs.getArea_ename();
            return a.compareTo(b);
        }
    }
}
