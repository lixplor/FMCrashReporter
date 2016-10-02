/*
 *     Copyright © 2016 Fantasymaker
 *
 *     Permission is hereby granted, free of charge, to any person obtaining a copy
 *     of this software and associated documentation files (the "Software"), to deal
 *     in the Software without restriction, including without limitation the rights
 *     to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *     copies of the Software, and to permit persons to whom the Software is
 *     furnished to do so, subject to the following conditions:
 *
 *     The above copyright notice and this permission notice shall be included in all
 *     copies or substantial portions of the Software.
 *
 *     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *     IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *     FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *     AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *     LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *     OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *     SOFTWARE.
 */

package cn.fantasymaker.lib_crashreporter;

import android.app.Application;
import android.content.Context;
import android.os.Process;

import cn.fantasymaker.lib_crashreporter.util.AppUtil;
import cn.fantasymaker.lib_crashreporter.util.DeviceUtil;
import cn.fantasymaker.lib_crashreporter.util.DisplayUtil;
import cn.fantasymaker.lib_crashreporter.util.MemoryUtil;
import cn.fantasymaker.lib_crashreporter.util.NetworkUtil;
import cn.fantasymaker.lib_crashreporter.util.PhoneUtil;
import cn.fantasymaker.lib_crashreporter.util.RootUtil;

/**
 * Created :  2016-07-24
 * Author  :  Fantasymaker
 * Web     :  http://blog.fantasymaker.cn
 * Email   :  me@fantasymaker.cn
 */
public class FMCrashReporter implements Thread.UncaughtExceptionHandler {

    private static Context sContext;
    private static Thread.UncaughtExceptionHandler sDefaultHandler;
    private static FMCrashReporter sFMCrashReporter = new FMCrashReporter();
    private static OnHandleExceptionCallback sOnHandleExceptionCallback;

