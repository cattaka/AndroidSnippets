package net.cattaka.android.snippets.example.data;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;

public class AccountContainer implements Parcelable {
    public static Creator<AccountContainer> CREATOR = new Creator<AccountContainer>() {
        @Override
        public AccountContainer createFromParcel(Parcel source) {
            AccountContainer container = new AccountContainer();
            container.setAccount(source.readParcelable(AccountContainer.class.getClassLoader()));
            container.setAuthToken(source.readString());
            return container;
        }

        @Override
        public AccountContainer[] newArray(int size) {
            return new AccountContainer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(account, flags);
        dest.writeString(authToken);
    }

    private Account account;
    private String authToken;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
