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
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by qiang on 16/1/10.
 */
public class BookManagerService extends Service {


    public static final String TAG = BookManagerService.class.getSimpleName();

    private AtomicBoolean mIsServiceDistory = new AtomicBoolean(false);

    private CopyOnWriteArrayList<Book> mBookList = new CopyOnWriteArrayList<>();

    private CopyOnWriteArrayList<IOnNewBookArrivedListener> mListenerList = new CopyOnWriteArrayList<>();


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

            if(!mListenerList.contains(listener)){
                mListenerList.add(listener);
            }else{
                Log.d(TAG,"listener exists");
            }

            Log.d(TAG, "listener.size =" + mListenerList.size());

        }

        @Override
        public void unregisterListener(IOnNewBookArrivedListener listener) throws RemoteException {

            if(!mListenerList.contains(listener)){

                mListenerList.remove(listener);

            }else{
                Log.d(TAG,"this listern not found");
            }

            Log.d(TAG,"listener.size ="+mListenerList.size());

        }
    };






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
        return mBinder;
    }


}
