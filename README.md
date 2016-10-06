# FMCrashReporter

FMCrashReporter is a simple android crash information collector.


## Usage

```
// init crash reporter with Application context and a callback
FMCrashReporter.init(this, new FMCrashReporter.OnHandleExceptionCallback() {
    @Override
    public void onExceptionCaught(Throwable exception) {
        // handle exception as you need
        Log.d(TAG, "[Exception caught] " + exception.toString());
    }

    @Override
    public boolean afterExceptionHandled(Thread thread, Throwable exception, FMCrashReporter.CrashInfo crashInfo) {
        // you will get crash info here, you can upload to your server or store locally
        Log.d(TAG, crashInfo.toString());
        // do something like toast to inform user what happened
        // return true if you would like to kill app process; false to handle by your own
        return true;
    }
});
```

When crash caught, you will get crash info as well as device info

```
CrashInfo{
    localTime='1475741108606', 
    imei='865743028444098', 
    deviceBrand='Honor', 
    deviceModel='Che1-CL20', 
    manufacturer='HUAWEI', 
    screenWidth=720, 
    screenHeight=1280, 
    dpi=320, 
    dpiScale=2.0, 
    fontDpiScale=2.0, 
    androidVersionName='4.4.4', 
    androidVersionCode='19', 
    phoneOperatorName='中国移动 4G', 
    phoneNetworkType='LTE', 
    networkState='NETWORK_TYPE_WIFI', 
    sysAvailableMem=292089856, 
    sysUsedMem=1711345664, 
    sysTotalMem=2003435520, 
    appUsedMem=15847424, 
    isRoot=false, 
    appVersionCode=1, 
    appVersionName='1.0', 
    exception='Could not execute method for android:onClick
            android.support.v7.app.AppCompatViewInflater$DeclaredOnClickListener.onClick(AppCompatViewInflater.java:293)
            android.view.View.performClick(View.java:4457)
            android.view.View$PerformClick.run(View.java:18491)
            android.os.Handler.handleCallback(Handler.java:733)
            android.os.Handler.dispatchMessage(Handler.java:95)
            android.os.Looper.loop(Looper.java:136)
            android.app.ActivityThread.main(ActivityThread.java:5336)
            java.lang.reflect.Method.invokeNative(Native Method)
            java.lang.reflect.Method.invoke(Method.java:515)
            com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:871)
            com.android.internal.os.ZygoteInit.main(ZygoteInit.java:687)
            dalvik.system.NativeStart.main(Native Method)
            Caused by: null
            java.lang.reflect.Method.invokeNative(Native Method)
            java.lang.reflect.Method.invoke(Method.java:515)
            android.support.v7.app.AppCompatViewInflater$DeclaredOnClickListener.onClick(AppCompatViewInflater.java:288)
            android.view.View.performClick(View.java:4457)
            android.view.View$PerformClick.run(View.java:18491)
            android.os.Handler.handleCallback(Handler.java:733)
            android.os.Handler.dispatchMessage(Handler.java:95)
            android.os.Looper.loop(Looper.java:136)
            android.app.ActivityThread.main(ActivityThread.java:5336)
            java.lang.reflect.Method.invokeNative(Native Method)
            java.lang.reflect.Method.invoke(Method.java:515)
            com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:871)
            com.android.internal.os.ZygoteInit.main(ZygoteInit.java:687)
            dalvik.system.NativeStart.main(Native Method)'
}
```

## Lisence

```
Apache 2.0
```