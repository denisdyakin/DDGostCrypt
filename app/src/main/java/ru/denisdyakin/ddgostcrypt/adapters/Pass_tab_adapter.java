package ru.denisdyakin.ddgostcrypt.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.denisdyakin.ddgostcrypt.R;
import ru.denisdyakin.ddgostcrypt.intent.Pass_tab_object;

/**
 * Created by Denis on 22.05.2015.
 */
public class Pass_tab_adapter extends ArrayAdapter<Pass_tab_object>{
    private final Context context;
    private final List<Pass_tab_object> values;

    public Pass_tab_adapter(Context context, List<Pass_tab_object> values){
        super(context, R.layout.row_pass, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row_pass, parent, false);
        TextView txt_web_adress = (TextView) row.findViewById(R.id.web_site_adress_txt);
        TextView txt_login = (TextView) row.findViewById(R.id.login_web_txt);
        TextView txt_password = (TextView) row.findViewById(R.id.password_web_txt);
        Pass_tab_object object = values.get(position);
        txt_web_adress.setText(object.getWeb_adress());
        txt_login.setText(object.getLogin());
        txt_password.setText(object.getPassword());
        return row;
    }
}
