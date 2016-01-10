// IBookManager.aidl
package com.qiang.art.myaidl;
import com.qiang.art.myaidl.Book;
import com.qiang.art.myaidl.IOnNewBookArrivedListener;

// Declare any non-default types here with import statements

interface IBookManager {
   List<Book> getBookList();
   void addBook(in Book book);
        void registerListener(IOnNewBookArrivedListener listener);
        void unregisterListener(IOnNewBookArrivedListener listener);
}
