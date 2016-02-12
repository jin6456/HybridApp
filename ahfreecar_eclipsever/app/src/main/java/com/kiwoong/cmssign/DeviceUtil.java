package com.kiwoong.cmssign;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class DeviceUtil {

    public static String getPhoneNumber(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String phone = telephony.getLine1Number();
        if(phone.contains("+82")) {
            phone = phone.replace("+82", "0");
        }
        return phone;
    }

    public static String getDeviceId(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephony.getDeviceId();
    }

    public static String getAndroidId() {
        return Settings.Secure.ANDROID_ID;
    }

    public static boolean isUsim(Context context) {
        TelephonyManager telephony = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (telephony.getSimState() == TelephonyManager.SIM_STATE_ABSENT) {
            return false;
        }
        else {
            return true;
        }
    }

    public static String getIp(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        final NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if(wifi.isAvailable()) {
            WifiManager myWifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
            WifiInfo myWifiInfo = myWifiManager.getConnectionInfo();
            int ipAddress = myWifiInfo.getIpAddress();

            return Formatter.formatIpAddress(ipAddress);
        }
        else if(mobile.isAvailable()) {
            try {
                for (Enumeration<NetworkInterface> en = NetworkInterface
                        .getNetworkInterfaces(); en.hasMoreElements();) {
                    NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf
                            .getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()) {
                            return inetAddress.getHostAddress().toString();
                        }
                    }
                }
            } catch (SocketException ex) {
                return null;
            }
        }
        else {
            return null;
        }

        return null;
    }
}
