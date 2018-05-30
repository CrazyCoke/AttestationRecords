package com.gdctwh.attestationrecords.acitvity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.gdctwh.attestationrecords.acitvity.base.BaseActionBarActivity;
import com.gdctwh.attestationrecords.utils.IConstants;
import com.gdctwh.attestationrecords.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResetPswAndUserClauseActivity extends BaseActionBarActivity implements View.OnClickListener {

    @BindView(R.id.edt_resetpsw_account)
    EditText edtResetpswAccount;
    @BindView(R.id.edt_code)
    EditText edtCode;
    @BindView(R.id.btn_get_code)
    Button btnGetCode;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.lly_reset_psw)
    LinearLayout llyResetPsw;
    @BindView(R.id.sll_user_clause)
    ScrollView sllUserClause;


    protected void initEvent() {
        btnNext.setOnClickListener(this);
    }

    protected void initView() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


    }

    @Override
    protected int getContentView() {
        return R.layout.activity_rset_psw_and_user_clause;
    }

    @Override
    protected int getBarTitle() {
        String action = getIntent().getAction();
        if (IConstants.ACTION_STRING.RESET_PSW.equals(action)){
            llyResetPsw.setVisibility(View.VISIBLE);
            sllUserClause.setVisibility(View.GONE);
            return R.string.title_reset_psw;
        }else if (IConstants.ACTION_STRING.USER_CLAUSE.equals(action)){
            llyResetPsw.setVisibility(View.GONE);
            sllUserClause.setVisibility(View.VISIBLE);
            return R.string.title_user_clause;

        }
        return R.string.title_user_clause;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                Intent intent = new Intent(this,SubmitPswtActivity.class);
                intent.setAction(IConstants.ACTION_STRING.RESET_PSW);
                startActivity(intent);
                break;
        }
    }
}
