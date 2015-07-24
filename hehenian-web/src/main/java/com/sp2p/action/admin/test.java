package com.sp2p.action.admin;

import java.sql.Connection;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class test
{
    
    Calendar ca = null;
    
    Calendar ca1 = null;
    
    Timer timer = new Timer();
    
    static Connection con = null;
    
    public void chu()
    {
        ca = Calendar.getInstance();
        ca1 = (Calendar)ca.clone();
        ca1.add(Calendar.SECOND, 20);
    }
    
    public void Time()
    {
        float s =
            ca1.get(Calendar.SECOND) - ca.get(Calendar.SECOND)
                + (ca1.get(Calendar.MILLISECOND) - ca.get(Calendar.MILLISECOND)) / 1000f;
        if (con == null)
        {
            System.out.println("耗时" + String.valueOf(s) + "秒");
            if (ca.after(ca1))
            {
                System.out.println("取不到连接");
                timer.cancel();
                return;
            }
        }
        else
        {
            System.out.println("耗时" + String.valueOf(s) + "秒");
        }
        ca.add(Calendar.MILLISECOND, 10);
        timer.schedule(new TimerTask()
        {
            public void run()
            {
                System.out.println("时间在溜走。。。。");
                Time();
            }
        }, ca.getTime());
    }
    
    void getcon()
    {
        Connection con = null;//获取Connection 
        chu();
        Time();
    }
}
