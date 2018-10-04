package net.cattaka.android.snippets.example.account_manager;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;

import net.cattaka.android.snippets.example.Constants;

public class AccountAuthenticator extends AbstractAccountAuthenticator {
    Context mContext;

    public AccountAuthenticator(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        String name = "Test";
        String type = Constants.ACCOUNT_TYPE;
        String token = "fuga";

        final Account account = new Account(name, type);
        final AccountManager manager = AccountManager.get(mContext);

        manager.addAccountExplicitly(account, null, null);

        final Bundle result = new Bundle();
        result.putString(AccountManager.KEY_ACCOUNT_NAME, name);
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, type);
        result.putString(AccountManager.KEY_AUTHTOKEN, token);
        return result;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        AccountManager manager = AccountManager.get(mContext);
        String authToken = manager.peekAuthToken(account, Constants.AUTH_TOKEN_TYPE);

        Bundle result = new Bundle();
        result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
        result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
        if (Constants.AUTH_TOKEN_TYPE.equals(authTokenType)) {
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
        }
        return result;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        return null;
    }
}
