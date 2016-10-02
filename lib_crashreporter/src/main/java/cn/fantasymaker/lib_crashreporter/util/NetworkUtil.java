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

package cn.fantasymaker.lib_crashreporter.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import cn.fantasymaker.lib_crashreporter.CrashHandler;

/**
 * Created :  2016-10-02
 * Author  :  Fantasymaker
 * Web     :  http://blog.fantasymaker.cn
 * Email   :  me@fantasymaker.cn
 */
public class NetworkUtil {

    private static final String NETWORK_TYPE_DISCONNECT = "NETWORK_TYPE_DISCONNECT";
    private static final String NETWORK_TYPE_WIFI = "NETWORK_TYPE_WIFI";
    private static final String NETWORK_TYPE_2G = "NETWORK_TYPE_2G";
    private static final String NETWORK_TYPE_3G = "NETWORK_TYPE_3G";
    private static final String NETWORK_TYPE_4G = "NETWORK_TYPE_4G";
    private static final String NETWORK_TYPE_UNKNOWN = "NETWORK_TYPE_UNKNOWN";

    private static Context sContext = CrashHandler.getContext();

    private static ConnectivityManager getConnectivityManager(){
        return (ConnectivityManager) sContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    /**
     * Get network connection type
     *
     * @return type
     */
    public static String getNetworkType() {
        String type = NETWORK_TYPE_DISCONNECT;
        NetworkInfo info = getConnectivityManager().getActiveNetworkInfo();
        if (info == null) {
            type = NETWORK_TYPE_DISCONNECT;
        } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            type = NETWORK_TYPE_WIFI;
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int subType = info.getSubtype();
            if (subType == TelephonyManager.NETWORK_TYPE_CDMA
                    || subType == TelephonyManager.NETWORK_TYPE_GPRS
                    || subType == TelephonyManager.NETWORK_TYPE_EDGE) {
                type = NETWORK_TYPE_2G;
            } else if (subType == TelephonyManager.NETWORK_TYPE_UMTS
                    || subType == TelephonyManager.NETWORK_TYPE_HSDPA
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_A
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_0
                    || subType == TelephonyManager.NETWORK_TYPE_EVDO_B) {
                type = NETWORK_TYPE_3G;
            } else if (subType == TelephonyManager.NETWORK_TYPE_LTE) {// LTE是3g到4g的过渡，是3.9G的全球标准
                type = NETWORK_TYPE_4G;
            } else {
                type = NETWORK_TYPE_UNKNOWN;
            }
        }
        return type;
    }
}
