package com.example.mynewstoday.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;
import com.example.mynewstoday.R;
import com.example.mynewstoday.fragment.CareFragment;
import com.example.mynewstoday.fragment.HomeFragment;
import com.example.mynewstoday.fragment.MineFragment;
import com.example.mynewstoday.utils.Util;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

//    登录QQ
    public static final String TAG = "MainActivity";
    public static final String APP_ID = "101568619";//瀹樻柟鑾峰彇鐨凙PPID
    public static Tencent mTencent;
    public static BaseUiListener mIUiListener;
    public static String openidString;
    public UserInfo mUserInfo;
    public TextView nicknameTextView;
    public ImageView userlogo;
    public TextView openidTextView;
    public String nicknameString;
    Bitmap bitmap = null;
    ImageView imageView;
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;


//   底部碎片
    private Fragment f1;
    private Fragment f2;
    private Fragment f3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headview=navigationView.inflateHeaderView(R.layout.nav_header_main);//不加这行不然获取不到头像
//                点击左滑头像登录
        //用来显示OpenID的textView           openidTextView = findViewById(R.id.user_id);
        //用来显示昵称的textview
        nicknameTextView =headview.findViewById(R.id.nav_idname);
        //用来显示头像的Imageview
        userlogo =headview.findViewById(R.id.nav_imageView);
        //传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(APP_ID,getApplicationContext());
        imageView = headview.findViewById(R.id.nav_imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonLogin();
            }
        });



//    设置底部导航三个碎片  实现页面切换
        initFragment();
        RadioGroup rg = (RadioGroup)findViewById(R.id.tabs);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch (checkedId){
                    case R.id.home:
                        ft.show(f1).hide(f2).hide(f3).commit();
                        break;
                    case R.id.care:
                        ft.show(f2).hide(f1).hide(f3).commit();
                        break;
                    case R.id.mine:
                        ft.show(f3).hide(f1).hide(f2).commit();
                        break;
                }
            }
        });
        mIUiListener = new BaseUiListener();


        //创建IntentFilter的实例
        intentFilter = new IntentFilter();
       /**
        * 当网络状态发生变化时,系统发出的正是一条值为android.net.conn.CONNECTIVITY_CHANGE的广播
         * 也就是我们广播接收器想要监听什么广播，在这里添加相对应的Action就行了
         */
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");

       /**
            * 实例化NetworkChangeReceiver的实例和IntentFilter的实例都传了进去,
         * NetworkChangeReceiver就会收到所有值为android.net.conn.CONNECTIVITY_CHANGE的广播，
         * 也就实现了监听网络变化的功能
         */
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);

    }


    /**
     38      * 动态注册的广播接收器一定都要取消注册才行,这里是在onDestroy()方法中通过调用
     39      * unregisterReceiver()方法来实现的
     40      */
     @Override
     protected void onDestroy() {
                super.onDestroy();
                unregisterReceiver(networkChangeReceiver);
     }

     /*
            实现广播
      */
    public class NetworkChangeReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            //实现网络广播
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo!=null && networkInfo.isAvailable()){
                Toast.makeText(MainActivity.this , "network is available",Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(MainActivity.this , "network is unavailable",Toast.LENGTH_LONG).show();
            }
        }
    }


    public void buttonLogin(){
        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */

        //all表示获取所有权限
        mTencent.login(MainActivity.this , "all",mIUiListener);
    }


    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    public class BaseUiListener implements IUiListener {

        @Override
        public void onComplete(Object response) {
            Toast.makeText(MainActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(MainActivity.this,qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(final Object response) {
                        Log.e(TAG,"登录成功"+response.toString());
                        try {
                            //获得的数据是JSON格式的，获得你想获得的内容
                            //如果你不知道你能获得什么，看一下下面的LOG
                            Log.e(TAG, "-------------"+response.toString());
                            openidString = ((JSONObject) response).getString("openid");
                            openidTextView.setText(openidString);
                            Log.e(TAG, "-------------"+openidString);
                            //access_token= ((JSONObject) response).getString("access_token");              //expires_in = ((JSONObject) response).getString("expires_in");
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        QQToken qqToken = mTencent.getQQToken();
                        UserInfo info = new UserInfo(MainActivity.this, qqToken);
                        info.getUserInfo(new IUiListener() {
                            @Override
                            public void onComplete(Object o) {
                                Log.e(TAG, "---------------111111");
                                Message msg = new Message();
                                msg.obj = response;
                                msg.what = 0;
                                mHandler.sendMessage(msg);
                                Log.e(TAG, "-----111---"+response.toString());
                                /**由于图片需要下载所以这里使用了线程，如果是想获得其他文字信息直接
                                 * 在mHandler里进行操作
                                 *
                                 */
                                new Thread(){

                                    @Override
                                    public void run() {
                                        // TODO Auto-generated method stub
                                        JSONObject json = (JSONObject)response;
                                        try {
                                            bitmap = Util.getbitmap(json.getString("figureurl_qq_2"));
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        Message msg = new Message();
                                        msg.obj = bitmap;
                                        msg.what = 1;
                                        mHandler.sendMessage(msg);
                                    }
                                }.start();
                            }

                            @Override
                            public void onError(UiError uiError) {

                            }

                            @Override
                            public void onCancel() {

                            }
                        });
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(MainActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(MainActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }

    }



    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        nicknameString = response.getString("nickname");

                        nicknameTextView.setText(nicknameString);
                        Log.e(TAG, "--"+ nicknameString);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }else if(msg.what == 1){
                Bitmap bitmap = (Bitmap)msg.obj;
                userlogo.setImageBitmap(bitmap);

            }
        }

    };

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * QQ分享是注意：。。。。特别注意：一定要添加以下代码，才可以从回调listener中获取到消息
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /*
            侧滑栏的点击事件
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_start) {
            // Handle the camera action
        } else if (id == R.id.nav_phone) {

        } else if (id == R.id.nav_help) {

        } else if (id == R.id.nav_message) {

        } else if (id == R.id.nav_set) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


//    设置底部导航三个碎片  实现页面切换
    private void initFragment() {
        f1 = new HomeFragment();
        f2 = new CareFragment();
        f3 = new MineFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container,f1,"home")
                .add(R.id.container,f2,"care")
                .add(R.id.container,f3,"mine")
                .commit();
        getSupportFragmentManager().beginTransaction()
                .hide(f2)
                .hide(f3)
                .commit();
    }
}
