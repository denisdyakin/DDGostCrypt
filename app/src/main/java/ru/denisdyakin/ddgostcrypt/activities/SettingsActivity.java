package ru.denisdyakin.ddgostcrypt.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ru.denisdyakin.ddgostcrypt.R;

/**
 * Created by Denis on 04.05.2015.
 */
public class SettingsActivity extends Activity implements View.OnClickListener {
    private Button safeButton;
    private EditText synchEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.safeButton = (Button) findViewById(R.id.save_settings_btn);
        this.synchEdit = (EditText) findViewById(R.id.ed_text_synch);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.save_settings_btn:{
                safe();
                break;
            }
        }
    }

    private void safe(){
        if(checkValues()){

        }
    }

    private boolean checkValues(){

        return false;
    }
}
