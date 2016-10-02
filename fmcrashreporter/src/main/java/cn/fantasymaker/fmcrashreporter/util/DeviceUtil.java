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

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Field;

/**
 * Created :  2016-08-03
 * Author  :  Fantasymaker
 * Web     :  http://blog.fantasymaker.cn
 * Email   :  me@fantasymaker.cn
 */
public class DeviceUtil {




    //====================


    /**
     * Get name of the underlying board, like "goldfish". e.g. Che1-CL20
     *
     * @return board name
     */
    public static String getBoardName() {
        return Build.BOARD;
    }

    /**
     * Get bootloader version number
     *
     * @return bootloader version number
     */
    public static String getBootloaderVersionNumber() {
        return Build.BOOTLOADER;
    }

    /**
     * Get brand name of device; e.g. Honor
     *
     * @return brand name
     */
    public static String getDeviceBrand() {
        return Build.BRAND;
    }

    /**
     * name of the instruction set (CPU type + ABI convention) of native code.
     * e.g. armeabi-v7a
     *
     * @return name of the instruction set
     */
    public static String getCpuAbi() {
        return Build.CPU_ABI;
    }

    /**
     * name of the second instruction set (CPU type + ABI convention) of native code<br/>
     * e.g. armeabi
     *
     * @return name of the second instruction set
     */
    public static String getCpuAbi2() {
        return Build.CPU_ABI2;
    }

    /**
     * Get device industrial design name. e.g. Che1
     *
     * @return device name
     */
    public static String getDeviceName() {
        return Build.DEVICE;
    }

    /**
     * Get a build ID string meant for displaying to the user.<br/>
     * e.g. Che1-CL20V100R001CHNC00B286
     *
     * @return build version id
     */
    public static String getDeviceVersion() {
        return Build.DISPLAY;
    }

    /**
     * Get a string that uniquely identifies this build.<br/>
     * e.g. Honor/Che1-CL20/Che1:4.4.4/Che1-CL20/C00B286:user/ota-rel-keys,release-keys
     *
     * @return a unique string
     */
    public static String getFingerprint() {
        return Build.FINGERPRINT;
    }

    /**
     * Get name of the hardware. e.g. qcom
     *
     * @return hardware name
     */
    public static String getHardware() {
        return Build.HARDWARE;
    }

    /**
     * e.g. huawei-desktop
     *
     * @return not sure
     */
    public static String getHost() {
        return Build.HOST;
    }

    /**
     * Either a changelist number, or a label like "M4-rc20". e.g. Che1-CL20
     *
     * @return changelist number or label
     */
    public static String getId() {
        return Build.ID;
    }

    /**
     * Get manufacturer of the product/hardware. e.g. HUAWEI
     *
     * @return manufacturer
     */
    public static String getManufacture() {
        return Build.MANUFACTURER;
    }

    /**
     * Get end-user-visible device model name. e.g. Che1-CL20
     *
     * @return device model
     */
    public static String getDeviceModel() {
        return Build.MODEL;
    }

    /**
     * Get the name of the overall product. e.g. Che1-CL20
     *
     * @return product name
     */
    public static String getProductName() {
        return Build.PRODUCT;
    }

    /**
     * The radio firmware version number
     *
     * @return radio firmware version number
     */
    public static String getRadio() {
        return Build.RADIO;
    }

    /**
     * Get hardware serial number, if available. Alphanumeric only, case-insensitive. e.g. 240995d3ec58
     *
     * @return hardware serial number
     */
    public static String getSerial() {
        return Build.SERIAL;
    }

    /**
     * Comma-separated tags describing the build, like "unsigned,debug". e.g. release-keys
     *
     * @return build tags
     */
    public static String getBuildTags() {
        return Build.TAGS;
    }

    /**
     * e.g. 1462954988000
     *
     * @return not sure
     */
    public static long getTime() {
        return Build.TIME;
    }

    /**
     * The type of build, like "user" or "eng".
     *
     * @return build type
     */
    public static String getType() {
        return Build.TYPE;
    }

    /**
     * e.g. huawei
     *
     * @return not sure
     */
    public static String getUser() {
        return Build.USER;
    }


    /**
     * Get the current development codename, or the string "REL" if this is a release build.
     *
     * @return
     */
    public static String getDevelopCodeName() {
        return Build.VERSION.CODENAME;
    }

    /**
     * Get android version code. e.g. 19
     *
     * @return android version code
     */
    public static int getAndroidVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    /**
     * Get android version name. e.g. 4.4.4
     *
     * @return android version name
     */
    public static String getAndroidVersionName() {
        return Build.VERSION.RELEASE;
    }





    public static String getAll() {
        Field[] fields = Build.class.getDeclaredFields();
        StringBuffer stringBuffer = new StringBuffer();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                stringBuffer.append(field.getName())
                        .append("=")
                        .append(field.get(null).toString())
                        .append("\n");
            } catch (Exception e) {
                Log.e("aa", "an error occured when collect crash info", e);
            }
        }
        return stringBuffer.toString();
    }

}
