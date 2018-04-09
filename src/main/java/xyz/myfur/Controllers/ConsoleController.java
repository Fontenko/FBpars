package xyz.myfur.Controllers;

import xyz.myfur.API;
import xyz.myfur.Core;
import xyz.myfur.Util.Settings;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class ConsoleController extends Thread implements Runnable {
    public static PrintStream ps=null;
    public ConsoleController() {
        setDaemon(false);
        start();
    }

    @Override
    public void run() {
        ps.println("Старт сохранялки");
        Timer t = new Timer("timer",false);
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                System.gc();
                try {
                    Core.save();
                } catch (IOException e) {
                    System.out.println("Bad save!");
                }
            }
        },5000,60*5*1000);
        ps.println("Старт консоли!");
        while (true){
            try {
                Scanner sc =new Scanner(System.in);
                String command = sc.next();
                if (command.trim().equals("stop")){
                    Core.stop();
                }else if(command.equals("where")){
                    ps.println(API.where());
                }else if (command.equals("tasks")){
                    ps.println(Settings.pcon.tasks.size());
                    ps.println(Settings.lcon.tasks.size());
                }else ps.println("Bad command!");
            } catch (IOException e) {
                ps.println("Err");
            }
        }
    }
}
