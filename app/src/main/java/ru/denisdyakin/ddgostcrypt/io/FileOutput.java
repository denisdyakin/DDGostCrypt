package ru.denisdyakin.ddgostcrypt.io;

import android.annotation.TargetApi;
import android.os.Build;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Denis on 11.05.2015.
 */
public class FileOutput {
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public FileOutput(String way, byte[] buffer){

        try(FileOutputStream out=new FileOutputStream(way);
            BufferedOutputStream bos = new BufferedOutputStream(out))
        {
            bos.write(buffer, 0, buffer.length);
        }
        catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
