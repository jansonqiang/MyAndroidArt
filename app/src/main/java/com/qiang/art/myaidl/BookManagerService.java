package com.qiang.art.myaidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by qiang on 16/1/10.
 */
public class BookManagerService extends Service {


    public static final String TAG = BookManagerService.class.getSimpleName();

    private AtomicBoolean mIsServiceDistory = new AtomicBoolean(false);

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private RemoteCallbackList<IOnNewBookArrivedListener> mListenerList = new RemoteCallbackList<>(); //夸进程专用list


    private Binder mBinder = new IBookManager.Stub(){

        @Override
        public List<Book> getBookList() throws RemoteException {

            SystemClock.sleep(5000);

            return mBookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            mBookList.add(book);

        }

        @Override
        public void registerListener(IOnNewBookArrivedListener listener) throws RemoteException {

            mListenerList.register(listener);


        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {

            mListenerList.unregister(listener);



        }
    };

    private void onNewBookArrived(Book book) throws RemoteException {


        final int n = mListenerList.beginBroadcast();
         for(int i =0;i<n;i++){

             IOnNewBookArrivedListener listener = mListenerList.getBroadcastItem(i);
             listener.onNewBookArrived(book);
         }
        mListenerList.finishBroadcast();
    }


    private class ServiceWorker implements Runnable{

        @Override
        public void run() {

            while (!mIsServiceDistory.get()){

                Log.d(TAG,"运行到94了");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int bookId = mBookList.size()+1;
                Book book = new Book(bookId,"new Book#"+bookId);
                mBookList.add(book);

                try {
                    onNewBookArrived(book);
                } catch (RemoteException e) {

                    e.printStackTrace();
                }


            }

        }
    }





    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, TAG + " 启动了!!");
        mBookList.add(new Book(1, "adnroid"));
        mBookList.add(new Book(2,"ios"));
        new Thread(new ServiceWorker()).start();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
       Log.d(TAG,"运行到service 60~~~");
        return mBinder;
    }

    @Override
    public void onDestroy() {
        mIsServiceDistory.set(true);
        super.onDestroy();
    }
}
