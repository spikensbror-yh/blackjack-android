package blackjack.androidapp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import blackjack.game.Action;
import blackjack.game.Result;
import blackjack.network.Client;

public class GameClient {
    //region Fields

    private ResultListener resultListener;
    private Action action;
    private String host;
    private int port;

    //endregion

    public GameClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    //region Getters/Setters

    public ResultListener getResultListener() {
        return this.resultListener;
    }

    public void setResultListener(ResultListener resultListener) {
        this.resultListener = resultListener;
    }

    public Action getAction() {
        return this.action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    //endregion

    //region Public Methods

    public void listen() throws Exception {
        Socket socket = new Socket();
        socket.connect(new InetSocketAddress(getHost(), getPort()), 1000);

        Client networkClient = new Client(socket);

        try {
            Result result;
            while (true) {
                if ((result = (Result) networkClient.getInput().readObject()) == null) {
                    throw new Exception("NULL read from server.");
                }

                getResultListener().onResultReceived(result);

                Action action = waitForAction();
                setAction(null);

                networkClient.getOutput().writeObject(action);
                networkClient.getOutput().reset();
            }
        } catch (Exception e) {
            if (networkClient.getSocket().isConnected()) {
                networkClient.getSocket().close();
            }

            throw e;
        }
    }

    public Action waitForAction() throws InterruptedException {
        while (getAction() == null) {
            Thread.sleep(200);
        }

        return getAction();
    }

    //endregion

    public interface ResultListener {
        public void onResultReceived(Result result);
    }
}
