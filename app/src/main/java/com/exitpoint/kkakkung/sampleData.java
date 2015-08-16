package com.exitpoint.kkakkung;

import android.provider.BaseColumns;

/**
 * Created by k on 2015-08-15.
 */
public final class sampleData {
    // DataBase Table
    public static final class CreateDB implements BaseColumns {
        //사진 데이터 넣을 공간은 나중에 추가
        //이름,
        public static final String NAME = "name";
        public static final String CONTACT = "contact";
        public static final String EMAIL = "email";
        public static final String _TABLENAME = "samplelist";
        public static final String _CREATE =
                "create table " + _TABLENAME + "("
                        + _ID + " integer primary key autoincrement, "
                        + NAME + " text not null , "
                        + CONTACT + " text not null , "
                        + EMAIL + " text not null );";
    }
}

class InfoClass {
    public int _id;
    public String name;
    public String contact;
    public String email;

    public InfoClass() {
    }

    public InfoClass(int _id, String name, String contact, String email) {
        this._id = _id;
        this.name = name;
        this.contact = contact;
        this.email = email;
    }

}