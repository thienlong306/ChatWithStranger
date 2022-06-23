package Server;

import Enity.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collections;
import java.util.Random;

import static Server.Server.listUser;
import static Server.Server.listWait;

public class HandlerClient extends Thread {
    private Socket cl = null;
    private ObjectInputStream ois=null;
    private ObjectOutputStream oos=null;
    private User u1,u2;
    private Object o;
    private String username=null;
    public HandlerClient(Socket cl) {
        this.cl = cl;
        start();
    }

    public void run() {
        boolean status = true;
        String text="Kết nối thành công";
        String tmp="ok";
        try {
            ois = new ObjectInputStream(cl.getInputStream());
            oos = new ObjectOutputStream(cl.getOutputStream());
            oos.writeObject(text);
            while (status){
                o =ois.readObject();
                username=(String)o;
                if (listUser.size()!=0){
                    for (int i=0;i<listUser.size();i++){
                        try {
                            listWait.get(i).getOos().writeObject("e613b5f12127b1b4d593835a562ef330");
                        } catch (Exception e) {
                            listUser.remove(listWait.get(i).getUsername());
                            listWait.remove(i);
                            continue;
                        }
                        if (username.equals(listUser.get(i))){
                            tmp="not ok";
                            break;
                        }
                    }
                    if (tmp.equals("ok")){
                        break;
                    }else{
                        text="Đã có người sử dụng tên này";
                        oos.writeObject(text);
                        tmp="ok";
                    }
                }else {
                    break;
                }
            }
            System.out.println("Them thanh cong: "+username);
            listUser.add(username);
            oos.writeObject(tmp);
            findFriend();
        } catch (IOException | ClassNotFoundException e) {
//            e.printStackTrace();
        }
    }
    private void findFriend(){
        try {
            String find="finding";
            if(listWait.size()!=0){
                if (listWait.size()!=1){
                    Collections.shuffle(listWait);
                }
                for(int i=0;i<listWait.size();i++){
                        try {
                            listWait.get(i).getOos().writeObject("e613b5f12127b1b4d593835a562ef330");
                        } catch (Exception e) {
                            listUser.remove(listWait.get(i).getUsername());
                            listWait.remove(i);
                            continue;
                        }
                        oos.writeObject("Tìm người...");
                        oos.writeObject("aa8af3ebe14831a7cd1b6d1383a03755");
                        oos.writeObject("Bạn muốn chat với: "+listWait.get(i).getUsername()+" không?");
                        o=ois.readObject();
                        if (o.equals("y")) {
                            u2 = listWait.get(i);
                            listWait.remove(i);
                            u1 = new User(cl, username, ois, oos);
                            new RoomServer(u2, u1);
                                new RoomServer(u1, u2);
                            find="fined";
                        }
                }
                if (find.equals("finding")){
                    oos.writeObject("7f1e575cb5799331f6c95425b7bcd76d");
                    oos.writeObject("Chờ người khác");
                    u1 = new User(cl, username, ois, oos);
                    listWait.add(u1);
                }
            }else {
                u1 = new User(cl,username,ois,oos);
                listWait.add(u1);
                oos.writeObject("Chờ người khác");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
