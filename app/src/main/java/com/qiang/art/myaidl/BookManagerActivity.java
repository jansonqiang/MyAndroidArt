package com.qiang.art.myaidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.qiang.art.BaseActivity;
import com.qiang.art.R;

import java.util.List;

/**
 * Created by qiang on 16/1/10.
 */
public class BookManagerActivity extends BaseActivity {

    private boolean mBound;
    public static final String TAG =BookManagerActivity.class.getSimpleName();




     ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBound = true;
            Log.d(TAG, "来到32??");
            IBookManager bookManager = IBookManager.Stub.asInterface(service);


            try {
                bookManager.addBook(new Book(3,"安卓艺术开发"));

               List<Book> list = bookManager.getBookList();
                Log.d(TAG, "34 list = "+list.getClass().getCanonicalName());
                Log.d(TAG, "34 list = "+list.toString());

            } catch (RemoteException e) {
                e.printStackTrace();
            }


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;

        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_book_manager);
        Intent intent = new Intent(this,BookManagerService.class);
     bindService(intent,mServiceConnection, Context.BIND_AUTO_CREATE);

        Log.d(TAG,"来到75??");

      //  Log.d(TAG, "来到61?? isBind="+isBind);

    }

    @Override
    protected void onDestroy() {

        if(mBound)
            unbindService(mServiceConnection);


        super.onDestroy();
    }
}
