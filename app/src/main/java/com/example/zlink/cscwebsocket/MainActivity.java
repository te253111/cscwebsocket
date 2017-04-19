package com.example.zlink.cscwebsocket;

import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.BoolRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.zlink.cscwebsocket.model.Reqdata;
import com.example.zlink.cscwebsocket.model.Reqobject;
import com.example.zlink.cscwebsocket.model.objectAdapter;
import com.google.gson.Gson;
import com.koushikdutta.async.ByteBufferList;
import com.koushikdutta.async.DataEmitter;
import com.koushikdutta.async.callback.CompletedCallback;
import com.koushikdutta.async.callback.DataCallback;
import com.koushikdutta.async.future.Future;
import com.koushikdutta.async.http.AsyncHttpClient;
import com.koushikdutta.async.http.WebSocket;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    Button btn_connect=null;
    Button btn_getdata=null;
    TextView txt_status=null;
    TextView txt_data=null;
    EditText edt_url=null;
    private Future<WebSocket> mWebSocket;
    private Reqdata reqdata;
    StringBuilder str = new StringBuilder();
    private Boolean status;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_connect = (Button) findViewById(R.id.btn_connect);
        edt_url = (EditText) findViewById(R.id.edt_url);
        txt_status = (TextView) findViewById(R.id.txt_status);
        txt_data = (TextView) findViewById(R.id.txt_data);
        btn_getdata = (Button) findViewById(R.id.btn_getdata);




        init();
        status=isSocketConnected();
        txt_status.setText("Status :"+String.valueOf(status));

        btn_connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = edt_url.getText().toString();

                if(btn_connect.getText().equals("connect")){
                    connect(url);
                }else{
                    close();
                    btn_connect.setText("connect");
                    status=isSocketConnected();
                    txt_status.setText("Status :"+String.valueOf(status));
                }
            }
        });

        btn_getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new Gson();
                String data= "[\""+reqdata.getRequestId()+"\","+gson.toJson(reqdata)+"]";
                sentData(data);
            }
        });
    }

    private void init(){
        reqdata = new Reqdata();
        reqdata.setRequestId("ckod78hbxx");
        reqdata.setCmdCode("login");
        reqdata.setCmdMode("api");
        reqdata.setDeviceNo("7589a0733ed98411");

        objectAdapter adapter = new objectAdapter();
        adapter.setClassName("apimodel.request.xx");
        adapter.setObject(new Object());
        adapter.setType("Json");

        Reqobject reqobject = new Reqobject();
        reqobject.setObjectAdapter(adapter);

        reqdata.setParameter(reqobject);
        reqdata.setVersionNo("1.0");

    }

    private void connect(String url){
        this.mWebSocket = AsyncHttpClient.getDefaultInstance().websocket(url,"ws", new AsyncHttpClient.WebSocketConnectCallback() {
            @Override
            public void onCompleted(Exception ex, WebSocket webSocket) {
                if (ex != null) {
                    Log.d("ws",ex.toString());
                    return;
                }
                Log.d("ws","connect");

                webSocket.setStringCallback(new WebSocket.StringCallback() {
                    public void onStringAvailable(String s) {
                        Log.d("ws","string : "+s);
                        str.append("data :").append(s).append("\n\n");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txt_data.setText(str.toString());
                            }
                        });
                    }
                });
                webSocket.setClosedCallback(new CompletedCallback() {
                    public void onCompleted(Exception ex) {
                        Log.d("ws","close");
                    }
                });
                webSocket.setDataCallback(new DataCallback() {
                    public void onDataAvailable(final DataEmitter emitter, ByteBufferList bb) {
                        Log.d("ws","data"+emitter.toString());
                    }
                });
                webSocket.setEndCallback(new CompletedCallback() {
                    public void onCompleted(Exception ex) {
                        Log.d("ws","end");
                    }
                });
            }
        });
        status=isSocketConnected();
        txt_status.setText("Status :"+String.valueOf(status));
        if(isSocketConnected()){
            btn_connect.setText("disconnect");
        }
    }


    private boolean isSocketConnected() {
        try {
            return this.mWebSocket != null && ((WebSocket)this.mWebSocket.get()).isOpen();
        } catch (Exception var2) {
            var2.printStackTrace();
            return false;
        }
    }

    public void close() {
        try {
            if(this.mWebSocket != null && ((WebSocket)this.mWebSocket.get()).isOpen()) {
                ((WebSocket)this.mWebSocket.get()).close();
            }
        } catch (InterruptedException var2) {
            var2.printStackTrace();
        } catch (ExecutionException var3) {
            var3.printStackTrace();
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("str",str.toString());
        outState.putString("url",url);
        outState.putBoolean("status",status);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        url=savedInstanceState.getString("url");
        str.append(savedInstanceState.getString("str"));
        status = savedInstanceState.getBoolean("status");
        if(status){
            connect(url);
        }
        txt_status.setText(String.valueOf(status));
        txt_data.setText(str.toString());

    }

    public synchronized void sentData(String data) {
        try {
            if(this.isSocketConnected()) {
                ((WebSocket)this.mWebSocket.get()).send(data);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
