package ru.denisdyakin.ddgostcrypt.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ru.denisdyakin.ddgostcrypt.R;

/**
 * Created by Denis on 06.05.2015.
 */
public class Add_file_adapter extends ArrayAdapter<String> {
    private final Activity context;
    private final List<String> values;

    public Add_file_adapter(Activity context, List<String> values){
        super(context, R.layout.row, values);
        this.context = context;
        this.values = values;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.row, parent, false);
        TextView txt = (TextView) rowView.findViewById(R.id.rowtext);
        ImageView img = (ImageView) rowView.findViewById(R.id.icon);
        String str = values.get(position);
        txt.setText(str);
        if(str.endsWith("/")){
            img.setImageResource(R.drawable.folder_icon);
        }else if(str.endsWith(".ddc")) {
            img.setImageResource(R.drawable.locked_icon);
        }else{
            img.setImageResource(R.drawable.file_icon);
        }

        return rowView;
    }
}
