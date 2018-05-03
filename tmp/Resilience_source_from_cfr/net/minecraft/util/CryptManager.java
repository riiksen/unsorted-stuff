/*
 * Decompiled with CFR 0_123.
 */
package net.minecraft.util;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class CryptManager {
    private static final String __OBFID = "CL_00001483";

    public static SecretKey createNewSharedKey() {
        try {
            KeyGenerator var0 = KeyGenerator.getInstance("AES");
            var0.init(128);
            return var0.generateKey();
        }
        catch (NoSuchAlgorithmException var1) {
            throw new Error(var1);
        }
    }

    public static KeyPair createNewKeyPair() {
        try {
            KeyPairGenerator var0 = KeyPairGenerator.getInstance("RSA");
            var0.initialize(1024);
            return var0.generateKeyPair();
        }
        catch (NoSuchAlgorithmException var1) {
            var1.printStackTrace();
            System.err.println("Key pair generation failed!");
            return null;
        }
    }

    public static byte[] getServerIdHash(String par0Str, PublicKey par1PublicKey, SecretKey par2SecretKey) {
        try {
            return CryptManager.digestOperation("SHA-1", par0Str.getBytes("ISO_8859_1"), par2SecretKey.getEncoded(), par1PublicKey.getEncoded());
        }
        catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
            return null;
        }
    }

    private static /* varargs */ byte[] digestOperation(String par0Str, byte[] ... par1ArrayOfByte) {
        try {
            MessageDigest var2 = MessageDigest.getInstance(par0Str);
            byte[][] var3 = par1ArrayOfByte;
            int var4 = par1ArrayOfByte.length;
            int var5 = 0;
            while (var5 < var4) {
                byte[] var6 = var3[var5];
                var2.update(var6);
                ++var5;
            }
            return var2.digest();
        }
        catch (NoSuchAlgorithmException var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public static PublicKey decodePublicKey(byte[] par0ArrayOfByte) {
        try {
            X509EncodedKeySpec var3 = new X509EncodedKeySpec(par0ArrayOfByte);
            KeyFactory var2 = KeyFactory.getInstance("RSA");
            return var2.generatePublic(var3);
        }
        catch (NoSuchAlgorithmException var3) {
        }
        catch (InvalidKeySpecException var3) {
            // empty catch block
        }
        System.err.println("Public key reconstitute failed!");
        return null;
    }

    public static SecretKey decryptSharedKey(PrivateKey par0PrivateKey, byte[] par1ArrayOfByte) {
        return new SecretKeySpec(CryptManager.decryptData(par0PrivateKey, par1ArrayOfByte), "AES");
    }

    public static byte[] encryptData(Key par0Key, byte[] par1ArrayOfByte) {
        return CryptManager.cipherOperation(1, par0Key, par1ArrayOfByte);
    }

    public static byte[] decryptData(Key par0Key, byte[] par1ArrayOfByte) {
        return CryptManager.cipherOperation(2, par0Key, par1ArrayOfByte);
    }

    private static byte[] cipherOperation(int par0, Key par1Key, byte[] par2ArrayOfByte) {
        try {
            return CryptManager.createTheCipherInstance(par0, par1Key.getAlgorithm(), par1Key).doFinal(par2ArrayOfByte);
        }
        catch (IllegalBlockSizeException var4) {
            var4.printStackTrace();
        }
        catch (BadPaddingException var5) {
            var5.printStackTrace();
        }
        System.err.println("Cipher data failed!");
        return null;
    }

    private static Cipher createTheCipherInstance(int par0, String par1Str, Key par2Key) {
        try {
            Cipher var3 = Cipher.getInstance(par1Str);
            var3.init(par0, par2Key);
            return var3;
        }
        catch (InvalidKeyException var4) {
            var4.printStackTrace();
        }
        catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
        }
        catch (NoSuchPaddingException var6) {
            var6.printStackTrace();
        }
        System.err.println("Cipher creation failed!");
        return null;
    }

    public static Cipher func_151229_a(int p_151229_0_, Key p_151229_1_) {
        try {
            Cipher var2 = Cipher.getInstance("AES/CFB8/NoPadding");
            var2.init(p_151229_0_, p_151229_1_, new IvParameterSpec(p_151229_1_.getEncoded()));
            return var2;
        }
        catch (GeneralSecurityException var3) {
            throw new RuntimeException(var3);
        }
    }
}

