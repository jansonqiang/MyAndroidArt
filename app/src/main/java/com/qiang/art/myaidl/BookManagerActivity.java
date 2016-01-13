package com.qiang.art.myaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.qiang.art.BaseActivity;
import com.qiang.art.R;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiang on 16/1/10.
 */
public class BookManagerActivity extends BaseActivity {

    @Bind(R.id.but)
    Button but;
    private boolean mBound;
    public static final String TAG = BookManagerActivity.class.getSimpleName();
    public static final int MSSAGE_NEW_BOOK_ARRIVED = 1;

    private IBookManager mRemoteBookManager;


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case MSSAGE_NEW_BOOK_ARRIVED:
                    Log.d(TAG, "收到新书" + msg.obj);
                    break;

            }

        }
    };

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
            Log.d(TAG, "来到32??");
            IBookManager bookManager = IBookManager.Stub.asInterface(service);


            try {
                mRemoteBookManager = bookManager;

                bookManager.addBook(new Book(3, "安卓艺术开发"));

                List<Book> list = bookManager.getBookList();
                Log.d(TAG, "34 list = " + list.getClass().getCanonicalName());
                Log.d(TAG, "34 list = " + list.toString());
                bookManager.registerListener(mIOnNewBookArrivedListener);

            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;

        }
    };


    private IOnNewBookArrivedListener mIOnNewBookArrivedListener = new IOnNewBookArrivedListener.Stub() {
        @Override
        public void onNewBookArrived(Book newBook) throws RemoteException {

            Message message = new Message();
            message.what = MSSAGE_NEW_BOOK_ARRIVED;
            message.obj = newBook;
            handler.sendMessage(message);

          //  Toast.makeText(BookManagerActivity.this,"haha",Toast.LENGTH_SHORT).show(); 这里是Bider的线程池! 不能调用UI线程
        }


    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_manager);
        ButterKnife.bind(this);
        Intent intent = new Intent(this, BookManagerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);

        Log.d(TAG, "来到75??");

        //  Log.d(TAG, "来到61?? isBind="+isBind);

    }


    @OnClick(R.id.but)
    void clickBut(View v){

        try {
        List<Book> books =     mRemoteBookManager.getBookList();
            Toast.makeText(this,books.toString(),Toast.LENGTH_SHORT).show();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {

        if (mRemoteBookManager != null && mRemoteBookManager.asBinder().isBinderAlive()) {

            try {
                mRemoteBookManager.unregisterListener(mIOnNewBookArrivedListener);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

        }


        if (mBound)
            unbindService(mServiceConnection);


        super.onDestroy();
    }
}
