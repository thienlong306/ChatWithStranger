package Client;

import Enity.User;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static Client.GUI.statusWait;

public class ListenClient extends Thread {
    private Socket cl = null;
    private String username=null;
    private ObjectOutputStream oos=null;
    private ObjectInputStream ois=null;
    private JTextPane jTextPane;
    private JTextField content;
    private JButton send;
    private JTextField usernameField;
    private JButton connectButton;  
    public ListenClient(User u) {
        this.cl = u.getClient();
        this.username=u.getUsername();
        this.ois=u.getOis();
        this.oos=u.getOos();
        start();
    }
    public void setListen(JTextField content,JButton send,JTextPane jTextPane,JTextField usernameField,JButton connectButton){
        this.send=send;
        this.content=content;
        this.jTextPane=jTextPane;
        this.connectButton=connectButton;
        this.usernameField=usernameField;
    }
    public void run() {
       try {
           while (true) {
               Thread.sleep(1);
               Object o=ois.readObject();
               if (o.equals("aa8af3ebe14831a7cd1b6d1383a03755")){
                   //Chấp nhận không?
                   AcceptChat(ois.readObject());
               }else if (o.equals("893e101a9e5e6486c713830c915383d8")){
                   //Kết nối thành công
                   o=ois.readObject();
                   String text=o+"\n\n";
                   StyledDocument doc=jTextPane.getStyledDocument();
                   jTextPane.setText("");
                   SimpleAttributeSet right = new SimpleAttributeSet();
                   StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
                   StyleConstants.setForeground(right, Color.GREEN);
                   doc.insertString(doc.getLength(), text,right);
                   send.setEnabled(true);
                   content.setEnabled(true);
                   statusWait=false;
               }else if (o.equals("7f1e575cb5799331f6c95425b7bcd76d")){
                   // Chờ người khác
                   o=ois.readObject();
                   String text=o+"\n";
                   StyledDocument doc=jTextPane.getStyledDocument();
                   SimpleAttributeSet right = new SimpleAttributeSet();
                   StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
                   StyleConstants.setForeground(right, Color.RED);
                   doc.insertString(doc.getLength(), text,right);
                   send.setEnabled(false);
                   content.setEnabled(false);
                   statusWait=true;
               } else if (((String)o).contains("724a9f13c8569552344c0648b74e784f")){
                   //Friend mất kết nối
                   StyledDocument doc=jTextPane.getStyledDocument();
                   JOptionPane.showMessageDialog(null,ois.readObject());
                   jTextPane.setText("");
                   send.setEnabled(false);
                   content.setEnabled(false);
                   usernameField.setEnabled(true);
                   connectButton.setEnabled(true);
               }else if (o.equals("e613b5f12127b1b4d593835a562ef330")){
                   System.out.println("Found you");
               }
               else {
                   String text = o + "\n";
                   StyledDocument doc = jTextPane.getStyledDocument();
                   SimpleAttributeSet right = new SimpleAttributeSet();
                   StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
                   StyleConstants.setForeground(right, Color.RED);
                   doc.insertString(doc.getLength(), text, right);
               }
           }
       } catch (IOException e) {
//           e.printStackTrace();
           JOptionPane.showMessageDialog(null,"Mất kết nối máy chủ");
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       } catch (InterruptedException e) {
           e.printStackTrace();
       } catch (BadLocationException e) {
           e.printStackTrace();
       }
    }
    private void AcceptChat(Object o){
        try {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int dialogResult = JOptionPane.showConfirmDialog(null, o, "Warning", dialogButton);
            if(dialogResult == 0) {
                oos.writeObject("y");
            } else {
                oos.writeObject("n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
