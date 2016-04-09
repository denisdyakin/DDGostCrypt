package ru.denisdyakin.ddgostcrypt.activities;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import java.util.ArrayList;

import ru.denisdyakin.ddgostcrypt.R;
import ru.denisdyakin.ddgostcrypt.adapters.Pass_tab_adapter;
import ru.denisdyakin.ddgostcrypt.crypt.Gost2814789;
import ru.denisdyakin.ddgostcrypt.crypt.Gost3411;
import ru.denisdyakin.ddgostcrypt.intent.Pass_tab_object;
import ru.denisdyakin.ddgostcrypt.resources.Res;
import ru.denisdyakin.ddgostcrypt.utils.*;

/**
 * Created by Denis on 04.05.2015.
 */
public class PasswordsActivity extends ListActivity {
    private final String BROADCAST_ACTION_UPDATE_PASSLIST = Res.getBroadcastActionUpdatePasslist();
    private BroadcastReceiver broadcastReceiver;
    private SQLiteDatabase db;
    private SQLHelperClass dbHelper;
    private ArrayList<Pass_tab_object> list;
    private Gost2814789 gost2814789;
    private Gost3411 gost3411;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_passwords);
        this.gost3411 = new Gost3411(256);
        this.gost2814789 = new Gost2814789(gost3411.getHash(ru.denisdyakin.ddgostcrypt.utils.Math.strToIntArray(this.getIntent().getStringExtra("password"))));

        this.broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                update();
            }
        };

        IntentFilter intentFilter = new IntentFilter(BROADCAST_ACTION_UPDATE_PASSLIST);
        registerReceiver(broadcastReceiver, intentFilter);

        this.dbHelper = new SQLHelperClass(this);
        this.db = this.dbHelper.getReadableDatabase();

        getPasswords();

    }

    private void getPasswords(){
        this.list = new ArrayList<Pass_tab_object>();
        Cursor c = this.db.query(SQLHelperClass.TABLE_THIRD, null, null, null, null, null, null);
        if(c.moveToFirst()){
            do{
                //String web_adress = Math.intArrayToStr(this.gost2814789.ecbMode(Math.strToIntArray(c.getString(c.getColumnIndex(dbHelper.WEB_SITE))), 1));
                //String web_login = Math.intArrayToStr(this.gost2814789.ecbMode(Math.strToIntArray(c.getString(c.getColumnIndex(dbHelper.LOGIN))), 1));
                //String web_password = Math.intArrayToStr(this.gost2814789.ecbMode(Math.strToIntArray(c.getString(c.getColumnIndex(dbHelper.PASSWORD))), 1));
                //object.setWeb_adress(web_adress);
                //object.setLogin(web_login);
                //object.setPassword(web_password);

                Pass_tab_object object = new Pass_tab_object();
                String web_adress = c.getString(c.getColumnIndex(SQLHelperClass.WEB_SITE));
                object.setWeb_adress(web_adress);
                object.setLogin(c.getString(c.getColumnIndex(SQLHelperClass.LOGIN)));
                object.setPassword(c.getString(c.getColumnIndex(SQLHelperClass.PASSWORD)));

                this.list.add(object);

            }while(c.moveToNext());
        }
        Pass_tab_adapter passtab_adapter = new Pass_tab_adapter(this, this.list);

        setListAdapter(passtab_adapter);
    }

    private void update(){
        getPasswords();
    }
}
