package net.cattaka.android.snippets.example.dialog;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import net.cattaka.android.snippets.example.Constants;
import net.cattaka.android.snippets.example.R;
import net.cattaka.android.snippets.example.databinding.ActivityAccountEditBinding;

public class AccountEditActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_ORIG_ACCOUNT = "et.cattaka.android.snippets.example.AccountEditActivity.ORIG_ACCOUNT";

    public static Intent createIntent(@NonNull Context context, @Nullable Account origAccount) {
        Intent intent = new Intent(context, AccountEditActivity.class);
        intent.putExtra(KEY_ORIG_ACCOUNT, origAccount);
        return intent;
    }

    ActivityAccountEditBinding mBinding;
    Account mOrigAccount;

    AccountManager mAccountManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_edit);

        mOrigAccount = getIntent().getParcelableExtra(KEY_ORIG_ACCOUNT);

        mBinding.buttonCancel.setOnClickListener(this);
        mBinding.buttonRemove.setOnClickListener(this);
        mBinding.buttonOk.setOnClickListener(this);

        if (mOrigAccount != null) {
            mBinding.editAccountName.setText(mOrigAccount.name);
            mBinding.buttonRemove.setVisibility(View.VISIBLE);
        } else {
            mBinding.buttonRemove.setVisibility(View.INVISIBLE);
        }

        mAccountManager = AccountManager.get(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_cancel) {
            finish();
        } else if (v.getId() == R.id.button_remove) {
            onClickRemove();
        } else if (v.getId() == R.id.button_ok) {
            onClickOk();
        }
    }

    private void onClickOk() {
        String name = String.valueOf(mBinding.editAccountName.getText());
        String authToken = String.valueOf(mBinding.editAuthToken.getText());
        if (mOrigAccount != null) {
            // TODO: Show block
            mAccountManager.removeAccount(mOrigAccount, future -> {
                Account account = new Account(name, Constants.ACCOUNT_TYPE);
                mAccountManager.addAccountExplicitly(account, null, null);
                finish();
            }, null);
        } else {
            Account account = new Account(name, Constants.ACCOUNT_TYPE);
            mAccountManager.addAccountExplicitly(account, null, null);
            finish();
        }
    }

    private void onClickRemove() {
        if (mOrigAccount != null) {
            mAccountManager.removeAccountExplicitly(mOrigAccount);
        } else {
            // error case
        }
        finish();
    }
}
