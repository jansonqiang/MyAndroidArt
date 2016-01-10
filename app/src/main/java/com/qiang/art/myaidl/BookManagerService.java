package com.qiang.art.myaidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by qiang on 16/1/10.
 */
public class BookManagerService extends Service {


    public static final String TAG = BookManagerService.class.getSimpleName();
    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();


    private Binder mBinder = new IBookManager.Stub(){

        @Override
        public List<Book> getBookList() throws RemoteException {
            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);

        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {

        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {

        }
    };



    class MyBinder extends Binder {

        public void startDownload() {
            Log.d("TAG", "startDownload() executed");
            // 执行具体的下载任务
        }

    }
    private MyBinder mBinder2 = new MyBinder();



    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, TAG + " 启动了!!");
        mBookList.add(new Book(1, "adnroid"));
        mBookList.add(new Book(2,"ios"));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       Log.d(TAG,"运行到service 60~~~");
        return mBinder2;
    }


}
