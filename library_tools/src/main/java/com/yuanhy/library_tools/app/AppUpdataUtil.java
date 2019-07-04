package com.yuanhy.library_tools.app;

import android.content.Context;
import android.support.v4.app.FragmentActivity;

import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;
import com.yuanhy.library_tools.http.okgo.OkGoUpdateHttpUtil;
import com.yuanhy.library_tools.util.AppCommonTool;
import com.yuanhy.library_tools.util.JsonOrEntyTools;
import com.yuanhy.library_tools.util.SharedPreferencesUtil;
import com.yuanhy.library_tools.util.StringUtil;

public class AppUpdataUtil {
    /**
     * App升级
     *
     * @param fragmentActivity
     * @param isRemind         true版本不一致就提示 false 当天提示过就不再显示更新
     * @param isTost           true无新版本出提示语 false 无新版本不出提示语
     */
    public static void updateApk(final FragmentActivity fragmentActivity, final String url, final boolean isRemind, final boolean isTost) {

//
//                String mUpdateUrl = "https://raw.githubusercontent.com/WVector/AppUpdateDemo/master/json/json.txt";
        new UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(fragmentActivity)
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new OkGoUpdateHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(url)
                //全局异常捕获
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        e.printStackTrace();
                    }
                })
                //以下设置，都是可选
                //设置请求方式，默认get
                .setPost(false)
                //不显示通知栏进度条
//                .dismissNotificationProgress()
                //是否忽略版本
//                .showIgnoreVersion()
                //添加自定义参数，默认version=1.0.0（app的versionName）；apkKey=唯一表示（在AndroidManifest.xml配置）
//                .setParams(params)
                //设置点击升级后，消失对话框，默认点击升级后，对话框显示下载进度，如果是强制更新，则设置无效
//                .hideDialogOnDownloading()
                //设置头部，不设置显示默认的图片，设置图片后自动识别主色调，然后为按钮，进度条设置颜色
//                .setTopPic(R.mipmap.top_8)
                //为按钮，进度条设置颜色。
                .setThemeColor(0xffffac5d)
                //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
//                .setTargetPath(path)
                //设置appKey，默认从AndroidManifest.xml获取，如果，使用自定义参数，则此项无效
//                .setAppKey("ab55ce55Ac4bcP408cPb8c1Aaeac179c5f6f")
                .setUpdateDialogFragmentListener(new IUpdateDialogFragmentListener() {
                    @Override
                    public void onUpdateNotifyDialogCancel(UpdateAppBean updateApp) {
                        //用户点击关闭按钮，取消了更新，如果是下载完，用户取消了安装，则可以在 onActivityResult 监听到。
                        SharedPreferencesUtil.getSharedPreferencesUtil(fragmentActivity)
                                .putString(SharedPreferencesUtil.AppDataData, StringUtil.getDate());

                    }
                })
                //不自动，获取
//                .setIgnoreDefParams(true)
                .build()
                //检测是否有新版本
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        try {

                            SharedPreferencesUtil.getSharedPreferencesUtil(fragmentActivity)
                                    .putAppUpDataString(json);
                            AppUpDataEnty appUpDataEnty = (AppUpDataEnty) JsonOrEntyTools.getEnty(json, AppUpDataEnty.class);
                            String newVersion = appUpDataEnty.getNew_version();// jsonObject.optString("new_version");
                            String update = appUpDataEnty.getUpdate();// jsonObject.optString("update");

                            if (isUpdateApp(fragmentActivity)) {
                                update = "Yes";
                            } else {
                                update = "No";
                            }
                            String apk_file_url = appUpDataEnty.getApk_file_url();// jsonObject.optString("apk_file_url");
                            String target_size = appUpDataEnty.getTarget_size();//jsonObject.optString("target_size");
                            String constraint = appUpDataEnty.getConstraint();// jsonObject.optString("constraint");
                            String update_log = appUpDataEnty.getUpdate_log();//jsonObject.optString("update_log");


                            boolean isConstraint = false;
                            if ("true".equals(constraint)) {
                                isConstraint = true;
                            }
//                            apk_file_url="http://app.hc360.com/download/android/youke/app-hc-release.apk" ;
//                            target_size="7.7M";
                            updateAppBean
                                    //（必须）是否更新Yes,No
                                    .setUpdate(update)
                                    //（必须）新版本号，
                                    .setNewVersion(newVersion)
                                    //（必须）下载地址
                                    .setApkFileUrl(apk_file_url)
                                    //测试下载路径是重定向路径
//                                    .setApkFileUrl("http://openbox.mobilem.360.cn/index/d/sid/3282847")
//                                    .setUpdateDefDialogTitle(String.format("AppUpdate 是否升级到%s版本？", newVersion))
                                    //（必须）更新内容
//                                    .setUpdateLog(jsonObject.optString("update_log"))
                                    //测试内容过度
//                                    .setUpdateLog("测试")
                                    .setUpdateLog(update_log)
//                                    .setUpdateLog("今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说相对于其他行业来说今天我们来聊一聊程序员枯燥的编程生活，相对于其他行业来说\r\n")
                                    //大小，不设置不显示大小，可以不设置
                                    .setTargetSize(target_size)
                                    //是否强制更新，可以不设置
                                    .setConstraint(isConstraint);
                            //设置md5，可以不设置
//                                    .setNewMd5(jsonObject.optString("new_md5"));
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, final UpdateAppManager updateAppManager) {
                        String time = SharedPreferencesUtil.getSharedPreferencesUtil(fragmentActivity)
                                .getString(SharedPreferencesUtil.AppDataData);
                        if (!updateApp.isConstraint() && !StringUtil.isNull(time)) {
                            if (time.equals(StringUtil.getDate()) && !isRemind) {
                                return;
                            }
                        }
                        updateAppManager.showDialogFragment();
                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
//                        CProgressDialogUtils.showProgressDialog(HomeActivity.this);
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
//                        CProgressDialogUtils.cancelProgressDialog(HomeActivity.this);
                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    public void noNewApp(String error) {
                    }
                });

    }

    public static boolean isUpdateApp(Context context) {
        try {
            String str = SharedPreferencesUtil.getSharedPreferencesUtil(context).getAppUpDataString();
            AppUpDataEnty appUpDataEnty = (AppUpDataEnty) JsonOrEntyTools.getEnty(str, AppUpDataEnty.class);
            String localVersion = AppCommonTool.getAppVersionName(context);
            String[] localVersions = localVersion.split("\\.");
            String serverVersion = appUpDataEnty.getNew_version();
            String[] serverVersions = serverVersion.split("\\.");
            for (int i = 0; i < localVersions.length; i++) {
                int nub = Integer.parseInt(localVersions[i]);
                int nub2 = Integer.parseInt(serverVersions[i]);
                if (nub < nub2) {
                    return true;
                }
            }
        } catch (NullPointerException e) {
            e.getStackTrace();
            return false;
        }

        return false;
    }
}
