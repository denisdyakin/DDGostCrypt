package ru.denisdyakin.ddgostcrypt;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import ru.denisdyakin.ddgostcrypt.activities.MainTabActivity;
import ru.denisdyakin.ddgostcrypt.crypt.Gost3411;
import ru.denisdyakin.ddgostcrypt.utils.*;
import ru.denisdyakin.ddgostcrypt.utils.Math;


public class MainActivity extends Activity{

    private final String STRING_CREATING = "Cr";
    private boolean cr;
    private String password = null;
    private Gost3411 gostHash;

    SQLHelperClass sqlhelper;

    private ImageButton login_button;
    private ImageButton erase_button;
    private ImageButton one_but;
    private ImageButton two_but;
    private ImageButton three_but;
    private ImageButton four_but;
    private ImageButton five_but;
    private ImageButton six_but;
    private ImageButton seven_but;
    private ImageButton eigth_but;
    private ImageButton nine_but;
    private ImageButton zero_but;

    private TextView status_message;
    private TextView pass_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        status_message = (TextView) findViewById(R.id.status_message);
        pass_text_view = (TextView) findViewById(R.id.pass_text_view);
        pass_text_view.setText("");

        sqlhelper = new SQLHelperClass(this);

        cr = isFirstCreating();
        if(cr){
            setStatus(getString(R.string.your_password));
        }else{
            setStatus(getString(R.string.input_password));
        }

        login_button = (ImageButton) findViewById(R.id.login_button);
        erase_button = (ImageButton) findViewById(R.id.erase_button);
        one_but = (ImageButton) findViewById(R.id.one_but);
        two_but = (ImageButton) findViewById(R.id.two_but);
        three_but = (ImageButton) findViewById(R.id.three_but);
        four_but = (ImageButton) findViewById(R.id.four_but);
        five_but = (ImageButton) findViewById(R.id.five_but);
        six_but = (ImageButton) findViewById(R.id.six_but);
        seven_but = (ImageButton) findViewById(R.id.seven_but);
        eigth_but = (ImageButton) findViewById(R.id.eigth_but);
        nine_but = (ImageButton) findViewById(R.id.nine_but);
        zero_but = (ImageButton) findViewById(R.id.zero_but);

        login_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                        {
                            login_button.setImageResource(R.drawable.loginp);
                            break;
                        }
                    case MotionEvent.ACTION_MOVE: // движение

                             break;
                    case MotionEvent.ACTION_UP: // отпускание
                        {
                            login_button.setImageResource(R.drawable.login);
                            pass_text_view.setText("");

                            if(password == null){
                                setStatus(getString(R.string.pass_is_empty));
                            }else{
                                if(cr){
                                    putPassInDB(password);
                                    pushCountOfCreating();
                                    Intent intent = new Intent(getApplicationContext(), MainTabActivity.class);
                                    startActivity(intent);
                                    password = null;
                                    cr = false;
                                }else{
                                    if(checkPass(password)){
                                        Intent intent = new Intent(getApplicationContext(), MainTabActivity.class);
                                        intent.putExtra("password", password);
                                        startActivity(intent);
                                        password = null;
                                    }else{
                                        setStatus(getString(R.string.pass_error));
                                        password = null;
                                    }
                                }
                            }
                            break;
                        }
                    case MotionEvent.ACTION_CANCEL:

