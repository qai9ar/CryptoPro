package common;

import view.MainGUI;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.*;
import java.io.*;
import java.security.SecureRandom;
import java.util.Base64;

public class CryptoFactory {
    public MainGUI mainGUI;

    private String plainData;
    private String cipherData;
    private SecretKey secretKey;
    private SecretKeyFactory secretKeyFactory;

    public CryptoFactory(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        this.secretKey = generateSecretKey("AES", 256);
    }
    public void startEncryptProcess() {
        if (!inputCheck(1)) {
            JOptionPane.showMessageDialog(null, "[ NO PLAIN MESSAGE PROVIDED ]", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.plainData = this.mainGUI.getPlainData();
        byte[] plainDataBArray;
        byte[] cipherDataBArray;
        String bs64cipher = null;
        try {
            Cipher cipher = Cipher.getInstance(this.secretKey.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, this.secretKey);
            plainDataBArray = plainData.getBytes();
            cipherDataBArray = cipher.doFinal(plainDataBArray);
            bs64cipher = Base64.getEncoder().encodeToString(cipherDataBArray);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        this.mainGUI.setCipherData(bs64cipher);
    }
    public void startDecryptProcess() {
        if (!inputCheck(2)) {
            JOptionPane.showMessageDialog(null, "[ NO CIPHER MESSAGE PROVIDED ]", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.cipherData = this.mainGUI.getCipherData();
        byte[] plainDataBArray;
        byte[] cipherDataBArray;
        try {
            cipherDataBArray = Base64.getDecoder().decode(this.cipherData);
            Cipher cipher = Cipher.getInstance(this.secretKey.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, this.secretKey);
            plainDataBArray = cipher.doFinal(cipherDataBArray);
            this.plainData = new String(plainDataBArray);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(null, "[ INVALID KEY ]", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        this.mainGUI.setPlainData(this.plainData);
    }
    private SecretKey generateSecretKey(String algorithm, int keySize) {
        SecretKey secretKey = null;
        try {
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            SecureRandom sr = new SecureRandom();
            kg.init(keySize, sr);
            secretKey = kg.generateKey();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return secretKey;
    }
    public void retrieveSecretKey(String filename) {
        this.secretKeyFactory = null;
        if (new File("./" + filename + ".ser").exists()) {
            try {
                FileInputStream fis = new FileInputStream("./" + filename + ".ser");
                ObjectInputStream ois = new ObjectInputStream(fis);

                this.secretKeyFactory = (SecretKeyFactory) ois.readObject();
                ois.close();
                fis.close();
                JOptionPane.showMessageDialog(null, "[ SUCCESSFULLY USING OPENED KEY ]", "Info" ,JOptionPane.DEFAULT_OPTION);
                this.secretKey = this.secretKeyFactory.secretKey;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            JOptionPane.showMessageDialog(null, "[ SECRET KEY FILE NOT FOUND ]", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }
    public void saveSecretKey(String filename) {
        this.secretKeyFactory = new SecretKeyFactory();
        this.secretKeyFactory.secretKey = this.secretKey;
        if (new File("./" + filename + ".ser").exists()) {
            JOptionPane.showMessageDialog(null, "[ PROVIDED FILENAME IS USED ]", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream("./" + filename + ".ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(this.secretKeyFactory);
            oos.flush();
            oos.close();
            fos.close();
            JOptionPane.showMessageDialog(null, "[ SUCCESSFULLY SAVE KEY ] With The Filename: " + filename + ".ser", "Info", JOptionPane.DEFAULT_OPTION);
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    private boolean inputCheck(int mode) {
        // Modes [1 = Encryption, 2 = Decryption]
        boolean trigger = false;
        String plainData, cipherData;
        if (mode == 1) {
            plainData = this.mainGUI.getPlainData();
            if (plainData.equals(""))
                trigger = false;
            else
                trigger = true;
        }
        else if (mode == 2) {
            cipherData = this.mainGUI.getCipherData();
            if (cipherData.equals(""))
                trigger = false;
            else
                trigger = true;
        }
        return trigger;
    }
}
