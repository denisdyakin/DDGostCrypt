package ru.denisdyakin.ddgostcrypt.intent;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Denis on 18.05.2015.
 */
public class ParcelableObject implements Parcelable {
    private ArrayList<String> list;
    public ParcelableObject(ArrayList<String> _list){
        this.list = _list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(list);
    }

    public final Parcelable.Creator<ParcelableObject> CREATOR = new Parcelable.Creator<ParcelableObject>() {
        // распаковываем объект из Parcel
        public ParcelableObject createFromParcel(Parcel in) {
            return new ParcelableObject(in);
        }

        public ParcelableObject[] newArray(int size) {
            return new ParcelableObject[size];
        }
    };

    // конструктор, считывающий данные из Parcel
    private ParcelableObject(Parcel parcel) {
        parcel.readStringList(this.list);
    }

    public ArrayList<String> getList(){
        return this.list;
    }
}
