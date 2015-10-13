package com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.R;
import com.experiment1.s3.srm.android_laravel_test2_simpleimplementation.helper.SaveDataHelper;

public class SettingActivity extends Activity implements View.OnClickListener{


    SaveDataHelper saveDataHelper;

    EditText clientIdEditText;
    EditText clientSecretEditText;
    EditText loginUrlEditText;
    EditText projectRetrivalUrlEditText;
    EditText baseUrlEditText;
    Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        this.setTitle("Settings");

        saveDataHelper = new SaveDataHelper(this.getApplicationContext());
//
        clientIdEditText = (EditText) findViewById(R.id.clientIdeditText);
        clientSecretEditText = (EditText) findViewById(R.id.clientSecrettextView);

        baseUrlEditText = (EditText) findViewById(R.id.baseUrleditText);
        loginUrlEditText = (EditText) findViewById(R.id.loginUrleditText);
        projectRetrivalUrlEditText = (EditText) findViewById(R.id.projectUrleditText);
//
//
//
        clientIdEditText.setText(saveDataHelper.getPrefClientId());
        clientSecretEditText.setText(saveDataHelper.getPrefClientSecret());
        baseUrlEditText.setText(saveDataHelper.getPrefBaseUrl());
        loginUrlEditText.setText(saveDataHelper.getPrefLoginUrl());
        projectRetrivalUrlEditText.setText(saveDataHelper.getPrefProjectRetrivalUrl());


        saveButton = (Button) findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {


        switch(v.getId()){

            case R.id.saveButton :

          saveDataHelper.updateSettings(clientIdEditText.getText().toString(),
                        clientSecretEditText.getText().toString()
                        , baseUrlEditText.getText().toString()
                        , loginUrlEditText.getText().toString(),
                        projectRetrivalUrlEditText.getText().toString());


                    Toast.makeText(this ,"Saved ..",Toast.LENGTH_SHORT).show();
            break;

        }


    }
}
