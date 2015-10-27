/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simple_irc_jgroups;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

/**
 *
 * @author adwisatya
 */
public class ReplStack<T> extends ReceiverAdapter{
    static JChannel channel;
    final Stack<T> stack=new Stack<T>();
    private static final String username = System.getProperty("user.name", "n/a");;
    
    public void push(T obj){
        synchronized(stack){
            stack.push((T)obj);
        }
    }
    public T pop(){
        synchronized(stack){
            if(stack.empty()){
                return null;
            }else{
                return stack.pop();
            }
        }
    }
    public T top(){
        synchronized(stack){
            if(stack.empty()){
                return null;
            }else{
                return stack.peek();
            }
        }
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
                    stack.push((T) splitted[1]);
                //}
            }else if(splitted[0].toLowerCase().equals("/pop")){
                System.out.println("2");
                //synchronized(stack) {
                    if(stack.empty()){
                        System.out.println("Stack kosong");
                    }else{
                        stack.pop();
                    }

                //}
            }
    }
    public void getState(OutputStream output) throws Exception {
        synchronized(stack) {
            Util.objectToStream(stack, new DataOutputStream(output));
        }
    }
    @Override
    public void setState(InputStream input) throws Exception {
        Stack<T> new_stack=(Stack<T>)Util.objectFromStream(new DataInputStream(input));
        synchronized(stack) {
            stack.clear();
            stack.addAll(new_stack);
        }
        System.out.println("received state (" + stack.size() + " messages in chat history):");
        for(T str: stack) {
            System.out.println(str);
        }
    }
    public static void main(String[] args) throws Exception{
        ReplStack<String> replstack = new ReplStack<String>();
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));        
        String result;
        replstack.start();
        String input = null;
        String [] splitted;
        System.out.print("Type your message:");
        input = console.readLine();
        Message msg;
        while(!input.equalsIgnoreCase("/EXIT")){
            splitted =  input.split(" ");
            switch (splitted[0].toLowerCase()){
                case "/top":
                    msg = new Message(null, null, input);
                    replstack.channel.send(msg);
                    result = replstack.top();
                    if(result == null){
                        System.out.println("Stack kosong");
                    }else{
                        System.out.println("Top dari stack adalah "+replstack.top());
                    }
                    break;
                case "/pop":
                    msg = new Message(null, null, input);
                    replstack.channel.send(msg);
                    
                    result = replstack.pop();
                    if(result == null){
                        System.out.println("Stack kosong");
                    }else{
                        System.out.println("Hasil pop adalah "+result);
                    }
                    break;
                case "/push":
                    msg = new Message(null, null, input);
                    replstack.channel.send(msg);
                    
                    replstack.push(splitted[1]);
                    System.out.println(splitted[1] + " have been pushed to stack");
                    break;
                default:
                    break;
            }
            input = console.readLine();
        }
        replstack.channel.close();
    }
}
