package com.sinovoice.example;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.sinovoice.hcicloudsdk.api.HciCloudSys;
import com.sinovoice.hcicloudsdk.common.AuthExpireTime;
import com.sinovoice.hcicloudsdk.common.HciErrorCode;
import com.sinovoice.hcicloudsdk.common.InitParam;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author sinovoice
 */
public class HciCloudExampleActivity extends UnityPlayerActivity {
  private static final String TAG = "HciCloudExampleActivity";

  /**
   * 展示引擎返回信息的TextView
   */
  private TextView mLogView;
  private PaintView mPaintView;
  private Button hwrBtn;
  private TextView mResultView;
  private LinearLayout hwrModule;
  private FrameLayout unityScene;

  /**
   * 加载用户信息工具类
   */
  private AccountInfo mAccountInfo;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate( savedInstanceState );

    setContentView( R.layout.main );

    unityScene = findViewById( R.id.unity_scene );
    hwrModule = findViewById( R.id.hwr_module );
    unityScene.addView( mUnityPlayer );
    hwrModule.setVisibility( View.INVISIBLE );

    // 初始化属性
    mLogView = findViewById( R.id.logview );
    mResultView = findViewById( R.id.result_text );
    initPaint();
    //    mPaintView.setBgColor( 0x00000000 );
    //   mPaintView.setColor( 0x00ff0000 );

    mAccountInfo = AccountInfo.getInstance();
    boolean loadResult = mAccountInfo.loadAccountInfo( this );
    if (loadResult) {
      // 加载信息成功进入主界面
      //mLogView.setText( "加载灵云账号成功" );
      //mLogView.setText( "C-L-W-H" );
    } else {
      // 加载信息失败，显示失败界面
      mLogView.setText(
          "加载灵云账号失败！请在assets/AccountInfo.txt文件中填写正确的灵云账户信息，账户需要从www.hcicloud.com开发者社区上注册申请。" );
      return;
    }

    // 加载信息,返回InitParam, 获得配置参数的字符串
    InitParam initParam = getInitParam();
    String strConfig = initParam.getStringConfig();
    Log.i( TAG, "\nhciInit config:" + strConfig );

    // 初始化
    int errCode = HciCloudSys.hciInit( strConfig, this );
    if (errCode != HciErrorCode.HCI_ERR_NONE && errCode != HciErrorCode.HCI_ERR_SYS_ALREADY_INIT) {
      mLogView.append( "\nhciInit error: " + HciCloudSys.hciGetErrorInfo( errCode ) );
      return;
    } else {
      //mLogView.append( "\nhciInit success" );
    }

    // 获取授权/更新授权文件 :
    errCode = checkAuthAndUpdateAuth();
    if (errCode != HciErrorCode.HCI_ERR_NONE) {
      // 由于系统已经初始化成功,在结束前需要调用方法hciRelease()进行系统的反初始化
      mLogView.append(
          "\nCheckAuthAndUpdateAuth error: " + HciCloudSys.hciGetErrorInfo( errCode ) );
      HciCloudSys.hciRelease();
      return;
    }

