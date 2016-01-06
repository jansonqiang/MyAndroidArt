package serviceTraining;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

import com.qiang.art.BaseActivity;
import com.qiang.art.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by qiang on 2016/1/6.
 */
public class ServiceActivity extends BaseActivity {

    @Bind(R.id.start_service)
    Button startService;
    @Bind(R.id.stop_service)
    Button stopService;
    @Bind(R.id.bind_service)
    Button bindService;
    @Bind(R.id.unbind_service)
    Button unbindService;
    private GeneralServer.MyBinder myBinder;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (GeneralServer.MyBinder) service;
            myBinder.startDownload();


        }

        @Override
        public void onServiceDisconnected(ComponentName name) {


        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.start_service,R.id.stop_service,
            R.id.bind_service,R.id.unbind_service})
    void clickBut(View v){
        switch (v.getId()) {
            case R.id.start_service:
                Intent startIntent = new Intent(this, GeneralServer.class);
                startService(startIntent);
                break;
            case R.id.stop_service:
                Intent stopIntent = new Intent(this, GeneralServer.class);
                stopService(stopIntent);
                break;
            case R.id.bind_service:
                Intent bindIntent = new Intent(this, GeneralServer.class);
                bindService(bindIntent, serviceConnection, BIND_AUTO_CREATE);
                break;
            case R.id.unbind_service:
                unbindService(serviceConnection);
                break;
            default:
                break;
        }


    }




}
