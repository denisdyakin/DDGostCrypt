package ru.denisdyakin.ddgostcrypt.activities;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import ru.denisdyakin.ddgostcrypt.R;
import ru.denisdyakin.ddgostcrypt.adapters.Add_file_adapter;
import ru.denisdyakin.ddgostcrypt.intent.ParcelableObject;
import ru.denisdyakin.ddgostcrypt.resources.Res;
import ru.denisdyakin.ddgostcrypt.utils.Math;


/**
 * Created by Denis on 05.05.2015.
 */
public class AddFile extends ListActivity{
    private final String EXTRA_CONST = Res.getExtraConstAddFiles();
    private final String BACK_CONST = "../";
    private final String DIR_INDICATOR = "/";

    private List<String> item = null;
    private List<String> path = null;

    private List<String> itemDir = null;
    private List<String> itemFile = null;
    private List<String> pathDir = null;
    private List<String> pathFile = null;

    private String root = "/storage";
    private String currentPath = root;
    private TextView myPath;
    private Button add_end;
    private Button back_up;

    private ArrayList<File> fileIntentList;

    private StringBuilder toast_status;

    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfiles);

        fileIntentList = new ArrayList<File>();
        toast_status = new StringBuilder(getResources().getString(R.string.your_chooice));

        this.myPath = (TextView) findViewById(R.id.path2);
        this.back_up = (Button) findViewById(R.id.back_up);
        this.add_end = (Button) findViewById(R.id.add_end);

        File filesDirectory = new File(root);
        if(! filesDirectory.exists()){
            if(filesDirectory.mkdirs()){
                this.root = filesDirectory.getPath();
            }
        }else{
            this.root = filesDirectory.getPath();
        }

        getDir(this.root);

        this.back_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(path.size() >= 2){
                        getDir(path.get(1));
                }else{
                    onDestroy();
                }
            }
        });

        this.add_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDestroy();
            }
        });
    }


    private void getDir(String dirPath){
        myPath.setText(getString(R.string.location) + dirPath);

        item = new ArrayList<String>();
        path = new ArrayList<String>();

        itemDir = new ArrayList<String>();
        itemFile = new ArrayList<String>();
        pathDir = new ArrayList<String>();
        pathFile = new ArrayList<String>();

        File f = new File(dirPath);
        File[] files = f.listFiles();


        if(!dirPath.equals(root)){
            item.add(root);
            path.add(root);
            item.add(BACK_CONST);
            path.add(f.getParent());
        }

        if(files != null){
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                if (!file.isHidden() && file.canRead()) {
                    if (file.isDirectory()) {
                        itemDir.add(file.getName() + DIR_INDICATOR);
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
        }

        Add_file_adapter adapter = new Add_file_adapter(this, item, fileIntentList);
        setListAdapter(adapter);
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
                        .setIcon(R.drawable.locked_icon)
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
            if(!fileIntentList.contains(file)){
                fileIntentList.add(file);
                getDir(file.getParent());
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.file_is_choosen) + " " + file.getName(), Toast.LENGTH_SHORT).show();
            }else{
                fileIntentList.remove(file);
                getDir(file.getParent());
                Toast.makeText(getApplicationContext(),getResources().getString(R.string.file_is_unchoosen), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(!fileIntentList.isEmpty()){
            Intent _intent = new Intent();
            _intent.putStringArrayListExtra(EXTRA_CONST, Math.arrayListFileToArrayListString(fileIntentList));
            setResult(RESULT_OK, _intent);
            Toast.makeText(this,getResources().getString(R.string.file_choosen), Toast.LENGTH_SHORT).show();
            finish();
        }else{
            setResult(RESULT_CANCELED);
            Toast.makeText(this,getResources().getString(R.string.file_no_choosen), Toast.LENGTH_SHORT).show();
            finish();
        }

    }



}
