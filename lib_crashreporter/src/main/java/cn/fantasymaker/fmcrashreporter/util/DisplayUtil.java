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

package cn.fantasymaker.fmcrashreporter.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * Created :  2016-10-02
 * Author  :  Fantasymaker
 * Web     :  http://blog.fantasymaker.cn
 * Email   :  me@fantasymaker.cn
 */
public class DisplayUtil {

    private static DisplayMetrics getDisplayMetrics() {
        return Resources.getSystem().getDisplayMetrics();
    }

    /**
     * Get screen width in pixel
     *
     * @return screen width in pixel
     */
    public static int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /**
     * Get screen height in pixel
     *
     * @return screen height in pixel
     */
    public static int getScreenHeight() {
        return getDisplayMetrics().heightPixels;
    }

    /**
     * Get dpi (dots per inch)
     *
     * @return dpi
     */
    public static int getDpi() {
        return getDisplayMetrics().densityDpi;
    }

    /**
     * Get scale of dpi. 160dpi = 1; 320dpi=2
     *
     * @return scale of dpi
     */
    public static float getDpiScale() {
        return getDisplayMetrics().density;
    }

    /**
     * Get scale of font dpi.
     *
     * @return scale of font dpi
     */
    public static float getFontScale() {
        return getDisplayMetrics().scaledDensity;
    }

    /**
     * Return true if screen is vertical displaying; false otherwise
     *
     * @return true if screen is vertical displaying; false otherwise
     */
    public static boolean isScreenVertical() {
        return getScreenWidth() < getScreenHeight();
    }
}
