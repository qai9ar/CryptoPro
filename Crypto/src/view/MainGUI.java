package view;

import common.CryptoFactory;

import javax.swing.*;

public class MainGUI extends JFrame {

    private CryptoFactory cryptofactory;

    private JLabel label_encrypted, label_decrypted;
    private JTextField field_encrypted, field_decrypted, field_toEncrypt, field_toDecrypt;
    private JButton btn_encrypt, btn_decrypt;

    public MainGUI() {
        this.cryptofactory = new CryptoFactory(this);

    }
    private void createAndShowGUI() {

    }
}
