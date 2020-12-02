package com.hackathon.pCloudy.utils;

public class MiscUtils {

    /**
     * To get the divisor with which a number can be equally divided
     * between N devices
     *
     * @param totalRecords Total number of Target in <code>int</code>
     * @param totalDevices Total Number of Device in <code>int</code>
     * @return Divisor with which to divide
     */
    public static int getDivisor(int totalRecords, int totalDevices) {
        int remainder = totalRecords % totalDevices;
        if (remainder == 0) {
            return (totalRecords / totalDevices);
        } else {
            return (int) Math.floor(totalRecords / totalDevices);
        }
    }
}
