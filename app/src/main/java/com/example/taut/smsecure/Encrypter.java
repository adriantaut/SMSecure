package com.example.taut.smsecure;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
//import javax.xml.bind.DatatypeConverter;




public class Encrypter {
    static String initVector = "RandomInitVector"; // 16 bytes IV
    static String key ; // 128 bit key

    public static String encrypt(String encryptkey, /*String initVector,*/ String value) {
        try {
            key = String.format("%-16s", encryptkey).replace(' ', '*');
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");


            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(value.getBytes());
            String ret = android.util.Base64.encodeToString(encrypted,0);
            return ret;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public static String decrypt(String decryptkey, /*String initVector,*/ String encrypted) {
        try {
            key = String.format("%-16s", decryptkey).replace(' ', '*');
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] original = cipher.doFinal(android.util.Base64.decode(encrypted,0));
            //byte[] original = cipher.doFinal(android.util.Base64.decode(encrypted,0));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

   /* public static void main(String[] args) {
        //String key = "Bar12345Bar12345"; // 128 bit key
        //String initVector = "RandomInitVector"; // 16 bytes IV
        //System.out.println(encrypt(key, initVector, "Hello World"));
        /*System.out.println(decrypt(key, initVector,
                encrypt(key, initVector, "Hello World Hello World Hello World")));

        System.out.println(decrypt(encrypt("Hello World")));
    }*/
}
