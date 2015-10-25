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
    JChannel channel;
    final Stack<T> stack=new Stack<T>();
    String user_name = "aryya";
    
    public void push(T obj){
        synchronized(stack){
            stack.push((T)obj);
        }
    }
    public T pop(){
        synchronized(stack){
            return stack.pop();
        }
    }
    public T top(){
        synchronized(stack){
            return stack.lastElement();
        }
        
    }
    private void start() throws Exception{
        channel=new JChannel();
        channel.setReceiver(this);
        channel.connect("ReplicatedStack");
        eventLoop();
        channel.getState(null, 10000);
        channel.close();
    }
    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }
    public void receive(Message msg) {
        String line=msg.getSrc() + ": " + msg.getObject();
        System.out.println(line);
        synchronized(stack) {
            stack.push((T) line);
        }
        
    }
    private void eventLoop() throws IOException {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                System.out.print("> "); System.out.flush();
                String line=in.readLine().toLowerCase();
                if(line.startsWith("quit") || line.startsWith("exit"))
                    break;
                line="[" + user_name + "] " + line;
                Message msg=new Message(null, null, line);
                channel.send(msg);
            }
            catch(Exception e) {
            }
        }
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));        
        String input = null;
        String [] splitted;
        String result = null;
        
        System.out.print("Type your message:");
        input = console.readLine();
        while(!input.equalsIgnoreCase("/EXIT")){
            splitted =  input.split(" ");
            switch (splitted[0].toLowerCase()){
                case "/nick":
                    break;
                case "/join":
                    break;
                case "/leave":
                default:
                    break;
            }
            input = console.readLine();
        }  
    }
    public void getState(OutputStream output) throws Exception {
        synchronized(stack) {
            Util.objectToStream(stack, new DataOutputStream(output));
        }
    }
    @SuppressWarnings("unchecked")
    public void setState(InputStream input) throws Exception {
        Stack<T> stack=(Stack<T>)Util.objectFromStream(new DataInputStream(input));
        synchronized(stack) {
            stack.clear();
            stack.addAll(stack);
        }
        System.out.println("received state (" + stack.size() + " messages in chat history):");
        for(T str: stack) {
            System.out.println(str);
        }
    }
    public static void main(String[] args) throws Exception{
        new ReplStack().start();
    }
}
