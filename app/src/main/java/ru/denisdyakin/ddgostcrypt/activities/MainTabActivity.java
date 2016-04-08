package ru.denisdyakin.ddgostcrypt.activities;

import android.app.ActionBar;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TabHost;
import android.widget.TextView;

import ru.denisdyakin.ddgostcrypt.R;

/**
 * Created by Denis on 04.05.2015.
 */
public class MainTabActivity extends TabActivity implements View.OnClickListener {
    private final int REQUEST_CODE_ADD_FILE = 2341;
    private final String TAG1 = "TAG1";
    private final String TAG2 = "TAG2";
    private final String TAG3 = "TAG3";
    private final String TAG4 = "TAG4";

    private String currentTagId;

    private TextView tab_title;
    private Button add;

    private View v;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);
        tab_title = (TextView) findViewById(R.id.tab_title);

        add = (Button) findViewById(R.id.addfile_button);
        add.setOnClickListener(this);

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
        tabSpec.setContent(new Intent(this,PasswordsActivity.class));
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

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId){
                    case TAG1: {
                        tab_title.setText(getString(R.string.files_tab));

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
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.addfile_button:{
                showpopupmenu(v, currentTagId);
            }
        }
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

                                        return true;
                                    case R.id.add_note_folder_menu:

                                        return true;
                                    default:
                                        return false;
                                }

                            }
                        });
                break;
            }
        }
        popupMenu.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {return;}
        if(resultCode == RESULT_OK){
            switch(requestCode){
                case REQUEST_CODE_ADD_FILE:{
                   // encrypt();
                    //putInTemp();
                    //writeToDB();
                    updateList();
                    //обновить listView через broadcastreciever
                    break;
                }
            }
        }
        // вытаскиваем файлы из intent data, добавляем в базу данных и шифруем в папку files, и оставляем до destroy() в папке temp_files
    }

    private void updateList(){
        //broadcast message to FilesActivity for update listActivity
    }
}
