package ru.denisdyakin.ddgostcrypt.io;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Denis on 11.05.2015.
 */
public class FileInput {
    private File file;

    public FileInput(File file){
        this.file = file;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public byte[] getBytes(){
        byte[] buffer = null;
        try(FileInputStream fin=new FileInputStream(this.file.getAbsolutePath()))
        {
            buffer = new byte[fin.available()];
            fin.read(buffer, 0, fin.available());
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
        return buffer;
    }
}
