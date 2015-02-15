package blackjack.androidapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;

public class BaseActivity extends ActionBarActivity {
    //region Constants

    public final static int RESULT_CONNECTION_FAILURE = 420;

    //endregion

    //region Protected Methods

    protected DialogFragment createResultDialog(int stringId) {
        Bundle args = new Bundle();
        args.putInt("stringId", stringId);

        DialogFragment dialog = new ResultDialogFragment();
        dialog.setArguments(args);

        return dialog;
    }

    protected DialogFragment createLoadingDialog(int stringId) {
        Bundle args = new Bundle();
        args.putInt("stringId", stringId);

        DialogFragment dialog = new LoadingDialogFragment();
        dialog.setArguments(args);

        return dialog;
    }

    protected void showDialog(DialogFragment dialog, String tag) {
        dialog.show(getSupportFragmentManager(), "ResultDialogFragment");
    }

    protected <T> T getExtra(String key, T defaultValue, Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Object value = extras.getSerializable(key);
                if (value != null) {
                    return (T)value;
                }
            }
        }
        else {
            return (T)savedInstanceState.getSerializable(key);
        }

        return defaultValue;
    }

    //endregion
}
