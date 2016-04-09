package ru.denisdyakin.ddgostcrypt.activities;

import android.app.TabActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import ru.denisdyakin.ddgostcrypt.R;
import ru.denisdyakin.ddgostcrypt.crypt.Gost2814789;
import ru.denisdyakin.ddgostcrypt.crypt.Gost3411;
import ru.denisdyakin.ddgostcrypt.io.FileController;
import ru.denisdyakin.ddgostcrypt.io.FileSafer;
import ru.denisdyakin.ddgostcrypt.resources.Res;
import ru.denisdyakin.ddgostcrypt.threads.ThreadController;
import ru.denisdyakin.ddgostcrypt.utils.Math;
import ru.denisdyakin.ddgostcrypt.utils.SQLHelperClass;

/**
 * Created by Denis on 04.05.2015.
 */
public class MainTabActivity extends TabActivity implements View.OnClickListener {
    private final int REQUEST_CODE_ADD_FILE = 2341;
    private final int REQUEST_CODE_ADD_PASS = 2342;
    private final int REQUEST_CODE_ADD_NOTE = 2388;
    private final String TAG1 = "TAG1";
    private final String TAG2 = "TAG2";
    private final String TAG3 = "TAG3";
    private final String TAG4 = "TAG4";

    private int[] key;

    private String currentTagId;

    private TextView tab_title;
    private Button add;
    private Button back;

