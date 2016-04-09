package ru.denisdyakin.ddgostcrypt.threads;

import android.os.Environment;

import java.io.File;

import ru.denisdyakin.ddgostcrypt.crypt.Gost2814789;
import ru.denisdyakin.ddgostcrypt.io.FileInput;
import ru.denisdyakin.ddgostcrypt.io.FileOutput;
import ru.denisdyakin.ddgostcrypt.resources.Res;
import ru.denisdyakin.ddgostcrypt.utils.Math;

/**
 *
 * @author Denis
 * @propetry shifrMode means 1- ecb mode, 2- cfb mode, 3- cbc mode, 4- mac mode, (cfb mode by default)
 * @property directory means folder for save encrypt/decrypt information
 */
public class ThreadObject implements Runnable{
    private int shifrMode = 1;
    private String directory = Environment.getExternalStorageDirectory() + Res.getDirectoryConst();
    private Gost2814789 gost;
    private File file;
    private Thread thread;
    private int mode;

    public ThreadObject(File file, Gost2814789 gost, int mode) {
        this.gost = gost;
        this.file = file;
        this.mode = mode;
        thread = new Thread(this, file.getName());
        thread.start();
    }

    public ThreadObject(File file, Gost2814789 gost, int mode, String directory){
        this.gost = gost;
        this.file = file;
        this.mode = mode;
        this.directory = directory;
        thread = new Thread(this, file.getName());
        thread.start();
    }

    @Override
    public void run() {
        switch(mode){
            case 1:{
                encrypt(directory);
                break;
            }
            case 2:{
                decrypt(directory);
                break;
            }
            case 3:{

                break;
            }
            case 4:{

                break;
            }
        }
    }

    private void encrypt(String directory){
        byte[] bytes = new FileInput(file).getBytes();

        switch(this.shifrMode){
            case 1:{
                int[] fileArray = Math.byteArrayToIntArray(bytes);

                fileArray = gost.ecbMode(fileArray, 0);

                bytes = null;
                bytes = Math.intArrayToByteArray(fileArray);

                break;
            }
            case 2:{
                int[] fileArray = Math.byteArrayToIntArray(bytes);

                fileArray = gost.cfbMode(fileArray, Res.getSync());

                bytes = null;
                bytes = Math.intArrayToByteArray(fileArray);

                break;
            }
            case 3:{

                break;
            }
            case 4:{

                break;
            }
        }

        new FileOutput(directory + file.getName() + "dd", bytes);

    }

    private void decrypt(String directory){
        byte[] bytes = new FileInput(file).getBytes();

        switch(this.shifrMode){
            case 1:{
                int[] fileArray = Math.byteArrayToIntArray(bytes);

                fileArray = gost.ecbMode(fileArray, 1);

                bytes = null;
                bytes = Math.intArrayToByteArray(fileArray);
                break;
            }
            case 2:{
                int[] fileArray = Math.byteArrayToIntArray(bytes);

                fileArray = gost.cfbMode(fileArray, Res.getSync());

                bytes = null;
                bytes = Math.intArrayToByteArray(fileArray);

                break;
            }
            case 3:{

                break;
            }
            case 4:{

                break;
            }
        }
        StringBuilder name = new StringBuilder(file.getName());
        name.delete(name.length()-3, name.length()-1);
        new FileOutput(directory + name.toString(), bytes);

    }

}
