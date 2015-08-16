package com.exitpoint.kkakkung;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;


public class SampleDBtest extends ActionBarActivity {
    private mDBopenHelper helper;
    private Cursor mCursor;
    private InfoClass mInfoClass;
    private ArrayList<InfoClass> mInfoArray;
    private CustomAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_dbtest);

        setLayout();

        helper = new mDBopenHelper(this);
        try {
            helper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        mInfoArray = new ArrayList<InfoClass>();

        doWhileCursorToArray();

        mAdapter = new CustomAdapter(this, mInfoArray);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemLongClickListener(longClickListener);

    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.btn_add:
                helper.insertColumn (
                                mEditTexts[0].getText().toString().trim(),//name
                                mEditTexts[1].getText().toString().trim(),//contact
                                mEditTexts[2].getText().toString().trim()//email
                        );

                mInfoArray.clear();

                doWhileCursorToArray();

                mAdapter.setArrayList(mInfoArray);
                mAdapter.notifyDataSetChanged();
                mCursor.close();

                break;

            default:
                break;
        }
    }

    /**
     * DB에서 받아온 값을 ArrayList에 Add
     */
    private void doWhileCursorToArray(){

        mCursor = null;
        mCursor = helper.getAllColumns();

        while (mCursor.moveToNext()) {

            mInfoClass = new InfoClass(
                    mCursor.getInt(mCursor.getColumnIndex("_id")),
                    mCursor.getString(mCursor.getColumnIndex("name")),
                    mCursor.getString(mCursor.getColumnIndex("contact")),
                    mCursor.getString(mCursor.getColumnIndex("email"))
            );

            mInfoArray.add(mInfoClass);
        }

        mCursor.close();
    }


    /**
     * OnClick Button
     */

    private EditText[] mEditTexts;
    private ListView mListView;

    private void setLayout(){
        mEditTexts = new EditText[]{
                (EditText)findViewById(R.id.et_name),
                (EditText)findViewById(R.id.et_contact),
                (EditText)findViewById(R.id.et_email)
        };

        mListView = (ListView) findViewById(R.id.lv_list);
    }
    private AdapterView.OnItemLongClickListener longClickListener = new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                       int position, long arg3) {

            boolean result = helper.deleteColumn(position);

            if(result){
                mInfoArray.remove(position);
                mAdapter.setArrayList(mInfoArray);
                mAdapter.notifyDataSetChanged();
            }else {
                Toast.makeText(getApplicationContext(), "INDEX를 확인해 주세요. - "+position,
                        Toast.LENGTH_LONG).show();
            }

            return false;
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sample_dbtest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
