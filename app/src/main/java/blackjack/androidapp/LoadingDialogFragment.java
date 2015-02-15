package blackjack.androidapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class LoadingDialogFragment extends DialogFragment {
    //region Overrides

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getArguments().getInt("stringId"));

        return builder.create();
    }

    //endregion
}
