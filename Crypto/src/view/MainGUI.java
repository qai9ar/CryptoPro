package view;

import common.CryptoFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI extends JFrame implements ActionListener {

    public CryptoFactory cryptofactory;

    private JMenuBar menu_bar;
    private JMenu key_menu;
    private JMenuItem open_key, save_key;
    private JPanel encrypt_panel, decrypt_panel, encrypt_panel_btn, decrypt_panel_btn;
    private JLabel label_encrypted, label_decrypted, label_toEncrypt, label_toDecrypt;
    private JTextField field_encrypted, field_decrypted, field_toEncrypt, field_toDecrypt;
    private JButton btn_encrypt, btn_decrypt;

    public MainGUI() {
        super("CryptoPro");
        this.cryptofactory = new CryptoFactory(this);
        createAndShowGUI();
    }
    private void createAndShowGUI() {

        menu_bar = new JMenuBar();
        key_menu = new JMenu("Keys");
        open_key = new JMenuItem("Open Saved Key");
        save_key = new JMenuItem("Save Current Key");
        key_menu.add(open_key);
        key_menu.add(save_key);
        menu_bar.add(key_menu);

        encrypt_panel = new JPanel();
        decrypt_panel = new JPanel();
        encrypt_panel_btn = new JPanel();
        decrypt_panel_btn = new JPanel();

        label_encrypted = new JLabel("Encrypted:");
        label_decrypted = new JLabel("Decrypted:");
        label_toEncrypt = new JLabel("Plain data:");
        label_toDecrypt = new JLabel("Cipher data:");

        field_encrypted = new JTextField(20);
        field_decrypted = new JTextField(20);
        field_toEncrypt = new JTextField(20);
        field_toDecrypt = new JTextField(20);
        field_encrypted.setEditable(false);
        field_encrypted.setBorder(null);
        field_decrypted.setEditable(false);
        field_decrypted.setBorder(null);

        btn_encrypt = new JButton("Encrypt");
        btn_decrypt = new JButton("Decrypt");

        setupMainLayout();
        setupEncryptLayoutWithBtn();
        setupDecryptLayoutWithBtn();

        this.btn_encrypt.addActionListener(this);
        this.btn_decrypt.addActionListener(this);
        this.open_key.addActionListener(this);
        this.save_key.addActionListener(this);

        this.setJMenuBar(menu_bar);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(3);
        this.setVisible(true);
    }
    private void setupMainLayout() {
        Container container = this.getContentPane();
        GroupLayout GL = new GroupLayout(container);
        container.setLayout(GL);
        GL.setAutoCreateContainerGaps(true);
        GL.setAutoCreateGaps(true);

        GL.setHorizontalGroup(
                GL.createParallelGroup().addComponent(encrypt_panel_btn).addComponent(decrypt_panel_btn)
        );
        GL.setVerticalGroup(
                GL.createSequentialGroup().addComponent(encrypt_panel_btn).addComponent(decrypt_panel_btn)
        );
        GL.linkSize(SwingConstants.HORIZONTAL, encrypt_panel_btn, decrypt_panel_btn);
    }
    private void setupEncryptLayout() {
        GroupLayout GL = new GroupLayout(this.encrypt_panel);
        this.encrypt_panel.setLayout(GL);
        GL.setAutoCreateGaps(true);
        GL.setAutoCreateContainerGaps(true);

        GL.setHorizontalGroup(
                GL.createSequentialGroup()
                        .addGroup(
                            GL.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(label_toEncrypt).addComponent(label_encrypted)
                        )
                        .addGroup(
                            GL.createParallelGroup().addComponent(field_toEncrypt).addComponent(field_encrypted)
                        )
        );
        GL.setVerticalGroup(
                GL.createSequentialGroup()
                    .addGroup(
                            GL.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(label_toEncrypt).addComponent(field_toEncrypt)
                    )
                    .addGroup(
                            GL.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(label_encrypted).addComponent(field_encrypted)
                    )
        );

    }
    private void setupEncryptLayoutWithBtn() {
        GroupLayout GL = new GroupLayout(this.encrypt_panel_btn);
        this.encrypt_panel_btn.setLayout(GL);
        this.encrypt_panel_btn.setBorder(BorderFactory.createTitledBorder("Encryption"));
        setupEncryptLayout();
        GL.setAutoCreateContainerGaps(true);
        GL.setHorizontalGroup(
                GL.createParallelGroup().addComponent(encrypt_panel).addComponent(btn_encrypt)
        );
        GL.setVerticalGroup(
                GL.createSequentialGroup().addComponent(encrypt_panel).addComponent(btn_encrypt)
        );
        GL.linkSize(SwingConstants.HORIZONTAL, btn_encrypt, encrypt_panel);
    }
    private void setupDecryptLayout() {
        GroupLayout GL = new GroupLayout(this.decrypt_panel);
        this.decrypt_panel.setLayout(GL);
        GL.setAutoCreateGaps(true);
        GL.setAutoCreateContainerGaps(true);

        GL.setHorizontalGroup(
                GL.createSequentialGroup()
                        .addGroup(
                                GL.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(label_toDecrypt).addComponent(label_decrypted)
                        )
                        .addGroup(
                                GL.createParallelGroup().addComponent(field_toDecrypt).addComponent(field_decrypted)
                        )
        );
        GL.setVerticalGroup(
                GL.createSequentialGroup()
                        .addGroup(
                                GL.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(label_toDecrypt).addComponent(field_toDecrypt)
                        )
                        .addGroup(
                                GL.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(label_decrypted).addComponent(field_decrypted)
                        )
        );

    }
    private void setupDecryptLayoutWithBtn() {
        GroupLayout GL = new GroupLayout(this.decrypt_panel_btn);
        this.decrypt_panel_btn.setLayout(GL);
        this.decrypt_panel_btn.setBorder(BorderFactory.createTitledBorder("Decryption"));
        setupDecryptLayout();
        GL.setAutoCreateContainerGaps(true);
        GL.setHorizontalGroup(
                GL.createParallelGroup().addComponent(decrypt_panel).addComponent(btn_decrypt)
        );
        GL.setVerticalGroup(
                GL.createSequentialGroup().addComponent(decrypt_panel).addComponent(btn_decrypt)
        );
        GL.linkSize(SwingConstants.HORIZONTAL, btn_decrypt, decrypt_panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btn_encrypt) {
            this.cryptofactory.startEncryptProcess();
        }
        else if (e.getSource() == btn_decrypt) {
            this.cryptofactory.startDecryptProcess();
        }
        else if (e.getSource() == open_key) {
            String value = JOptionPane.showInputDialog(null, "Input Filename");
            cryptofactory.retrieveSecretKey(value);
        }
        else if (e.getSource() == save_key) {
            String value = JOptionPane.showInputDialog(null, "Input Filename");
            cryptofactory.saveSecretKey(value);
        }
    }
    public String getPlainData() {
        return this.field_toEncrypt.getText();
    }
    public String getCipherData() {
        return this.field_toDecrypt.getText();
    }
    public void setCipherData(String cipherData) {
        this.field_encrypted.setText(cipherData);
    }
    public void setPlainData(String plainData) {
        this.field_decrypted.setText(plainData);
    }
}
