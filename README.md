# Appframework

1.引用方式  
工程的gradle 中添加
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
项目的gradle 中添加
dependencies {
	        implementation 'com.github.yuanhy:Appframework:Tag'
	}
2. 项目中所引用的外部动态库 

    implementation 'com.jakewharton:butterknife:9.0.0'
    annotationProcessor 'com.jakewharton:butterknife-compiler:9.0.0'
    implementation 'com.github.bumptech.glide:glide:3.7.0'
    implementation 'io.reactivex.rxjava2:rxjava:2.2.8'
    implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
    
    implementation 'com.squareup.retrofit2:retrofit:2.5.0'
    implementation 'com.squareup.retrofit2:adapter-rxjava2:2.3.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
    implementation 'com.squareup.okhttp3:okhttp:3.12.0'
    // 日志的拦截器，也可以自行选择
    implementation 'com.squareup.okhttp3:logging-interceptor:3.6.0'
3 在 Application 初始化
  AppFramentUtil.initAppFramentUtil(this);
4 功能介绍：
  a: 断点下载文件
   使用方式 参考：FileDownActivity
  b: MVP 架构模式
     参考 ：RetrofitAndRxjavaDemoActivity
  c: 版本更新
     参考 ：AppUpdataActivity
  d：app状态栏 适配
     使用方式 集成BaseActivity
     实现 setTransparent接口/**
      public void setTransparent() {
       setTransparent(boolean transparent);//true 黑色字体，白色背景;false 白色字体
      }