package ru.denisdyakin.ddgostcrypt.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import ru.denisdyakin.ddgostcrypt.R;
import ru.denisdyakin.ddgostcrypt.utils.Math;

/**
 * Created by Denis on 06.05.2015.
 */
public class Add_file_adapter extends ArrayAdapter<String> {
    private final Activity context;
    private final List<String> values;
    private TextView txt;
    List<String> fileListString;
    private String str = null;

    public Add_file_adapter(Activity context, List<String> values, ArrayList<File> fileList){
        super(context, R.layout.row, values);
        this.context = context;
        this.values = values;
         if(fileList.size() != 0 ){
             this.fileListString = Math.arrayListFileToArrayListString2(fileList);
         }else{
             fileListString = null;
         }
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.row, parent, false);
        txt = (TextView) rowView.findViewById(R.id.rowtext);
        ImageView img = (ImageView) rowView.findViewById(R.id.icon);
        this.str = values.get(position);
        txt.setText(str);
        if(str.endsWith("/")){
            img.setImageResource(R.drawable.folder_icon);
        }else if(str.endsWith(".dd")) {
            img.setImageResource(R.drawable.locked_icon);
        }else{
            img.setImageResource(R.drawable.file_icon);
        }

        if(fileListString != null){
            if(fileListString.contains(this.str)){
                txt.setTextColor(this.context.getResources().getColor(R.color.file_is_choosen));
            }
        }

        return rowView;
    }


}
