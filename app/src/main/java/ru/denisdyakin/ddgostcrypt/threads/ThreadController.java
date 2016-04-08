package ru.denisdyakin.ddgostcrypt.threads;

/**
 * Created by Denis on 11.05.2015.
 */

import java.io.File;
import java.util.ArrayList;

import ru.denisdyakin.ddgostcrypt.crypt.Gost2814789;

/**
 *
 * @author Denis
 * @value mode is int value of: 1 - encrypt files , 2 - decrypt files, 3 - copies files, 4 - delete files
 */
public class ThreadController{
    private int[] key;
    private int countOfThreads;
    private ArrayList<File> files = null;
    private int mode;

    public ThreadController(ArrayList<File> files, int[] key, int moded){
        this.key = key;
        this.countOfThreads = files.size();
        this.files = files;
        this.mode = moded;
    }

    public void startTask(){
        for(int i = 0; i<countOfThreads; i++){
            Gost2814789 newGostObject = new Gost2814789(key);
            ThreadObject newObject = new ThreadObject(files.get(i), newGostObject, mode);
        }
    }




}
