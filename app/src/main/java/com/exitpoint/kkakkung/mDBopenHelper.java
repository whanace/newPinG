package com.exitpoint.kkakkung;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.sql.SQLException;

/**
 * Created by k on 2015-08-15.
 */
public class mDBopenHelper {

    private static final String DATABASE_NAME = "sampleList.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        // 생성자
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(sampleData.CreateDB._CREATE);
        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + sampleData.CreateDB._TABLENAME);
            onCreate(db);

        }
    }

    public mDBopenHelper(Context context) {
        this.mCtx = context;
    }

    public mDBopenHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();

        return this;
    }

    public void close() {
        mDB.close();
    }

    // Insert DB
    public long insertColumn(String name, String contact, String email) {
        ContentValues values = new ContentValues();
        values.put(sampleData.CreateDB.NAME, name);
        values.put(sampleData.CreateDB.CONTACT, contact);
        values.put(sampleData.CreateDB.EMAIL, email);
        return mDB.insert(sampleData.CreateDB._TABLENAME, null, values);
    }

    // Update DB
    public boolean updateColumn(long id, String name, String contact, String email) {
        ContentValues values = new ContentValues();
        values.put(sampleData.CreateDB.NAME, name);
        values.put(sampleData.CreateDB.CONTACT, contact);
        values.put(sampleData.CreateDB.EMAIL, email);
        return mDB.update(sampleData.CreateDB._TABLENAME, values, "_id=" + id, null) > 0;
    }

    // Delete ID
    public boolean deleteColumn(long id) {
        return mDB.delete(sampleData.CreateDB._TABLENAME, "_id=" + id, null) > 0;
    }

    // Delete Contact
    public boolean deleteColumn(String number) {
        return mDB.delete(sampleData.CreateDB._TABLENAME, "contact=" + number, null) > 0;
    }

    // Select All
    public Cursor getAllColumns() {
        return mDB.query(sampleData.CreateDB._TABLENAME, null, null, null, null, null, null);
    }

    // ID 컬럼 얻어 오기
    public Cursor getColumn(long id) {
        Cursor c = mDB.query(sampleData.CreateDB._TABLENAME, null,
                "_id=" + id, null, null, null, null);
        if (c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }

    // 이름 검색 하기 (rawQuery)
    public Cursor getMatchName(String name) {
        Cursor c = mDB.rawQuery("select * from address where name=" + "'" + name + "'", null);
        return c;
    }
}

class CustomAdapter extends BaseAdapter{

    private LayoutInflater inflater;
    private ArrayList<InfoClass> infoList;
    private ViewHolder viewHolder;

    public CustomAdapter(Context c , ArrayList<InfoClass> array){
        inflater = LayoutInflater.from(c);
        infoList = array;
    }

    @Override
    public int getCount() {
        return infoList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {

        View v = convertview;

        if(v == null){
            viewHolder = new ViewHolder();
            v = inflater.inflate(R.layout.list_row, null);
            viewHolder.name = (TextView)v.findViewById(R.id.tv_name);
            viewHolder.contact = (TextView)v.findViewById(R.id.tv_contact);
            viewHolder.email = (TextView)v.findViewById(R.id.tv_email);
            v.setTag(viewHolder);

        }else {
            viewHolder = (ViewHolder)v.getTag();
        }

        viewHolder.name.setText(infoList.get(position).name);
        viewHolder.contact.setText(infoList.get(position).contact);
        viewHolder.email.setText(infoList.get(position).email);

        return v;
    }

    public void setArrayList(ArrayList<InfoClass> arrays){
        this.infoList = arrays;
    }

    public ArrayList<InfoClass> getArrayList(){
        return infoList;
    }
    /*
     * ViewHolder
     */
    class ViewHolder{
        TextView name;
        TextView contact;
        TextView email;
    }
}







