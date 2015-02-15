package blackjack.androidapp;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.net.Socket;
import java.util.Random;

import blackjack.network.Client;

public class ConnectActivity extends BaseActivity {
    //region Events

    public void onConnectButtonClick(View view) {
        EditText hostText = (EditText)findViewById(R.id.hostText);
        EditText portText = (EditText)findViewById(R.id.portText);
        String host;
        int port;

        try {
            host = hostText.getText().toString();
            port = Integer.parseInt(portText.getText().toString());
        }
        catch (Exception e) {
            e.printStackTrace();

            return;
        }

        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra("host", host);
        intent.putExtra("port", port);
        startActivityForResult(intent, 0);
    }

    //endregion

    //region Overrides

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_CONNECTION_FAILURE) {
            DialogFragment dialog = createResultDialog(R.string.connect_failed);
            showDialog(dialog, "ResultDialogFragment");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
    }

    //endregion
}
