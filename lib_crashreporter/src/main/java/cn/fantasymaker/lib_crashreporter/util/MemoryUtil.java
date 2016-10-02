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

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;

import cn.fantasymaker.lib_crashreporter.CrashHandler;

/**
 * Created :  2016-10-02
 * Author  :  Fantasymaker
 * Web     :  http://blog.fantasymaker.cn
 * Email   :  me@fantasymaker.cn
 */
public class MemoryUtil {

    private static Context sContext = CrashHandler.getContext();
    private static ActivityManager.MemoryInfo sMemoryInfo = new ActivityManager.MemoryInfo();

    private MemoryUtil() throws IllegalAccessException {
        throw new IllegalAccessException("Instantiation is not allowed! Use static methods only!");
    }

    private static ActivityManager getActivityManager() {
        return (ActivityManager) sContext.getSystemService(Context.ACTIVITY_SERVICE);
    }

    /**
     * Get used size of ram.
     *
     * @return used size in byte
     */
    public static long getUsedMemory() {
        return getTotalMemory() - getAvailableMemory();
    }

    /**
     * get available size of ram.
     *
     * @return available size in byte
     */
    public static long getAvailableMemory() {
        getActivityManager().getMemoryInfo(sMemoryInfo);
        return sMemoryInfo.availMem;
    }

    /**
     * get total size of ram. Work for API 16 and above.
     *
     * @return total size in byte
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static long getTotalMemory() {
        getActivityManager().getMemoryInfo(sMemoryInfo);
        return sMemoryInfo.totalMem;
    }

    /**
     * Get app used ram
     *
     * @return app used size in byte
     */
    public static long getAppUsedMemory() {
        return Runtime.getRuntime().totalMemory();
    }

}
