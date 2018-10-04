package net.cattaka.android.snippets.example;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import net.cattaka.android.adaptertoolbox.adapter.ScrambleAdapter;
import net.cattaka.android.adaptertoolbox.adapter.listener.ListenerRelay;
import net.cattaka.android.snippets.example.adapter.factory.AccountViewHolderFactory;
import net.cattaka.android.snippets.example.databinding.ActivityAccountsListBinding;
import net.cattaka.android.snippets.example.tracker.IScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class AccountsListActivity extends AppCompatActivity implements
        IScreen,
        View.OnClickListener {
    ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder> mListenerRelay = new ListenerRelay<ScrambleAdapter<?>, RecyclerView.ViewHolder>() {
        @Override
        public void onClick(@NonNull RecyclerView recyclerView, @NonNull ScrambleAdapter<?> adapter, @NonNull RecyclerView.ViewHolder holder, @NonNull View view) {
            super.onClick(recyclerView, adapter, holder, view);
            if (holder instanceof AccountViewHolderFactory.ViewHolder) {
                Account account = mAdapter.getItemAt(holder.getAdapterPosition());
                if (view.getId() == R.id.button_info) {
                    onClickButtonInfo(account);
                } else {
                    startActivity(AccountEditActivity.createIntent(me, account));
                }
            }
        }
    };

    AccountsListActivity me = this;
    ActivityAccountsListBinding mBinding;
    AccountManager mAccountManager;

    ScrambleAdapter<Account> mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_accounts_list);

        mBinding.buttonAdd.setOnClickListener(this);

        mAdapter = new ScrambleAdapter<>(this, new ArrayList<>(), mListenerRelay, new AccountViewHolderFactory());
        mBinding.recycler.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        mBinding.recycler.setAdapter(mAdapter);

        mAccountManager = AccountManager.get(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        reloadAccounts();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_add) {
            startActivity(AccountEditActivity.createIntent(this, null));
        }
    }

    private void reloadAccounts() {
        String accountType = getString(R.string.account_manager_account_type);
        Account[] accounts = mAccountManager.getAccountsByType(accountType);

        mAdapter.getItems().clear();
        mAdapter.getItems().addAll(Arrays.asList(accounts));
        mAdapter.notifyDataSetChanged();
    }

    private void onClickButtonInfo(@NonNull Account account) {
        AccountManagerFuture<Bundle> future1 = mAccountManager.getAuthToken(
                account,
                Constants.AUTH_TOKEN_TYPE,
                true,
                future -> {
                    if (future.isDone()) {
                        try {
                            Bundle bundle = future.getResult();
                            String name = bundle.getString(AccountManager.KEY_ACCOUNT_NAME);
                            String accountType = bundle.getString(AccountManager.KEY_ACCOUNT_TYPE);
                            String authToken = bundle.getString(AccountManager.KEY_AUTHTOKEN);

                            String text = String.format(Locale.ROOT, "name = %s\naccountType = %s\nauthToken = %s\n", name, accountType, authToken);
                            Toast.makeText(me, text, Toast.LENGTH_SHORT).show();
                        } catch (IOException e) {
                            // ignore
                        } catch (OperationCanceledException e) {
                            // ignore
                        } catch (AuthenticatorException e) {
                            // ignore
                        }
                    }
                }, null);
    }
}
