package view;

import javax.swing.*;

public class Entry {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        new MainGUI();
    }
}
