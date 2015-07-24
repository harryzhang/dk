package com.hehenian.mq.zeromq;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Poller;
import org.zeromq.ZMQ.Socket;

/**
* Simple request-reply broker
*
*/
public class JBrokerZeroMQ{

    public static void main (String[] args) {
        //  Prepare our context and sockets
        Context context = ZMQ.context(1);

        Socket frontend = context.socket(ZMQ.ROUTER);
        Socket backend  = context.socket(ZMQ.DEALER);
        frontend.bind("tcp://*:5559");
        backend.bind("tcp://*:5560");

        System.out.println("launch and connect broker.");

        //  Initialize poll set
        Poller items = new Poller (2);
        items.register(frontend, Poller.POLLIN);
        items.register(backend, Poller.POLLIN);

        boolean more = false;
        byte[] message;

        //  Switch messages between sockets
        while (!Thread.currentThread().isInterrupted()) {            
            //  poll and memorize multipart detection
            items.poll();

            if (items.pollin(0)) {
                while (true) {
                    // receive message多帧信息
                    message = frontend.recv(0);
                    more = frontend.hasReceiveMore();

                    // Broker it
                    backend.send(message, more ? ZMQ.SNDMORE : 0);
                    
                    String str_msg="";
                    if (message != null) {
                        str_msg= new String(message, Charset.forName("UTF-8"));
                    }
                    //打印多帧消息（详见下一篇msg identity），可以看出来第一帧是ID，第二帧是分隔符，第三帧才是数据
                    System.out.println(GetCurrtime()+": routed a Req. msg["+str_msg+"] more ["+more+"]");
                    if(!more){
                        break;
                    }
                }
            }
            if (items.pollin(1)) {
                while (true) {
                    // receive message多帧
                    message = backend.recv(0);
                    more = backend.hasReceiveMore();
                    // Broker it
                    frontend.send(message,  more ? ZMQ.SNDMORE : 0);
                    System.out.println(GetCurrtime()+": got a Rep.");
                    if(!more){
                        break;
                    }
                }
            }
        }
        //  We never get here but clean up anyhow
        frontend.close();
        backend.close();
        context.term();
    }
    
    public static String GetCurrtime(){
    	Calendar cal = Calendar.getInstance();
    	cal.getTime();
    	SimpleDateFormat sdf = new SimpleDateFormat("mm:ss.SS");
    	return sdf.format(cal.getTime());
    }
}

