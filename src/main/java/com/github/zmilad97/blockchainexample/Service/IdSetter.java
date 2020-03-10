package com.github.zmilad97.blockchainexample.Service;


import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;

public class IdSetter {
    String idHash;
    Cryptography cryptography = new Cryptography();


    public String idHash() {

        try {
            String idString = macAddress();
            idHash = cryptography.toHexString(cryptography.getSha(idString));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return idHash;
    }

    public String macAddress() {
        StringBuilder sb = new StringBuilder();
        try {
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            NetworkInterface inter;
            while (networks.hasMoreElements()) {
                inter = networks.nextElement();
                byte[] mac = inter.getHardwareAddress();
                if (mac != null) {
                    for (int i = 0; i < mac.length; i++) {
                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "" : ""));
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


}