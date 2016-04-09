package ru.denisdyakin.ddgostcrypt.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Denis on 19.05.2015.
 */
public class FileController implements Runnable {
    private String directory;
    private ArrayList<File> files = new ArrayList<File>();
    private int mode;

    public FileController(String _directory, ArrayList<File> _files){
        this.directory = _directory;
        this.files = _files;
        this.mode = 1;
    }

    public FileController(String _directory){
        this.directory = _directory;
        this.mode = 2;
    }

    @Override
    public void run() {
        switch (this.mode){
            case 1: {
                for(File file : this.files){
                    try {
                        FileCopy.copy(file, new File(directory + file.getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
            }
            case 2: {
                File[] fileArray = new File(directory).listFiles();
                if(fileArray != null){
                    for(File f : fileArray){
                        this.files.add(f);
                    }
                    for(File file : this.files){
                        FileDelete.delete(file);
                    }
                }

                break;
            }
        }



    }
}
