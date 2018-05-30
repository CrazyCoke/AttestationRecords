package com.gdctwh.attestationrecords.acitvity.mine;

import android.os.Bundle;
import android.widget.Button;

import com.gdctwh.attestationrecords.R;
import com.gdctwh.attestationrecords.acitvity.base.BaseActionBarActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscribeActivity extends BaseActionBarActivity {

    @BindView(R.id.subscribe_btn_hot)
    Button subscribeBtnHot;
    @BindView(R.id.subscribe_btn_culture)
    Button subscribeBtnCulture;
    @BindView(R.id.subscribe_btn_attestation)
    Button subscribeBtnAttestation;
    @BindView(R.id.subscribe_btn_collect)
    Button subscribeBtnCollect;
    @BindView(R.id.subscribe_btn_famous)
    Button subscribeBtnFamous;
    @BindView(R.id.subscribe_btn_ct)
    Button subscribeBtnCt;

    @Override
    protected void initView() {
        super.initView();
        subscribeBtnHot.setSelected(true);
        subscribeBtnCulture.setSelected(true);
        subscribeBtnAttestation.setSelected(true);
        subscribeBtnCollect.setSelected(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_subscribe;
    }

    @Override
    protected int getBarTitle() {
        return R.string.title_subscribe;
    }

}
