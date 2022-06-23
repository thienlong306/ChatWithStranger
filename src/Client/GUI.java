package Client;

import Enity.User;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Scanner;

import static Client.Client.*;

public class GUI extends JFrame {
    private JTextField usernameField;
    private JButton connectButton;
    private JTextPane textPane1;
    private JTextField contentField;
    private JButton sendButton;
    private JPanel chatPannel;
    public static boolean statusWait=false;
    private Object o;

    public GUI() {

        super("Chat với người lạ");
        addWindowListener(
                new WindowAdapter()
                {
                    public void windowClosing(WindowEvent e)
                    {
                        //SHUT DOWN CLIENT CORRECTLY
                        System.exit(0);
                    }
                }
        );
        add(chatPannel);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        final int HEIGHT = 700;
        final int WIDTH = 600;
        setBounds(((screenSize.width / 2) - (WIDTH / 2)),
                ((screenSize.height / 2) - (HEIGHT / 2)), WIDTH, HEIGHT);
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckLogin();
            }
        });
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (contentField.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "Nội dung không được để trống");
                    } else {
                        StyledDocument doc = textPane1.getStyledDocument();
                        String text = "Bạn: " + contentField.getText() + "\n";
                        SimpleAttributeSet right = new SimpleAttributeSet();
                        StyleConstants.setForeground(right, Color.BLUE);
                        doc.insertString(doc.getLength(), text, right);
                        oos.writeObject(" "+contentField.getText());
                        contentField.setText("");
                    }
                } catch (IOException | BadLocationException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

    }
    public void CheckLogin() {
        try {
            if (usernameField.getText().equals("")){
                JOptionPane.showMessageDialog(null,"Không được để trống");
            }else {
                Connect();
                o = ois.readObject();
                System.out.println(o);
                username = usernameField.getText();
                oos.writeObject(username);
                o = ois.readObject();
                if (o.equals("ok")) {
                    JOptionPane.showMessageDialog(null, "Đăng nhập thành công");
                    usernameField.setEnabled(false);
                    connectButton.setEnabled(false);
                    o = ois.readObject();
                    String text;
                    if (o.equals("7f1e575cb5799331f6c95425b7bcd76d")){
                        text = "";
                    }else{
                        text = o + "\n";
                    }
                    StyledDocument doc = textPane1.getStyledDocument();
                    SimpleAttributeSet right = new SimpleAttributeSet();
                    StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
                    StyleConstants.setForeground(right, Color.RED);
                    doc.insertString(doc.getLength(), text, right);
                    u = new User(cl, username, ois, oos);
                    ListenClient lc = new ListenClient(u);
                    lc.setListen(contentField, sendButton, textPane1, usernameField, connectButton);
                } else {
                    JOptionPane.showMessageDialog(null, "Đã có người sử dụng");
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
            JOptionPane.showMessageDialog(null,"Mất kết nối máy chủ");
        }
    }
}
