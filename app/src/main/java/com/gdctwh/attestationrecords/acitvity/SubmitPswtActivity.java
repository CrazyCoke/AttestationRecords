 package com.gdctwh.attestationrecords.acitvity;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.gdctwh.attestationrecords.utils.IConstants;
import com.gdctwh.attestationrecords.R;

 public class SubmitPswtActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_pswt);

        String action = getIntent().getAction();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        if (IConstants.ACTION_STRING.SET_PSW.equals(action)){
            actionBar.setTitle(R.string.tv_set_psw);
        }else if (IConstants.ACTION_STRING.RESET_PSW.equals(action)){
            actionBar.setTitle(R.string.tv_reset_psw);
        }

        initView();
    }

     private void initView() {

     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
         return super.onOptionsItemSelected(item);
     }
 }
