package ru.denisdyakin.ddgostcrypt.activities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.denisdyakin.ddgostcrypt.R;
import ru.denisdyakin.ddgostcrypt.adapters.File_tab_adapter;
import ru.denisdyakin.ddgostcrypt.intent.ParcelableObject;
import ru.denisdyakin.ddgostcrypt.resources.Res;

/**
 * Created by Denis on 04.05.2015.
 */
public class FilesActivity extends ListActivity implements View.OnClickListener, AdapterView.OnItemLongClickListener {
    private final String EXTRA_CONST_ADD_FILES = Res.getExtraConstAddFiles();
    private final String BROADCAST_ACTION_ADD_FILES = Res.getBroadcastActionAddFiles();
    private final String BROADCAST_ACTION_UPDATE_LIST = Res.getBroadcastActionUpdateList();

    private List<String> item = null;
    private List<String> path = null;

    private List<String> itemDir = null;
    private List<String> itemFile = null;
    private List<String> pathDir = null;
    private List<String> pathFile = null;

    private String root;
    private TextView myPath;

    private File[] files;
    private File[] dirs;

    private File f;

    private ArrayList<String> fileList;
    private BroadcastReceiver broadcastReciever;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_files);

        this.broadcastReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                update();
            }
        };
        // создаем фильтр для BroadcastReceiver
        IntentFilter intFilter = new IntentFilter(BROADCAST_ACTION_UPDATE_LIST);
        // регистрируем (включаем) BroadcastReceiver
        registerReceiver(broadcastReciever, intFilter);

        this.myPath = (TextView) findViewById(R.id.path);
        this.root = Environment.getExternalStorageDirectory().getPath();

        File filesDirectory = new File(root + Res.getDirectoryTempConst());
        if(! filesDirectory.exists()){
            if(filesDirectory.mkdirs()){
                this.root = filesDirectory.getPath();
            }
        }else{
            this.root = filesDirectory.getPath();
        }

        getDir(this.root);

    }

    private void getDir(String dirPath){
        myPath.setText(getString(R.string.location) + dirPath);

        item = new ArrayList<String>();
        path = new ArrayList<String>();

        itemDir = new ArrayList<String>();
        itemFile = new ArrayList<String>();
        pathDir = new ArrayList<String>();
        pathFile = new ArrayList<String>();

        f = new File(dirPath);
        File[] files = f.listFiles();


        if(!dirPath.equals(root)){
            item.add(root);
            path.add(root);
            item.add("../");
            path.add(f.getParent());
        }

        if(files != null){
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (!file.isHidden() && file.canRead()) {
                    if (file.isDirectory()) {
                        itemDir.add(file.getName() + "/");
                        pathDir.add(file.getPath());
                    } else {
                        itemFile.add(file.getName());
                        pathFile.add(file.getPath());
                    }
                }
            }
            for(int i=0; i<itemDir.size(); i++){
                item.add(itemDir.get(i));
                path.add(pathDir.get(i));
            }
            for(int i=0; i<itemFile.size(); i++){
                item.add(itemFile.get(i));
                path.add(pathFile.get(i));
            }
        }else{
            path.add("/");
            item.add(getString(R.string.no_data));
        }

        File_tab_adapter fileList = new File_tab_adapter(this, item);

        setListAdapter(fileList);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        File file = new File(path.get(position));
        if(file.isDirectory()){
            if(file.canRead()){
                getDir(path.get(position));
            }else{
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_launcher)
                        .setTitle(
                                "[" + file.getName()
                                        + "] " + getResources().getString(R.string.folder_is_closed))
                        .setPositiveButton(getResources().getString(R.string.ok),
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        // TODO Auto-generated method stu   b
                                    }
                                }).show();
            }
        }else{
            Intent intent = new Intent("android.intent.action.View", Uri.fromFile(file));
        }
    }

        public void update(){
        getDir(f.getPath());
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(path.size() >= 2){
            getDir(path.get(1));
        }else{
            onDestroy();
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        showpopupmenu(view);
        return true;
    }

    private void showpopupmenu(View view){
        PopupMenu menu = new PopupMenu(this, view);
        menu.inflate(R.menu.popum_menu_file);
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
    }
}