    private View v;
    private ArrayList<File> fileList = new ArrayList<File>();
    private Gost3411 gost3411 = new Gost3411(Res.getSizeOfHash());
    private Gost2814789 gost2814789;
    private SQLHelperClass helperDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);

        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tab_title = (TextView) findViewById(R.id.tab_title);
        add = (Button) findViewById(R.id.addfile_button);
        back = (Button) findViewById(R.id.back_button);
        add.setOnClickListener(this);
        back.setOnClickListener(this);

        this.key = gost3411.getHash(Math.strToIntArray(this.getIntent().getStringExtra("password")));
        this.gost2814789 = new Gost2814789(this.key);

        final TabHost tabHost = getTabHost();

        TabHost.TabSpec tabSpec;

        tabSpec = tabHost.newTabSpec(TAG1);
        v = getLayoutInflater().inflate(R.layout.first_tab_header_p, null);
        tabSpec.setIndicator(v);
        tabSpec.setContent(new Intent(this, FilesActivity.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(TAG2);
        v = getLayoutInflater().inflate(R.layout.second_tab_header_p, null);
        tabSpec.setIndicator(v);
        Intent passIntent = new Intent(this, PasswordsActivity.class);
        passIntent.putExtra("password", this.getIntent().getStringExtra("password"));
        tabSpec.setContent(passIntent);
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(TAG3);
        v = getLayoutInflater().inflate(R.layout.third_tab_header_p, null);
        tabSpec.setIndicator(v);
        tabSpec.setContent(new Intent(this,NotesActivity.class));
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec(TAG4);
        v = getLayoutInflater().inflate(R.layout.fourth_tab_header_p, null);
        tabSpec.setIndicator(v);
        tabSpec.setContent(new Intent(this,SettingsActivity.class));
        tabHost.addTab(tabSpec);

        tabHost.setCurrentTabByTag(TAG1);
        currentTagId = TAG1;
        tab_title.setText(getResources().getString(R.string.files_tab));

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case TAG1: {
                        tab_title.setText(getString(R.string.files_tab));
                        updateList();
                        currentTagId = TAG1;
                        break;
                    }
                    case TAG2: {
                        tab_title.setText(getString(R.string.passwords_tab));

                        currentTagId = TAG2;
                        break;
                    }
                    case TAG3:{
                        tab_title.setText(getString(R.string.notes_tab));

                        currentTagId = TAG3;
                        break;
                    }
                    case TAG4:{
                        tab_title.setText(getString(R.string.settings_tab));

                        currentTagId = TAG4;
                        break;
                    }
                }
            }
        });

        CryptTask decryptTask = new CryptTask(2);
        decryptTask.execute();
        updateList();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addfile_button:{
                if(currentTagId == TAG2){
                    showaddpassactivity(v);
                }else {
                    showpopupmenu(v, currentTagId);
                }
                break;
            }
            case R.id.back_button:{
                onBackButtonPressed(v, currentTagId);
                break;
            }
        }
    }

    private void onBackButtonPressed(View v, String tag){
        DeleteTask deleteTask = new DeleteTask(Environment.getExternalStorageDirectory().getPath() + Res.getDirectoryTempConst());
        deleteTask.execute();
        finish();
    }

    private void showpopupmenu(View v, String tag){
        PopupMenu popupMenu = new PopupMenu(this, v);

        switch (tag) {
            case TAG1: {
                popupMenu.inflate(R.menu.popup_menu_files);

                popupMenu
                        .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {

                                    case R.id.add_file_menu:
                                        Intent intent = new Intent(getApplicationContext(), AddFile.class);
                                        startActivityForResult(intent, REQUEST_CODE_ADD_FILE);
                                        return true;
                                    case R.id.add_photo1_menu:

                                        return true;
                                    case R.id.add_photo2_menu:

                                        return true;
                                    case R.id.add_file_folder:

                                        return true;
                                    default:
                                        return false;
                                }

                            }
                        });
                break;
            }

            case TAG3: {
                popupMenu.inflate(R.menu.popup_menu_notes);

                popupMenu
                        .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()) {

                                    case R.id.add_note_menu:
                                        Intent intent = new Intent(getApplicationContext(), AddNote.class);
                                        startActivity(intent);
                                        return true;
                                    default:
                                        return false;
                                }

                            }
                        });
                break;
            }

            case TAG4:{
                v.setVisibility(View.INVISIBLE);
                break;
            }
        }
        popupMenu.show();
    }

    private void showaddpassactivity(View v){
            LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(LAYOUT_INFLATER_SERVICE);
            View popup_add_pass = layoutInflater.inflate(R.layout.activity_addpass, null);
            final PopupWindow popupWindow = new PopupWindow(popup_add_pass, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            final Button add_pass_button = (Button) popup_add_pass.findViewById(R.id.add_web);
            final TextView web_adress_txt = (TextView) popup_add_pass.findViewById(R.id.web_site_adress);
            final TextView web_login_txt = (TextView) popup_add_pass.findViewById(R.id.login_web);
            final TextView web_password_txt = (TextView) popup_add_pass.findViewById(R.id.password_web);
            add_pass_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkValues(web_adress_txt.getText().toString(), web_login_txt.getText().toString(), web_password_txt.getText().toString())){
                        add_pass(web_adress_txt.getText().toString(), web_login_txt.getText().toString(), web_password_txt.getText().toString());
                    }else{
                        Toast.makeText(getBaseContext(),getResources().getString(R.string.add_pass_err),Toast.LENGTH_SHORT).show();
                    }
                    popupWindow.dismiss();
                }
            });
            popupWindow.showAsDropDown(v, Gravity.CENTER_HORIZONTAL, 0);
            popupWindow.setFocusable(true);
            popupWindow.update();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case REQUEST_CODE_ADD_FILE:{
                    this.fileList = new ArrayList<File>();
                    ArrayList<String> stringFileList = data.getStringArrayListExtra(Res.getExtraConstAddFiles());
                    this.fileList = Math.arrayListStringToArrayListFile(stringFileList);
                    CryptTask newCryptTask = new CryptTask(1);
                    newCryptTask.execute();
                    CopyTask newCopyTask = new CopyTask(Environment.getExternalStorageDirectory().getPath() + Res.getDirectoryTempConst());
                    newCopyTask.execute();
                    WriteToDBTask newWriteToDBTask = new WriteToDBTask();
                    newWriteToDBTask.execute();
                    updateList();
                    break;
                }
            }
        }
    }

    private void updateList(){
        Intent message_to_update = new Intent(Res.getBroadcastActionUpdateList());
        sendBroadcast(message_to_update);
    }

    private boolean checkValues(String web_adress, String web_login, String web_password){
        return web_adress != null && web_login != null && web_password != null && !web_adress.equals("") && !web_login.equals("") && !web_password.equals("");
    }

    private void add_pass(String web_adress, String web_login, String web_password){
        this.helperDB = new SQLHelperClass(this);
        SQLiteDatabase db = this.helperDB.getWritableDatabase();
        ContentValues cv = new ContentValues();
        //web_adress =  Math.intArrayToStr(this.gost2814789.ecbMode(Math.strToIntArray(web_adress), 0));
        //web_login = Math.intArrayToStr(this.gost2814789.ecbMode(Math.strToIntArray(web_login), 0));
        //web_password = Math.intArrayToStr(this.gost2814789.ecbMode(Math.strToIntArray(web_password), 0));
        cv.put(SQLHelperClass.LOGIN, web_login);
        cv.put(SQLHelperClass.WEB_SITE, web_adress);
        cv.put(SQLHelperClass.PASSWORD, web_password);
        db.insert(SQLHelperClass. TABLE_THIRD, null, cv);
        db.close();
        this.helperDB.close();
        sendBroadcast(new Intent(Res.getBroadcastActionUpdatePasslist()));
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DeleteTask deleteTask = new DeleteTask(Environment.getExternalStorageDirectory().getPath() + Res.getDirectoryTempConst());
        deleteTask.execute();
        finish();
    }


    public class CryptTask extends AsyncTask <Void, Void, Void> {
        private int modeTask;

        public CryptTask(int _mode){
             modeTask = _mode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            switch(modeTask){
                case 1:{
                    encrypt();
                    new FileSafer(new SQLHelperClass(getApplicationContext()).getWritableDatabase()).writeTrueFileList();
                    updateList();
                    break;
                }
                case 2:{
                    fileList = new FileSafer(new SQLHelperClass(getApplicationContext()).getReadableDatabase()).getTrueFileList();
                    decrypt();
                    updateList();
                    break;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            updateList();
        }

        private void encrypt(){
            ThreadController threadController = new ThreadController(fileList, key, 1);
            threadController.startTaskOfEncrypt();
        }

        private void decrypt(){
            ThreadController threadController = new ThreadController(fileList, key, 2);
            threadController.startTaskOfDecrypt();
        }
    }

    public class CopyTask extends AsyncTask <Void, Void, Void> {
        private String directory;

        public CopyTask(String _directory){
            directory = _directory;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            copyFiles();
            updateList();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        private void copyFiles(){
            FileController fileController = new FileController(directory, fileList);
            fileController.run();
        }

    }

    public class WriteToDBTask extends AsyncTask <Void, Void, Void> {

        public WriteToDBTask(){
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            write();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        private void write(){
            //!! в БД классе поковыряться и здесь через threadcontroller получить mac файла и записывать в БД
            // вместе с именем
        }

    }

    public class DeleteTask extends AsyncTask <Void, Void, Void> {
        private String directory = "";

        public DeleteTask(String _directory){
            directory = _directory;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            deleteFiles();
            updateList();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        private void deleteFiles(){
            FileController fileController = new FileController(directory);
            fileController.run();
        }

    }
}
