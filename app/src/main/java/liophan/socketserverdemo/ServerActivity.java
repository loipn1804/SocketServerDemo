package liophan.socketserverdemo;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Copyright (c) 2017, Stacck Pte Ltd. All rights reserved.
 *
 * @author Lio <lphan@stacck.com>
 * @version 1.0
 * @since March 16, 2017
 */

public class ServerActivity extends AppCompatActivity {

    public static final int SERVER_PORT = 6003;

    @BindView(R.id.txtLog)
    TextView txtLog;

    private ServerSocket mServerSocket;
    private Handler mUIHandler;
    private Thread mServerThread = null;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server);
        ButterKnife.bind(this);

        mUIHandler = new Handler();

        mServerThread = new Thread(new ServerThread());
        mServerThread.start();
    }

    @Override
    protected void onStop() {
        if (mServerSocket != null) {
            try {
                mServerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onStop();
    }

    private class ServerThread implements Runnable {

        @Override
        public void run() {
            Socket socket = null;
            try {
                mServerSocket = new ServerSocket(SERVER_PORT);
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (!Thread.currentThread().isInterrupted()) {
                try {
                    socket = mServerSocket.accept();

                    CommunicationThread communicationThread = new CommunicationThread(socket);
                    new Thread(communicationThread).start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class CommunicationThread implements Runnable {

        private Socket mClientSocket;
        private BufferedReader input;

        public CommunicationThread(Socket clientSocket) {
            mClientSocket = clientSocket;

            try {
                this.input = new BufferedReader(new InputStreamReader(mClientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    String read = input.readLine();

                    mUIHandler.post(new UIThread(read));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class UIThread implements Runnable {

        private String mMessage;

        public UIThread(String message) {
            mMessage = message;
        }

        @Override
        public void run() {
            txtLog.setText(txtLog.getText().toString() + "\n" + mMessage);
        }
    }
}
