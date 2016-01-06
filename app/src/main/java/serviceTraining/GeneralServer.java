package serviceTraining;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.qiang.art.R;

/**研究Service怎么跟Acitivty通讯
 * Created by qiang on 2016/1/6.
 */
public class GeneralServer extends Service {
    public static final String TAG = GeneralServer.class.getSimpleName();

    private MyBinder mBinder = new MyBinder();

    public static final int notificationId1 =1;

    private Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        this.mContext=this;
        Log.d(TAG, "onCreate() executed");
        setForeground();
    }

    /***
     * 设置为前台Service
     */
    private void setForeground(){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        Notification notif = builder
                .setContentTitle("通知")
                .setContentText("这是GeneralServerd的通知")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        Intent notificationIntent =  new Intent(mContext,ServiceActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        notificationIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        builder.setContentIntent(notifyIntent);
// Notifications are issued by sending them to the
// NotificationManager system service.
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// Builds an anonymous Notification object from the builder, and
// passes it to the NotificationManager
        mNotificationManager.notify(notificationId1, builder.build());


        startForeground(notificationId1, builder.build());


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand() executed"+"flags = "+flags +" startId="+startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    class MyBinder extends Binder {

        public void startDownload() {
            Log.d("TAG", "startDownload() executed");
            // 执行具体的下载任务
        }

    }


}
