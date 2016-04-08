package ru.denisdyakin.ddgostcrypt.activities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
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

/**
 * Created by Denis on 04.05.2015.
 */
public class FilesActivity extends ListActivity implements View.OnClickListener {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_files);

        // decrypt(); async task + threads

        this.myPath = (TextView) findViewById(R.id.path);
        this.root = Environment.getExternalStorageDirectory().getPath();

        File filesDirectory = new File(root + "/ddgostcrypt/files");
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
                                        // TODO Auto-generated method stub
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


}
