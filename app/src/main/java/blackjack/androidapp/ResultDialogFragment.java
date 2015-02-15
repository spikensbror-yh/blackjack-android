package blackjack.androidapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class ResultDialogFragment extends DialogFragment {
    //region Events

    public void onOkClick(DialogInterface dialog, int which) {
        dialog.cancel();
    }

    //endregion

    //region Overrides

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getArguments().getInt("stringId"))
                .setPositiveButton(R.string.global_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onOkClick(dialog, which);
                    }
                });

        return builder.create();
    }

    //endregion
}
