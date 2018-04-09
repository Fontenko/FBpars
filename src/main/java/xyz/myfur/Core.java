package xyz.myfur;

import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import xyz.myfur.Controllers.ConsoleController;
import xyz.myfur.Controllers.LoadersController;
import xyz.myfur.Controllers.ParsersController;
import xyz.myfur.Data.Response;
import xyz.myfur.Data.Save;
import xyz.myfur.Data.Task;
import xyz.myfur.Util.Settings;

import static xyz.myfur.Util.Settings.lcon;
import static xyz.myfur.Util.Settings.pcon;

import java.io.*;
import java.util.*;

import static xyz.myfur.Util.Logger.*;
public class Core {
    public static void main(String... a)  {
        ConsoleController.ps=System.out;
        outIcon();
        Settings.activate();

        System.out.println("Введите индекс символ для парсинга");
        System.out.println("Если есть сохранине мы проигнорим ответ )");
        lcon.chind = new Scanner(System.in).nextInt();


        setloggers();
        pcon = new ParsersController(3);
        pcon.start();
        lcon = new LoadersController(5);
        Settings.readdata();
        lcon.start();

        new ConsoleController();


    }

    public static void post(@NotNull Response res){
        if (res.res== Response.Responces.OK){
            if (res.doc!=null){
                pcon.getTasks().add(new Task(res.link,res.doc, Task.Tasks.PARSE));
                return;
            }
            printInfo("Parsed -"+res.link);
        }else {
            lcon.getTasks().add(new Task(res.link,null, Task.Tasks.DOWNLOAD));
        }
    }
    public static void stop() throws IOException {
        ConsoleController.ps.println("Init stop");
        lcon.killKids();
        save();
        System.exit(1);
    }
    public static void save() throws IOException {
        System.out.println("Save init");
        FileOutputStream fos =new FileOutputStream(Settings.dir+"save.json");
        Save s =new Save();
        s.chind = lcon.chind;
        s.index=lcon.getIndex();

        /*fos.write(String.valueOf(index).getBytes());
        fos.write('\n');
        Deque<Task> tasks = lcon.getTasks();
        for (Task task : tasks) {
            int ind = Integer.parseInt(task.link.split("-")[1]);
            System.out.println((ind-1)/ lcon.interval);
            fos.write(String.valueOf(((ind-1)/ lcon.interval)).getBytes());
            fos.write(" ".getBytes());
        }*/
        fos.write(new Gson().toJson(s).toString().getBytes());
        fos.flush();
        fos.close();
    }

    public static void setloggers(){
        try {
            System.setOut(new PrintStream(new FileOutputStream(Settings.logdir+ File.separator+"log"+System.currentTimeMillis()+".log")));
            System.setErr(new PrintStream(new FileOutputStream(Settings.logdir+ File.separator+ File.separator +"Err"+System.currentTimeMillis()+".log")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static void outIcon(){
        System.out.println(" .----------------.  .----------------.  .----------------.  .----------------.  .----------------.\n" +
                "| .--------------. || .--------------. || .--------------. || .--------------. || .--------------. |\n" +
                "| |  _________   | || | _____  _____ | || |  _______     | || |  _______     | || |  ____  ____  | |\n" +
                "| | |_   ___  |  | || ||_   _||_   _|| || | |_   __ \\    | || | |_   __ \\    | || | |_  _||_  _| | |\n" +
                "| |   | |_  \\_|  | || |  | |    | |  | || |   | |__) |   | || |   | |__) |   | || |   \\ \\  / /   | |\n" +
                "| |   |  _|      | || |  | '    ' |  | || |   |  __ /    | || |   |  __ /    | || |    \\ \\/ /    | |\n" +
                "| |  _| |_       | || |   \\ `--' /   | || |  _| |  \\ \\_  | || |  _| |  \\ \\_  | || |    _|  |_    | |\n" +
                "| | |_____|      | || |    `.__.'    | || | |____| |___| | || | |____| |___| | || |   |______|   | |\n" +
                "| |              | || |              | || |              | || |              | || |              | |\n" +
                "| '--------------' || '--------------' || '--------------' || '--------------' || '--------------' |\n" +
                " '----------------'  '----------------'  '----------------'  '----------------'  '----------------'");
    }
}