    hwrFunc();
    /*
    List<Short> data = new ArrayList<Short>();
    data.add( (short) 103 );
    data.add( (short) 283 );
    data.add( (short) 503 );
    data.add( (short) 283 );
    data.add( (short) -1 );
    data.add( (short) 0 );
    data.add( (short) -1 );
    data.add( (short) -1 );
    short[] ss = new short[data.size()];
    for (int i = 0; i < data.size(); i++) {
      ss[i] = data.get( i );
    }
    HciCloudFuncHelper.Func( this, mAccountInfo.getCapKey(), mLogView, ss );
    return;*/
/*    short s[] = new short[] {
        287, 307, 287, 325, 288, 354, 293, 392, 298, 423, 301, 442, 304, 449, -1, 0, 417, 140, 409,
        171, 403, 226, 399, 301, 394, 384, 392, 462, 389, 531, 387, 584, 385, 625, 384, 649, -1, 0,
        429, 470, 439, 479, 460, 501, 488, 530, 514, 552, -1, 0, 759, 77, 733, 96, 690, 138, 651,
        187, 615, 230, 583, 270, 562, 296, 550, 314, 548, 324, 553, 326, 570, 325, 613, 316, 664,
        297, 715, 270, 732, 261, -1, 0, 761, 195, 758, 221, 759, 245, 763, 269, 767, 292, 764, 323,
        -1, 0, 511, 451, 518, 439, 562, 418, 640, 389, 723, 365, 790, 348, 828, 339, 851, 331, 859,
        328, 860, 329, -1, 0, 685, 334, 676, 346, 653, 380, 613, 429, 564, 469, 523, 501, -1, 0,
        578, 450, 597, 435, 629, 418, 665, 407, 708, 402, 745, 404, 783, 408, 801, 412, -1, 0, 737,
        490, 730, 490, 712, 500, 682, 522, 641, 556, 621, 576, 610, 593, 610, 594, -1, 0, 802, 516,
        781, 532, 739, 565, 697, 603, 663, 637, 658, 643, -1, 0, 829, 608, 820, 613, 768, 655, 664,
        743, 643, 762, -1, 0, -1, -1
    };

    HciCloudFuncHelper.Func( this, mAccountInfo.getCapKey(), mLogView, mResultView, s );*/
  }

  public void showHWRModule() {
    runOnUiThread( new Runnable() {
      @Override public void run() {
        hwrModule.setVisibility( View.VISIBLE );
      }
    } );
  }

  public void hideHWRModule() {
    runOnUiThread( new Runnable() {
      @Override public void run() {
        hwrModule.setVisibility( View.INVISIBLE );
      }
    } );
  }

  private void hwrFunc() {
    hwrBtn = findViewById( R.id.hwr_btn );
    hwrBtn.setOnClickListener( new View.OnClickListener() {
      @Override public void onClick(View view) {

        List<Short> data = mPaintView.getPathData();
        data.add( (short) -1 );
        data.add( (short) -1 );

        short[] ss = new short[data.size()];
        for (int i = 0; i < data.size(); i++) {
          ss[i] = data.get( i );
        }
        data.clear();
        mPaintView.clear();

        Log.d( "HciCloudExampleShow", Arrays.toString( ss ) );
        HciCloudFuncHelper.Func( HciCloudExampleActivity.this, mAccountInfo.getCapKey(), mLogView,
            mResultView, ss );
        String str = mResultView.getText().toString();
        UnityPlayer.UnitySendMessage( "Scripts", "ShowEffect", str.substring( str.length() - 2 ) );
      }
    } );
  }

  private void initPaint() {
    mPaintView = (PaintView) findViewById( R.id.paint_view );
    mPaintView.setStrokeWidth( 10 );
    mPaintView.setColor( 0xffff0000 );
    findViewById( R.id.red_btn ).setOnClickListener( new View.OnClickListener() {
      @Override public void onClick(View view) {
        mPaintView.setColor( 0xffff0000 );
      }
    } );
    findViewById( R.id.green_btn ).setOnClickListener( new View.OnClickListener() {
      @Override public void onClick(View view) {
        mPaintView.setColor( 0xff00ff00 );
      }
    } );
    findViewById( R.id.blue_btn ).setOnClickListener( new View.OnClickListener() {
      @Override public void onClick(View view) {
        mPaintView.setColor( 0xff0000ff );
      }
    } );
  }

  @Override protected void onDestroy() {
    // 释放HciCloudSys，当其他能力全部释放完毕后，才能调用HciCloudSys的释放方法
    HciCloudSys.hciRelease();
    mLogView.append( "\nhciRelease" );
    super.onDestroy();
  }

  /**
   * 加载初始化信息
   *
   * // @param context 上下文语境
   *
   * @return 系统初始化参数
   */
  private InitParam getInitParam() {
    //String authDirPath = this.getFilesDir().getAbsolutePath();
    String authDirPath = Environment.getExternalStorageDirectory().getAbsolutePath()
        + File.separator
        + "sinovoice"
        + File.separator
        + "com.sinovoice.example/data";
    // 前置条件：无
    InitParam initparam = new InitParam();
    // 授权文件所在路径，此项必填
    initparam.addParam( InitParam.AuthParam.PARAM_KEY_AUTH_PATH, authDirPath );
    // 灵云云服务的接口地址，此项必填
    initparam.addParam( InitParam.AuthParam.PARAM_KEY_CLOUD_URL,
        AccountInfo.getInstance().getCloudUrl() );
    // 开发者Key，此项必填，由捷通华声提供
    initparam.addParam( InitParam.AuthParam.PARAM_KEY_DEVELOPER_KEY,
        AccountInfo.getInstance().getDeveloperKey() );
    // 应用Key，此项必填，由捷通华声提供
    initparam.addParam( InitParam.AuthParam.PARAM_KEY_APP_KEY,
        AccountInfo.getInstance().getAppKey() );

    // 配置日志参数
    String sdcardState = Environment.getExternalStorageState();
    if (Environment.MEDIA_MOUNTED.equals( sdcardState )) {
      String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();
      String packageName = this.getPackageName();

      String logPath = sdPath
          + File.separator
          + "sinovoice"
          + File.separator
          + packageName
          + File.separator
          + "log"
          + File.separator;

      // 日志文件地址
      File fileDir = new File( logPath );
      if (!fileDir.exists()) {
        fileDir.mkdirs();
      }

      // 日志的路径，可选，如果不传或者为空则不生成日志
      initparam.addParam( InitParam.LogParam.PARAM_KEY_LOG_LEVEL, "5" );
      initparam.addParam( InitParam.LogParam.PARAM_KEY_LOG_FILE_PATH, logPath );
    }

    return initparam;
  }

  /**
   * 获取授权
   *
   * @return true 成功
   */
  private int checkAuthAndUpdateAuth() {

    // 获取系统授权到期时间
    int initResult;
    AuthExpireTime objExpireTime = new AuthExpireTime();
    initResult = HciCloudSys.hciGetAuthExpireTime( objExpireTime );
    if (initResult == HciErrorCode.HCI_ERR_NONE) {
      // 显示授权日期,如用户不需要关注该值,此处代码可忽略
      Date date = new Date( objExpireTime.getExpireTime() * 1000 );
      SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd", Locale.CHINA );
      Log.i( TAG, "expire time: " + sdf.format( date ) );

      if (objExpireTime.getExpireTime() * 1000 > System.currentTimeMillis()) {
        // 已经成功获取了授权,并且距离授权到期有充足的时间(>7天)
        Log.i( TAG, "checkAuth success" );
        return initResult;
      }
    }

    // 获取过期时间失败或者已经过期
    initResult = HciCloudSys.hciCheckAuth();
    if (initResult == HciErrorCode.HCI_ERR_NONE) {
      Log.i( TAG, "checkAuth success" );
      return initResult;
    } else {
      Log.e( TAG, "checkAuth failed: " + initResult );
      return initResult;
    }
  }
}
