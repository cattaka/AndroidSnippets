package net.cattaka.android.snippets.example.dialog;

import android.accounts.Account;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import net.cattaka.android.snippets.example.R;
import net.cattaka.android.snippets.example.databinding.DialogAccountEditBinding;

public class AccountEditDialog extends DialogFragment implements View.OnClickListener {
    public static AccountEditDialog newInstance(@Nullable Account origAccount) {
        Bundle args = new Bundle();
        args.putParcelable("origAccount", origAccount);

        AccountEditDialog fragment = new AccountEditDialog();
        fragment.setArguments(args);
        return fragment;
    }

    DialogAccountEditBinding mBinding;
    Account mOrigAccount;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null) {
            mOrigAccount = args.getParcelable("origAccount");
        } else {
            // error case
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DialogAccountEditBinding.inflate(inflater, container, false);

        mBinding.buttonCancel.setOnClickListener(this);
        mBinding.buttonRemove.setOnClickListener(this);
        mBinding.buttonOk.setOnClickListener(this);

        if (mOrigAccount != null) {
            mBinding.editAccountName.setText(mOrigAccount.name);
            mBinding.buttonRemove.setVisibility(View.VISIBLE);
        } else {
            mBinding.buttonRemove.setVisibility(View.INVISIBLE);
        }

        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Dialog dialog = getDialog();
        Window window = dialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setAttributes(lp);
        }
    }

    @Override
    public void onClick(View v) {
        IAccountEditDialogListener listener = findListener();
        if (listener == null) {
            return;
        }

        if (v.getId() == R.id.button_cancel) {
            listener.onClickAccountEditDialogCancel(mOrigAccount);
            dismiss();
        } else if (v.getId() == R.id.button_remove) {
            listener.onClickAccountEditDialogRemove(mOrigAccount);
            dismiss();
        } else if (v.getId() == R.id.button_ok) {
            String name = String.valueOf(mBinding.editAccountName.getText());
            String authToken = String.valueOf(mBinding.editAuthToken.getText());
            listener.onClickAccountEditDialogOk(mOrigAccount, name, authToken);
            dismiss();
        }
    }

    @Nullable
    private IAccountEditDialogListener findListener() {
        Fragment f = getParentFragment();
        if (f instanceof IAccountEditDialogListener) {
            return (IAccountEditDialogListener) f;
        }
        Activity a = getActivity();
        if (a instanceof IAccountEditDialogListener) {
            return (IAccountEditDialogListener) a;
        }
        return null;
    }

    public interface IAccountEditDialogListener {
        void onClickAccountEditDialogCancel(@Nullable Account origAccount);

        void onClickAccountEditDialogRemove(@Nullable Account origAccount);

        void onClickAccountEditDialogOk(@Nullable Account origAccount, @NonNull String name, @NonNull String authToken);
    }
}
