package com.gdctwh.attestationrecords.acitvity.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gdctwh.attestationrecords.R;
import com.gdctwh.attestationrecords.acitvity.base.BaseActionBarActivity;

public class AuthenticateActivity extends BaseActionBarActivity {


    @Override
    protected int getContentView() {
        return R.layout.activity_authenticate;
    }

    @Override
    protected int getBarTitle() {
        return R.string.title_authenticate;
    }
}
