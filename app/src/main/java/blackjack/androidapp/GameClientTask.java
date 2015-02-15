package blackjack.androidapp;

import android.os.AsyncTask;
import android.util.Log;

import java.lang.ref.WeakReference;

import blackjack.game.Result;

public class GameClientTask extends AsyncTask<Void, Result, Void> {
    //region Fields

    private PlayActivity activity;
    private GameClient gameClient;

    //endregion

    //region Getters

    public PlayActivity getActivity() {
        return this.activity;
    }

    public GameClient getGameClient() {
        return this.gameClient;
    }

    //endregion

    public GameClientTask(PlayActivity activity, GameClient gameClient) {
        this.activity = activity;
        this.gameClient = gameClient;
    }

    //region Overrides

    @Override
    protected Void doInBackground(Void... voids) {
        getGameClient().setResultListener(
                new GameClient.ResultListener() {
                    @Override
                    public void onResultReceived(Result result) {
                        publishProgress(new Result[] { result });
                    }
                }
        );

        try {
            getGameClient().listen();
        } catch (Exception e) {
            e.printStackTrace();
            publishProgress(new Result[] { null });
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Result... values) {
        getActivity().handleResult(values[0]);

        super.onProgressUpdate(values);
    }

    //endregion
}
