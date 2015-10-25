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
import java.util.LinkedList;
import java.util.List;
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
    JChannel channel;
    final Stack<T> stack=new Stack<T>();
    String user_name = "aryya";
    
    public void push(T obj){
        
    }
    public T pop(){
        
        return null;
    }
    public T top(){
        
        return null;
    }
    private void start() throws Exception{
        channel=new JChannel();
        channel.setReceiver(this);
        channel.connect("ChatCluster");
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
    private void eventLoop() {
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
    }
    public void getState(OutputStream output) throws Exception {
        synchronized(stack) {
            Util.objectToStream(stack, new DataOutputStream(output));
        }
    }
    @SuppressWarnings("unchecked")
    public void setState(InputStream input) throws Exception {
        List<String> list=(List<String>)Util.objectFromStream(new DataInputStream(input));
        synchronized(stack) {
            stack.clear();
            //stack.addAll();
        }
        System.out.println("received state (" + list.size() + " messages in chat history):");
        for(String str: list) {
            System.out.println(str);
        }
    }
    public static void main(String[] args) throws Exception{
        new ReplSet().start();
    }
}
