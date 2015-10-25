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
    final Set<T> set = new Set<T>() {

        @Override
        public int size() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean isEmpty() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean contains(Object o) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Iterator<T> iterator() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Object[] toArray() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public <T> T[] toArray(T[] a) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean add(T e) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public void clear() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };
    private static final String username = System.getProperty("user.name", "n/a");;
    
    public void add(T obj){
        synchronized(set){
            set.add(obj);
        }
    }
    public boolean contains(T obj){
        synchronized(set){
            set.contains(obj);
        }
        return true;
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
        channel.connect("ReplicatedStack");
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
            if(splitted[0].toLowerCase().equals("/push")){
                System.out.println("1");
                //synchronized(stack) {
                    set.add((T) line);
                //}
            }else if(splitted[0].toLowerCase().equals("/pop")){
                System.out.println("2");
                //synchronized(stack) {
                    set.remove((T) line);
                //}
            }
    }
    public void getState(OutputStream output) throws Exception {
        synchronized(set) {
            Util.objectToStream(set, new DataOutputStream(output));
        }
    }
    @Override
    public void setState(InputStream input) throws Exception {
        Stack<T> new_stack=(Stack<T>)Util.objectFromStream(new DataInputStream(input));
        synchronized(set) {
            set.clear();
            set.addAll(new_stack);
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
                case "/contain":
                    msg = new Message(null, null, input);
                    replset.channel.send(msg);
                    result = replset.contains(splitted[1]);
                    if(result == null){
                        System.out.println("Stack kosong");
                    }else{
                        //System.out.println("Top dari stack adalah "+replset.top());
                    }
                    break;
                case "/pop":
                    msg = new Message(null, null, input);
                    replset.channel.send(msg);
                    
                    result = replset.remove(splitted[1]);
                    if(result == null){
                        System.out.println("Stack kosong");
                    }else{
                        System.out.println("Top dari stack adalah "+result);
                    }
                    break;
                case "/push":
                    msg = new Message(null, null, input);
                    replset.channel.send(msg);
                    
                    replset.add(splitted[1]);
                    System.out.println(splitted[1] + "have been pushed to stack");
                    break;
                default:
                    break;
            }
            input = console.readLine();
        }
        replset.channel.close();
    }
}
