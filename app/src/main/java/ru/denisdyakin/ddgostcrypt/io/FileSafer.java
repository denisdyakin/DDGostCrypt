package ru.denisdyakin.ddgostcrypt.io;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.File;
import java.util.ArrayList;

import ru.denisdyakin.ddgostcrypt.resources.Res;
import ru.denisdyakin.ddgostcrypt.utils.SQLHelperClass;

/**
 * Created by Denis on 19.05.2015.
 */
public class FileSafer {

    private ArrayList<File> fileList1 = new ArrayList<File>();
    private SQLiteDatabase dataBase;

    public FileSafer(SQLiteDatabase _dataBase){
        this.dataBase = _dataBase;
    }


    public boolean writeTrueFileList(){
        boolean tag = true;
        File fileExtDir = new File(Environment.getExternalStorageDirectory().getPath() + Res.getDirectoryConst());
        File[] filesExtDir = fileExtDir.listFiles();
        if(fileExtDir.length() > 0){
            for(File file : filesExtDir){
                fileList1.add(file);
            }
        }
        if(this.fileList1.size() >0){
            ContentValues cv = new ContentValues();
            for(File file : this.fileList1){
                cv.put(SQLHelperClass.FILE_NAME, file.getPath());
            }
            this.dataBase.insert(SQLHelperClass.TABLE_SECOND, null, cv);
        }

        this.dataBase.close();
        return tag;
    }

    public ArrayList<File> getTrueFileList(){
        Cursor c = this.dataBase.query(SQLHelperClass.TABLE_SECOND,null,null,null,null,null,null);
        ArrayList<String> filesPaths = new ArrayList<String>();
        //улучшить безопасность путем добавления и считывания хешей файлов
        if(c!=null){
            if(c.moveToFirst()){
                do{
                    filesPaths.add(c.getString(c.getColumnIndex(SQLHelperClass.FILE_NAME)));
                }while(c.moveToNext());
            }
        }
        for(String str : filesPaths){
            this.fileList1.add(new File(str));
        }
        this.dataBase.close();
        return this.fileList1;
    }
}
