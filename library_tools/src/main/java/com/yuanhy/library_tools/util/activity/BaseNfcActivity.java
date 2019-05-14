package com.yuanhy.library_tools.util.activity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.yuanhy.library_tools.util.LogCatUtil;
import com.yuanhy.library_tools.util.SdCardUtil;

import java.io.File;

/**
 * Created by Administrator on 2019/4/9.
 */

public class BaseNfcActivity extends Activity {
    public String TAG;
    public Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        TAG = getClass().getName();
        initFileDirs();
    }

    protected void initFileDirs() {
        String pathUrl1 = SdCardUtil.getExternalSdCardPath()
                + "/zenking/zksc/myCaches";
        String pathUrl2 = SdCardUtil.getExternalSdCardPath()
                + "/zenking/zksc/myRecorder";
        String pathUrl3 = SdCardUtil.getExternalSdCardPath()
                + "/zenking/zksc/myPic";
        File fileDir1 = new File(pathUrl1);
        if (!fileDir1.exists()) {
            fileDir1.mkdirs();
        }
        File fileDir2 = new File(pathUrl2);
        if (!fileDir2.exists()) {
            fileDir2.mkdirs();
        }
        File fileDir3 = new File(pathUrl3);
        if (!fileDir3.exists()) {
            fileDir3.mkdirs();
        }
    }

    public boolean isShow;// 页面是否可见，只有可见才可以setNfc
    public Boolean isFirst = false;
    public NfcAdapter nfcAdapter;
    public PendingIntent mPendingIntent;
    public IntentFilter[] mFilters;
    public String[][] mTechLists;

    public void initNFC() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (null != nfcAdapter) {
            mPendingIntent = PendingIntent.getActivity(this, 0,
                    new Intent(this, getClass())
                            .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

            IntentFilter ndef = new IntentFilter(
                    NfcAdapter.ACTION_NDEF_DISCOVERED);
            try {
                ndef.addDataType("*/*");
                Log.i(TAG, "NFC 种植成功");
            } catch (IntentFilter.MalformedMimeTypeException e) {
                Log.i(TAG, "NFC 种植失败");
                throw new RuntimeException("fail", e);
            }
            mFilters = new IntentFilter[]{ndef};
            mTechLists = new String[][]{
                    new String[]{NfcF.class.getName()},
                    new String[]{NfcA.class.getName()},
                    new String[]{NfcB.class.getName()},
                    new String[]{NfcV.class.getName()}};
            isFirst = true;
            setNFC();
        } else {
            boolean nfc = getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC);
            Log.i(TAG, "检查NFC设备是否支持 nfc:" + nfc);
        }
    }

    public void setNFC() {
        if (null != nfcAdapter && isShow) {
            nfcAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters,
                    mTechLists);
            Log.i(TAG, "NFC 设置成功");
        } else {
            Log.i(TAG, "NFC 设置失败  isShow： " + isShow + "nfcAdapter: " + nfcAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShow = true;
        setNfcAdapter();
    }

    public void setNfcAdapter() {
        if (nfcAdapter == null) {
            initNFC();
        }
        if (null != nfcAdapter && isFirst && isShow) {
            nfcAdapter.enableForegroundDispatch(this, mPendingIntent, mFilters,
                    mTechLists);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShow = false;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

//注意复制下面的到子类，然后 启动模式设置singleTop
    //1111111111111111111111111111
//     <activity
//    android:name=".activity.NfcActivity"
//    android:launchMode="singleTop" />
//2222222222222222222222222222222222222222222222
//     <uses-permission android:name="android.permission.NFC" />
//    <!-- 要求当前设备必须要有NFC芯片 -->
//    <uses-feature
//    android:name="android.hardware.nfc"
//    android:required="true" />
//3333333333333333333333333333333333333333333333333
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        byte[] myNFCID = intent.getByteArrayExtra(NfcAdapter.EXTRA_ID);
//        String t = bytesToHexString(myNFCID);
//        try {
//            setCode(String.valueOf(Long.parseLong(t, 16)));
//        } catch (NumberFormatException e) {
//            LogCatUtil.getInstance(context).e(TAG,"无法识别信息！");
//            e.printStackTrace();
//        }
//
//    }
//    // 字符序列转换为16进制字符串
//    private String bytesToHexString(byte[] src) {
//        StringBuilder stringBuilder = new StringBuilder();
//        if (src == null || src.length <= 0) {
//            return null;
//        }
//        char[] buffer = new char[2];
//        for (int i = 0; i < src.length; i++) {
//            buffer[1] = Character.forDigit((src[i] >>> 4) & 0x0F, 16);
//            buffer[0] = Character.forDigit(src[i] & 0x0F, 16);
//            stringBuilder.append(buffer);
//        }
//        stringBuilder.reverse();
//        return stringBuilder.toString();
//    }
//
//    /**
//     * 此方法提供给使用nfc扫描时设置文本内容
//     *
//     * @param num
//     */
//    public void setCode(String num) {
//        // 请求接口获取数据
//        if (!TextUtils.isEmpty(num)) {
//            if (num.length() == 0) {
//                LogCatUtil.getInstance(context).e(TAG,"无法识别信息！");
//                return;
//            }
//            LogCatUtil.getInstance(context).i(TAG,"设备识别号码："+num);
//        } else {
//            LogCatUtil.getInstance(context).e(TAG,"无法识别信息！");
//        }
//    }


}
