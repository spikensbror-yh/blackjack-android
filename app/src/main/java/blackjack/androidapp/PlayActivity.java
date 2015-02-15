package blackjack.androidapp;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.ref.WeakReference;

import blackjack.game.*;

public class PlayActivity extends BaseActivity {
    //region Fields

    private GameClient gameClient;
    private GameClientTask gameTask;

    //endregion

    //region Getters/Setters

    public GameClient getGameClient() {
        return this.gameClient;
    }

    public void setGameClient(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    public GameClientTask getGameTask() {
        return this.gameTask;
    }

    public void setGameTask(GameClientTask gameTask) {
        this.gameTask = gameTask;
    }

    //endregion

    //region Events

    public void onRestartButtonClick(View view) {
        getGameClient().setAction(new Action(Action.Type.Restart));
    }

    public void onStandButtonClick(View view) {
        getGameClient().setAction(new Action(Action.Type.Stand));
    }

    public void onHitButtonClick(View view) {
        getGameClient().setAction(new Action(Action.Type.Hit));
    }

    //endregion

    //region Public Methods

    public void handleResult(Result result) {
        if (result == null) {
            cancelGameTask();
            finishWithResult(RESULT_CONNECTION_FAILURE);

            return;
        }

        updateScores(result);
        updateLists(result);
        if (result.isEnded()) {
            int id;
            if (result.isBust()) {
                id = R.string.result_bust;
            } else if (result.isWin()) {
                id = R.string.result_win;
            } else {
                id = R.string.result_loss;
            }

            if (result.getShouldRestart()) {
                id = R.string.result_restart;
            }

            DialogFragment dialog = createResultDialog(id);
            showDialog(dialog, "ResultDialogFragment");
        }
    }

    //endregion

    //region Protected Methods

    protected void updateScores(Result result) {
        updateScore((TextView)findViewById(R.id.playerScoreLabel), R.string.score_label_player, result.getPlayerHand());
        updateScore((TextView)findViewById(R.id.dealerScoreLabel), R.string.score_label_dealer, result.getDealerHand());
    }

    protected void updateScore(TextView label, int stringId, Hand hand) {
        String output;
        Object[] args;
        if (hand.hasAce() && hand.getHighScore() <= 21) { // TODO: If hand includes ace.
            output = getResources().getString(R.string.score_label_diverse);
            args = new Object[] { getResources().getString(stringId), hand.getHighScore(), hand.getLowScore() }; // TODO: Actual score.
        } else {
            output = getResources().getString(R.string.score_label);
            args = new Object[] { getResources().getString(stringId), hand.getScore() }; // TODO: Actual score.
        }

        label.setText(String.format(output, args));
    }

    protected void updateLists(Result result) {
        updateList((ListView)findViewById(R.id.playerCardList), result.getPlayerHand());
        updateList((ListView)findViewById(R.id.dealerCardList), result.getDealerHand());
    }

    protected void updateList(ListView list, Hand hand) {
        list.setAdapter(new CardListAdapter(getApplicationContext(), hand.getCards()));
    }

    protected void cancelGameTask() {
        if (!getGameTask().isCancelled()) {
            getGameTask().cancel(true);
        }
    }

    protected void finishWithResult(int result) {
        setResult(result);
        finish();
    }

    //endregion

    //region Overrides

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        String host = getExtra("host", "", savedInstanceState);
        int port = getExtra("port", 1, savedInstanceState);

        setGameClient(new GameClient(host, port));
        setGameTask(new GameClientTask(this, getGameClient()));
        getGameTask().execute();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        cancelGameTask();
        finishWithResult(0);

        super.onBackPressed();
    }

    //endregion
}