    private FMCrashReporter() {
        sDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    /**
     * Get singleton
     *
     * @return FMCrashReporter
     */
    public static FMCrashReporter getInstance() {
        return sFMCrashReporter;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        //print exception
        ex.printStackTrace();
        // Gather excetion and device info
        CrashInfo crashInfo = gatherInfo(thread, ex);
        // handle exception and return handle result
        boolean isExceptionHandled = handleException(ex);
        // do different treatment according to handle result
        if (!isExceptionHandled && sDefaultHandler != null) {
            // if custom handle failed, try system default handler
            sDefaultHandler.uncaughtException(thread, ex);
        } else {
            // if custom handle succeed, do callback
            if (sOnHandleExceptionCallback != null) {
                if (sOnHandleExceptionCallback.afterExceptionHandled(thread, ex, crashInfo)) {
                    Process.killProcess(Process.myPid());
                }
            }
        }
    }

    private static String createExceptionString(Throwable ex) {
        if (ex == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(ex.getLocalizedMessage())
                .append("\n");
        for (StackTraceElement element : ex.getStackTrace()) {
            stringBuffer.append(element.toString())
                    .append("\n");
        }
        Throwable cause = ex.getCause();
        stringBuffer.append("Caused by: ")
                .append(cause.getLocalizedMessage())
                .append("\n");
        for (StackTraceElement element : cause.getStackTrace()) {
            stringBuffer.append(element.toString())
                    .append("\n");
        }
        return stringBuffer.toString();
    }

    private static CrashInfo gatherInfo(Thread thread, Throwable ex) {
        String exception = createExceptionString(ex);

        CrashInfo crashInfo = new CrashInfo(
                PhoneUtil.getDeviceIdIMEI(),
                DeviceUtil.getDeviceBrand(),
                DeviceUtil.getDeviceModel(),
                DeviceUtil.getManufacture(),
                DisplayUtil.getScreenWidth(),
                DisplayUtil.getScreenHeight(),
                DisplayUtil.getDpi(),
                DisplayUtil.getDpiScale(),
                DisplayUtil.getFontScale(),
                DeviceUtil.getAndroidVersionName(),
                DeviceUtil.getAndroidVersionCode(),
                PhoneUtil.getNetworkOperatorName(),
                PhoneUtil.getNetworkTypeName(),
                NetworkUtil.getNetworkType(),
                MemoryUtil.getAvailableMemory(),
                MemoryUtil.getUsedMemory(),
                MemoryUtil.getTotalMemory(),
                MemoryUtil.getAppUsedMemory(),
                RootUtil.isDeviceRooted(),
                AppUtil.getVersionCode(),
                AppUtil.getVersionName(),
                exception
        );
        return crashInfo;
    }

    /**
     * Custom to handle exception
     *
     * @param exception exception
     * @return true if exception has been handled; Otherwise false.
     */
    private boolean handleException(Throwable exception) {
        if (exception != null && sOnHandleExceptionCallback != null) {
            sOnHandleExceptionCallback.onExceptionCaught(exception);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set listener for handling exception
     *
     * @param onHandleExceptionCallback onHandleExceptionCallback
     */
    public static void setOnHandleExceptionListener(OnHandleExceptionCallback onHandleExceptionCallback) {
        sOnHandleExceptionCallback = onHandleExceptionCallback;
    }

    public static void init(Application application, OnHandleExceptionCallback onHandleExceptionCallback) {
        sContext = application;
        // set callback
        sOnHandleExceptionCallback = onHandleExceptionCallback;
        // set as default handler
        Thread.setDefaultUncaughtExceptionHandler(getInstance());
    }

    public static Context getContext() {
        return sContext;
    }

    public interface OnHandleExceptionCallback {

        /**
         * Callback to custom how to handle exception<br/>
         * You can do other operations such as store/upload expection to server, toast a tip to inform user
         *
         * @param exception exception
         */
        void onExceptionCaught(Throwable exception);

        /**
         * Callback to custom what to do after exception was handled<br/>
         * You can upload crash info and quit app here for example
         *
         * @param thread    thread which raise exception
         * @param exception exception
         * @param crashInfo crashInfo
         * @return true if kill this app; false otherwise
         */
        boolean afterExceptionHandled(Thread thread, Throwable exception, CrashInfo crashInfo);
    }

    /**
     * Contains crash and device info
     */
    public static class CrashInfo {
        private String imei;
        private String deviceBrand;
        private String deviceModel;
        private String manufacturer;
        private int screenWidth;
        private int screenHeight;
        private int dpi;
        private float dpiScale;
        private float fontDpiScale;
        private String androidVersionName;
        private int androidVersionCode;
        private String phoneOperatorName;
        private String phoneNetworkType;
        private String networkState;
        private long sysAvailableMem;
        private long sysUsedMem;
        private long sysTotalMem;
        private long appUsedMem;
        private boolean isRoot;
        private int appVersionCode;
        private String appVersionName;

        private String exception;

        public CrashInfo(String imei, String deviceBrand, String deviceModel, String manufacturer, int screenWidth, int screenHeight, int dpi, float dpiScale, float fontDpiScale, String androidVersionName, int androidVersionCode, String phoneOperatorName, String phoneNetworkType, String networkState, long sysAvailableMem, long sysUsedMem, long sysTotalMem, long appUsedMem, boolean isRoot, int appVersionCode, String appVersionName, String exception) {
            this.imei = imei;
            this.deviceBrand = deviceBrand;
            this.deviceModel = deviceModel;
            this.manufacturer = manufacturer;
            this.screenWidth = screenWidth;
            this.screenHeight = screenHeight;
            this.dpi = dpi;
            this.dpiScale = dpiScale;
            this.fontDpiScale = fontDpiScale;
            this.androidVersionName = androidVersionName;
            this.androidVersionCode = androidVersionCode;
            this.phoneOperatorName = phoneOperatorName;
            this.phoneNetworkType = phoneNetworkType;
            this.networkState = networkState;
            this.sysAvailableMem = sysAvailableMem;
            this.sysUsedMem = sysUsedMem;
            this.sysTotalMem = sysTotalMem;
            this.appUsedMem = appUsedMem;
            this.isRoot = isRoot;
            this.appVersionCode = appVersionCode;
            this.appVersionName = appVersionName;
            this.exception = exception;
        }

        @Override
        public String toString() {
            return "CrashInfo{" +
                    "imei='" + imei + '\'' +
                    ", deviceBrand='" + deviceBrand + '\'' +
                    ", deviceModel='" + deviceModel + '\'' +
                    ", manufacturer='" + manufacturer + '\'' +
                    ", screenWidth=" + screenWidth +
                    ", screenHeight=" + screenHeight +
                    ", dpi=" + dpi +
                    ", dpiScale=" + dpiScale +
                    ", fontDpiScale=" + fontDpiScale +
                    ", androidVersionName='" + androidVersionName + '\'' +
                    ", androidVersionCode='" + androidVersionCode + '\'' +
                    ", phoneOperatorName='" + phoneOperatorName + '\'' +
                    ", phoneNetworkType='" + phoneNetworkType + '\'' +
                    ", networkState='" + networkState + '\'' +
                    ", sysAvailableMem=" + sysAvailableMem +
                    ", sysUsedMem=" + sysUsedMem +
                    ", sysTotalMem=" + sysTotalMem +
                    ", appUsedMem=" + appUsedMem +
                    ", isRoot=" + isRoot +
                    ", appVersionCode=" + appVersionCode +
                    ", appVersionName='" + appVersionName + '\'' +
                    ", exception='" + exception + '\'' +
                    '}';
        }
    }
}