package ru.denisdyakin.ddgostcrypt.utils;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Denis on 05.05.2015.
 */
public class Math {

    public static ArrayList<String> arrayListFileToArrayListString(ArrayList<File> arrayListFile){
        ArrayList<String> result = new ArrayList<String>();
        for(File file : arrayListFile){
            result.add(file.getPath());
        }
        return result;
    }

    public static ArrayList<File> arrayListStringToArrayListFile(ArrayList<String> arrayListString){
        ArrayList<File> result = new ArrayList<File>();
        if(arrayListString.size()>0){
            for(String strFile : arrayListString){
                result.add(new File(strFile));
            }
        }
        return result;
    }

    public static ArrayList<String> arrayListFileToArrayListString2(ArrayList<File> arrayListFile){
        ArrayList<String> result = new ArrayList<String>();
        for(File file : arrayListFile){
            result.add(file.getName());
        }
        return result;
    }

    public static int[] byteArrayToIntArray(byte[] bytes){
        int[] result = null;

        if(bytes.length != 0){
            int n = 0;
            n = bytes.length / 4 == 0 ? 1 : bytes.length / 4;
            if(bytes.length != 0 && bytes.length % 4 != 0){
                n++;
            }

            result = new int[n];
            for(int j=0; j<result.length; j++){
                result[j] = 0x00000000;
            }
            int k = 0;
            int y = 0;
            for(int i=0; i<bytes.length; i++){
                y = (bytes[i] << (8*(i%4)));
                if(y<0){
                    y = y  & (bytes[i] >>> (8*(3-(i%4))));
                }
                result[k] = result[k] | y;
                if((i+1)%4 == 0){
                    k++;
                }
            }
        }else{
            return null;
        }
        return result;
    }

    public static byte[] intArrayToByteArray(int[] array){
        int k = 0;
        byte [] result = new byte[array.length * 4];
        for(int i=0; i<result.length; i++){
            int t = (array[k] >>> 8*(i%4)) & 0xFF;
            result[i] = (byte) t;
            if((i+1)%4 == 0){
                k++;
            }
        }

        return result;
    }

    public static int[] strToIntArray(String str){
        int[] result = new int[str.length()];
        for(int i=0; i<str.length(); i++){
            result[i] = (int) str.charAt(i);
        }
        return result;
    }

    public static String intArrayToStr(int[] array){
        StringBuilder str = new StringBuilder("");
        for(int i=0; i<array.length; i++){
            str.append((char) array[i]);
        }
        return str.toString();
    }
}