                            break;
                }
                return true;
            }
        });

        erase_button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearStatus();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                    {
                        erase_button.setImageResource(R.drawable.erasep);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: // движение

                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    {
                        erase_button.setImageResource(R.drawable.erase);
                        pass_text_view.setText("");
                        password = null;
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                return true;
            }
        });

        one_but.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearStatus();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                    {
                        one_but.setImageResource(R.drawable.onep);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: // движение

                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    {
                        one_but.setImageResource(R.drawable.one);
                        pass_text_view.setText(pass_text_view.getText() + " *");
                        password = password + "1";
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                return true;
            }
        });

        two_but.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearStatus();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                    {
                        two_but.setImageResource(R.drawable.twop);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: // движение

                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    {
                        two_but.setImageResource(R.drawable.two);
                        pass_text_view.setText(pass_text_view.getText() + " *");
                        password = password + "2";
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                return true;
            }
        });

        three_but.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearStatus();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                    {
                        three_but.setImageResource(R.drawable.threep);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: // движение

                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    {
                        three_but.setImageResource(R.drawable.three);
                        pass_text_view.setText(pass_text_view.getText() + " *");
                        password = password + "3";
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                return true;
            }
        });

        four_but.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearStatus();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                    {
                        four_but.setImageResource(R.drawable.fourp);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: // движение

                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    {
                        four_but.setImageResource(R.drawable.four);
                        pass_text_view.setText(pass_text_view.getText() + " *");
                        password = password + "4";
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                return true;
            }
        });

        five_but.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearStatus();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                    {
                        five_but.setImageResource(R.drawable.fivep);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: // движение

                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    {
                        five_but.setImageResource(R.drawable.five);
                        pass_text_view.setText(pass_text_view.getText() + " *");
                        password = password + "5";
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                return true;
            }
        });

        six_but.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearStatus();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                    {
                        six_but.setImageResource(R.drawable.sixp);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: // движение

                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    {
                        six_but.setImageResource(R.drawable.six);
                        pass_text_view.setText(pass_text_view.getText() + " *");
                        password = password + "6";
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                return true;
            }
        });

        seven_but.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearStatus();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                    {
                        seven_but.setImageResource(R.drawable.sevenp);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: // движение

                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    {
                        seven_but.setImageResource(R.drawable.seven);
                        pass_text_view.setText(pass_text_view.getText() + " *");
                        password = password + "7";
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                return true;
            }
        });

        eigth_but.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearStatus();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                    {
                        eigth_but.setImageResource(R.drawable.eigthp);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: // движение

                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    {
                        eigth_but.setImageResource(R.drawable.eigth);
                        pass_text_view.setText(pass_text_view.getText() + " *");
                        password = password + "8";
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                return true;
            }
        });

        nine_but.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearStatus();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                    {
                        nine_but.setImageResource(R.drawable.ninep);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: // движение

                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    {
                        nine_but.setImageResource(R.drawable.nine);
                        pass_text_view.setText(pass_text_view.getText() + " *");
                        password = password + "9";
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                return true;
            }
        });

        zero_but.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                clearStatus();
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // нажатие
                    {
                        zero_but.setImageResource(R.drawable.zerop);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: // движение

                        break;
                    case MotionEvent.ACTION_UP: // отпускание
                    {
                        zero_but.setImageResource(R.drawable.zero);
                        pass_text_view.setText(pass_text_view.getText() + " *");
                        password = password + "0";
                        break;
                    }
                    case MotionEvent.ACTION_CANCEL:

                        break;
                }
                return true;
            }
        });

    }

    private boolean isFirstCreating(){
        return getPreferences(MODE_PRIVATE).getString(STRING_CREATING, "").equals("");
    }

    private void pushCountOfCreating(){
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString(STRING_CREATING, "1");
        editor.commit();
    }

    private void clearStatus(){
        status_message.setText("");
    }

    private void setStatus(String str){
        status_message.setText(str);
    }

    private boolean checkPass(String pass){
        SQLiteDatabase db = sqlhelper.getReadableDatabase();
        Cursor c = db.query(SQLHelperClass.TABLE_FIRST,null,"id = ?", new String[]{"21"}, null,null,null);
        if(c!=null){
            boolean tag = false;
           if(c.moveToFirst()){
               tag = hashOfPass(pass).equals(c.getString(c.getColumnIndex(SQLHelperClass.PASSHASH)));
           }
            c.close();
            sqlhelper.close();
            return tag;
        }else
             return false;
    }

    private String hashOfPass(String pass){
        gostHash = new Gost3411(256);
        pass = pass + "s";
        int[] hashR = gostHash.getHash(ru.denisdyakin.ddgostcrypt.utils.Math.strToIntArray(pass));
        return Math.intArrayToStr(gostHash.getHash(hashR));
    }

    private void putPassInDB(String pass){
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = sqlhelper.getWritableDatabase();
        cv.put(SQLHelperClass.ID, "21");
        cv.put(SQLHelperClass.PASSHASH, hashOfPass(pass));
        db.insert(SQLHelperClass.TABLE_FIRST, null, cv);
        sqlhelper.close();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
