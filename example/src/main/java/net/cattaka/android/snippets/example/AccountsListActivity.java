package net.cattaka.android.snippets.example;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import net.cattaka.android.snippets.example.databinding.ActivityAccountsListBinding;
import net.cattaka.android.snippets.example.dialog.AccountEditDialog;
import net.cattaka.android.snippets.example.tracker.IScreen;

public class AccountsListActivity extends AppCompatActivity implements
        IScreen,
        View.OnClickListener,
        AccountEditDialog.IAccountEditDialogListener {
    static final String TAG_DIALOG_ACCOUNT_EDIT = "DIALOG_ACCOUNT_EDIT";

    ActivityAccountsListBinding mBinding;
    AccountManager mAccountManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_accounts_list);

        mBinding.buttonAdd.setOnClickListener(this);

        mAccountManager = (AccountManager) getSystemService(ACCOUNT_SERVICE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        reloadAccounts();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_add) {
            AccountEditDialog dialog = AccountEditDialog.newInstance(null);
            dialog.show(getSupportFragmentManager(), TAG_DIALOG_ACCOUNT_EDIT);
        }
    }

    private void reloadAccounts() {
        Account[] accounts = mAccountManager.getAccountsByType(Constants.ACCOUNT_TYPE);

        Log.d("test", String.valueOf(accounts));

    }

    @Override
    public void onClickAccountEditDialogRemove(@Nullable Account origAccount) {
        mAccountManager.removeAccount(origAccount, future -> {
            reloadAccounts();
        }, null);
    }

    @Override
    public void onClickAccountEditDialogOk(@Nullable Account origAccount, @NonNull String name, @NonNull String authToken) {
        if (origAccount != null) {
            mAccountManager.removeAccount(origAccount, future -> {
                Account account = new Account(name, Constants.ACCOUNT_TYPE);
                mAccountManager.addAccountExplicitly(account, null, null);
                reloadAccounts();
            }, null);
        } else {
            Account account = new Account(name, Constants.ACCOUNT_TYPE);
            mAccountManager.addAccountExplicitly(account, null, null);
            reloadAccounts();
        }

    }
}
