// IOnNewBookArrivedListener.aidl
package com.qiang.art.myaidl;
import com.qiang.art.myaidl.Book;
// Declare any non-default types here with import statements

interface IOnNewBookArrivedListener {
   void onNewBookArrived(in Book newBook);
}
