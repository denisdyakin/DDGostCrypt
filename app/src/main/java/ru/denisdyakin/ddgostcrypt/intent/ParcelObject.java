package ru.denisdyakin.ddgostcrypt.intent;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Denis on 18.05.2015.
 */
public class ParcelObject {
    private ArrayList<File> _fileList;

    public ParcelObject(ArrayList<File> fileList){
        this._fileList = fileList;
    }
}
