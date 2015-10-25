/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple_irc_jgroups;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;
import simple_irc_jgroups.learning.SimpleChat;

/**
 *
 * @author adwisatya
 */
public class ReplSet<T> extends ReceiverAdapter{
    static JChannel channel;
    final HashSet<T> set = new HashSet<T>();
    private static final String username = System.getProperty("user.name", "n/a");;
    
    public boolean add(T obj){
        synchronized(set){
            return set.add(obj);
        }
    }
    public boolean contains(T obj){
        synchronized(set){
            return set.contains(obj);
        }
    }
    public boolean remove(T obj){
        synchronized(set){
            set.remove(obj);
        }
        return true;
    }
    private void start() throws Exception{
        channel=new JChannel();
        channel.setReceiver(this);
        channel.connect("ReplicatedSet");
        channel.setDiscardOwnMessages(true);
        channel.getState(null, 10000);
        
    }
    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }
    public void receive(Message msg) {
        String line= (String)msg.getObject();
        System.out.println("Received: " + msg.getSrc().toString() + ":"+line);
        String[] splitted =  line.split(" ");
        System.out.println(splitted[0].toLowerCase());
            if(splitted[0].toLowerCase().equals("/add")){
                System.out.println("1");
                set.add((T) splitted[1]);
            }else if(splitted[0].toLowerCase().equals("/remove")){
                System.out.println("2");
                set.remove((T) splitted[1]);
            }
    }
    public void getState(OutputStream output) throws Exception {
        synchronized(set) {
            Util.objectToStream(set, new DataOutputStream(output));
        }
    }
    @Override
    public void setState(InputStream input) throws Exception {
        HashSet<T> new_set=(HashSet<T>)Util.objectFromStream(new DataInputStream(input));
        synchronized(set) {
            set.clear();
            set.addAll(new_set);
        }
        System.out.println("received state (" + set.size() + " messages in chat history):");
        for(T str: set) {
            System.out.println(str);
        }
    }
    public static void main(String[] args) throws Exception{
        ReplSet<String> replset = new ReplSet<String>();
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));        
        Boolean result;
        replset.start();
        String input = null;
        String [] splitted;
        System.out.print("Type your message:");
        input = console.readLine();
        Message msg;
        while(!input.equalsIgnoreCase("/EXIT")){
            splitted =  input.split(" ");
            switch (splitted[0].toLowerCase()){
                case "/contains":
                    result = replset.contains(splitted[1]);
                    if(!result){
                        if(replset.set.size()== 0){
                            System.out.println("Set kosong");
                        }else{
                            System.out.println("Element tidak ditemukan");
                        }
                    }else{
                        System.out.println("Terdapat "+splitted[1]+" dalam set");
                    }
                    break;
                case "/remove":
                    msg = new Message(null, null, input);
                    replset.channel.send(msg);
                    
                    result = replset.contains(splitted[1]);
                    if(!result){
                        if(replset.set.size()== 0){
                            System.out.println("Set kosong");
                        }else{
                            System.out.println("Element tidak ditemukan");
                        }
                    }else{
                        if(replset.remove(splitted[1])){
                            System.out.println(splitted[1]+" sudah dihapus dari set");
                        }
                    }
                    break;
                case "/add":
                    msg = new Message(null, null, input);
                    replset.channel.send(msg);
                    if(replset.contains(splitted[1])){
                        System.out.println("Element sudah ada dalam set");
                    }else{
                        if(replset.add(splitted[1])){
                            System.out.println(splitted[1] + " have been pushed to set");
                        }
                    }
                    break;
                default:
                    break;
            }
            input = console.readLine();
        }
        replset.channel.close();
    }
}
