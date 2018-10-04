package net.cattaka.android.snippets.example;

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

import net.cattaka.android.snippets.example.data.AccountContainer;
import net.cattaka.android.snippets.example.databinding.ActivityAccountEditBinding;

public class AccountEditActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_ORIG_ACCOUNT = "et.cattaka.android.snippets.example.AccountEditActivity.ORIG_ACCOUNT";

    public static Intent createIntent(@NonNull Context context, @NonNull AccountContainer origAccountContainer) {
        Intent intent = new Intent(context, AccountEditActivity.class);
        intent.putExtra(KEY_ORIG_ACCOUNT, origAccountContainer);
        return intent;
    }

    ActivityAccountEditBinding mBinding;
    AccountContainer mOrigAccountContainer;

    AccountManager mAccountManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_account_edit);

        mOrigAccountContainer = getIntent().getParcelableExtra(KEY_ORIG_ACCOUNT);

        mBinding.buttonCancel.setOnClickListener(this);
        mBinding.buttonRemove.setOnClickListener(this);
        mBinding.buttonOk.setOnClickListener(this);

        if (mOrigAccountContainer != null) {
            mBinding.editAccountName.setText(mOrigAccountContainer.getAccount().name);
            mBinding.editAuthToken.setText(mOrigAccountContainer.getAuthToken());
            mBinding.buttonRemove.setVisibility(View.VISIBLE);
        } else {
            mOrigAccountContainer = new AccountContainer();
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
        String accountType = getString(R.string.account_manager_account_type);
        String name = String.valueOf(mBinding.editAccountName.getText());
        String authToken = String.valueOf(mBinding.editAuthToken.getText());
        if (mOrigAccountContainer.getAccount() != null) {
            // TODO: Show block
            mAccountManager.removeAccount(mOrigAccountContainer.getAccount(), future -> {
                Account account = new Account(name, accountType);
                mAccountManager.addAccountExplicitly(account, null, null);
                mAccountManager.setAuthToken(account, Constants.AUTH_TOKEN_TYPE, authToken);
                finish();
            }, null);
        } else {
            Account account = new Account(name, accountType);
            mAccountManager.addAccountExplicitly(account, null, null);
            mAccountManager.setAuthToken(account, Constants.AUTH_TOKEN_TYPE, authToken);
            finish();
        }
    }

    private void onClickRemove() {
        if (mOrigAccountContainer.getAccount() != null) {
            // TODO: Show block
            mAccountManager.removeAccount(mOrigAccountContainer.getAccount(), future -> {
                finish();
            }, null);
        } else {
            // error case
            finish();
        }
    }
}
