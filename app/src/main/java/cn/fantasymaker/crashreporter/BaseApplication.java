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

package cn.fantasymaker.crashreporter;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import cn.fantasymaker.fmcrashreporter.FMCrashReporter;

/**
 * Created :  2016-10-02
 * Author  :  Fantasymaker
 * Web     :  http://blog.fantasymaker.cn
 * Email   :  me@fantasymaker.cn
 */
public class BaseApplication extends Application {

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;

        // init crash reporter with Application context and a callback
        FMCrashReporter.init(this, new FMCrashReporter.OnHandleExceptionCallback() {
            @Override
            public void onExceptionCaught(Throwable exception) {
                // handle exception as you need
                Log.d("aa", "[Exception caught] " + exception.toString());
            }

            @Override
            public boolean afterExceptionHandled(Thread thread, Throwable exception, FMCrashReporter.CrashInfo crashInfo) {
                // you will get crash info here, you can upload to your server or store locally
                Log.d("aa", crashInfo.toString());
                // do something like toast to inform user what happened
                // return true if you would like to kill app process; false to handle by your own
                return true;
            }
        });
    }

    public Context getContext() {
        return sContext;
    }
}
