package ru.denisdyakin.ddgostcrypt.resources;

/**
 * Created by Denis on 08.05.2015.
 */
public class Res {
    private static final String BROADCAST_ACTION_UPDATE_LIST = "ru.safegost.develop.backbroadcast";
    private static final String BROADCAST_ACTION_UPDATE_PASSLIST = "ru.safegost.develop.updatepasslist";
    private static final String BROADCAST_ACTION_ADD_FILES = "ru.safegost.develop.addfilesbroadcast";
    private static final String EXTRA_CONST_ADD_FILES = "ru.safegost.develop.extralistoffiles";
    private static final String DIRECTORY_TEMP_CONST = "/safegost/tempf/";
    private static final String DIRECTORY_CONST = "/safegost/files/";
    private static final int[] synch = new int[]{0,0};
    private static final int sizeOfHash = 256;

    public static int[] getSync(){
        return synch;
    }

    public static String getBroadcastActionUpdateList(){
        return BROADCAST_ACTION_UPDATE_LIST;
    }

    public static String getBroadcastActionUpdatePasslist(){
        return BROADCAST_ACTION_UPDATE_PASSLIST;
    }

    public static String getBroadcastActionAddFiles(){
        return BROADCAST_ACTION_ADD_FILES;
    }

    public static String getExtraConstAddFiles(){
        return EXTRA_CONST_ADD_FILES;
    }

    public static String getDirectoryTempConst(){
        return DIRECTORY_TEMP_CONST;
    }

    public static String getDirectoryConst(){
        return DIRECTORY_CONST;
    }

    public static int getSizeOfHash(){ return sizeOfHash; }
}
