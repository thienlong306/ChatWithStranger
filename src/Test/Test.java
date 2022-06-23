package Test;

import Enity.User;

import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

import static Server.Server.listUser;
import static Server.Server.listWait;

public class Test {
    public void getIndex(){
        ArrayList<User> list = new ArrayList<>();
        int i= list.indexOf(new Object(){
            public boolean equals(Object obj){
                return ((User)obj).getUsername().equals("ngan");
            }
        });
    }
    public static void main(String[] args) {
//        ArrayList<User> list = new ArrayList<>();
//        User u = new User(null,"long",null,null);
//        list.add(u);
//         u = new User(null,"thai",null,null);
//        list.add(u);
//        User u1 = new User(null,"ngan",null,null);
//        User u2= new User(null,"ngan",null,null);
//        list.add(u1);
//
//        System.out.println(list.indexOf(u1));
        TestCount tc=new TestCount();
        tc.stop();
    }
}
class TestCount extends Thread{
    private User u1;
    public TestCount(){
        start();
    }
    public void run(){
        int i=0;
        while (true){
            System.out.println(i++);
        }
    }
}