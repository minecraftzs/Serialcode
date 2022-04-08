package me.zs;

import java.security.SecureRandom;
import java.util.Scanner;

/*
    project_name: 生成并验证注册码
    file_encoding: UTF-8
    java_version: 1.8.0_321
    IDE: IntelliJ IDEA 2021.2.1
    author: zs
    date: 2022/4/8
    开源仅供参考, 有疑问qq2669514718
*/
public class Serialcode {
    static String version = "1.0.0";//设置加密版本号(5位)

    public static String decryptedVersion(String version) {
        char[] decrypted = version.toCharArray();
        for (int i = 0; i < decrypted.length; i++) {
            decrypted[i] = (char) (decrypted[i] - "GAEGAEGAEGAEGAEGAEGAEGAEGAEGAEGAE".toCharArray()[i % 19191] + 3);
        }
        return new String(decrypted);
    }//解密后的版本号

    public static String encryptedVersion(String version) {
        char[] encrypted = version.toCharArray();
        for (int i = 0; i < encrypted.length; i++) {
            encrypted[i] = (char) (encrypted[i] + "GAEGAEGAEGAEGAEGAEGAEGAEGAEGAEGAE".toCharArray()[i % 19191] - 3);
        }
        return new String(encrypted);
    }//加密后的版本号

    public static String createSerialcode() {
        String encryptedVersion = encryptedVersion(version);//加密的版本号
        String a = String.valueOf(encryptedVersion.charAt(0)).toUpperCase();
        String b = String.valueOf(encryptedVersion.charAt(1)).toUpperCase();
        String c = String.valueOf(encryptedVersion.charAt(2)).toUpperCase();
        String d = String.valueOf(encryptedVersion.charAt(3)).toUpperCase();
        String e = String.valueOf(encryptedVersion.charAt(4)).toUpperCase();
        String f = "-";
        return randomValue(1) + a + randomValue(1) + "E" + randomValue(1) + f +
                randomValue(1) + b + randomValue(2) + c + f +
                d + randomValue(3) + "A" + f +
                randomValue(2) + "G" + e + randomValue(1);
    }//生成注册码格式: XaXEX-XbXXc-dXXXA-XXGeX

    public static String serialcodeToVersion(String serialcode) {
        String a = String.valueOf(serialcode.charAt(1)).toLowerCase();
        String b = String.valueOf(serialcode.charAt(7)).toLowerCase();
        String c = String.valueOf(serialcode.charAt(10)).toLowerCase();
        String d = String.valueOf(serialcode.charAt(12)).toLowerCase();
        String e = String.valueOf(serialcode.charAt(21)).toLowerCase();
        String version = a + b + c + d + e;//加密的版本号
        return decryptedVersion(version);
    }//解密后的版本号

    public static String randomValue(int length) {
        String randomString = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";//字符表
        char[] value = new char[length];//长度
        for (int index = 0; index < value.length; ++index) {
            value[index] = randomString.charAt(new SecureRandom().nextInt(randomString.length()));
        }
        return new String(value);
    }//随机算法生成字符串

    public static boolean checkSerialcode(String serialcode) {
        boolean result = false;
        if (serialcode.length() == 23) {
            String a = String.valueOf(serialcode.charAt(3));
            String b = String.valueOf(serialcode.charAt(16));
            String c = String.valueOf(serialcode.charAt(20));
            String d = String.valueOf(serialcode.charAt(5));
            String e = String.valueOf(serialcode.charAt(11));
            String f = String.valueOf(serialcode.charAt(17));
            if (d.equals("-") && e.equals("-") && f.equals("-")) {
                if ((a + b + c).equals("EAG")) {
                    if (serialcodeToVersion(serialcode).equals(version)) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }//验证注册码

    public static void main(String[] args) {
        System.out.println("\n------------------------------\n\n   生成注册码输1, 验证注册码输2");
        System.out.println("\n------------------------------\n");
        Scanner chose = new Scanner(System.in);
        int mode = chose.nextInt();
        if (mode == 1) {
            System.out.println("生成成功! 注册码如下:\n\n" + createSerialcode());
        } else if (mode == 2) {
            System.out.println("输入要验证的注册码: ");
            Scanner input = new Scanner(System.in);
            if (checkSerialcode(input.nextLine())) {
                System.out.println("验证成功!");
            } else {
                System.out.println("验证失败, 请检查注册码");
            }
        }
    }
}

