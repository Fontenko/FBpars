package xyz.myfur.Util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import xyz.myfur.Controllers.LoadersController;
import xyz.myfur.Controllers.ParsersController;
import xyz.myfur.Data.Save;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Settings {
    public static final String dir = System.getProperty("user.dir")+ File.separator+"FurParser"+File.separator;
    public static final String plugdir = dir+"plugins"+File.separator;
    public static final String logdir = dir+"logs"+File.separator;
    public static final String outdir = dir+"out"+File.separator;
    static {
        mkdir(dir);
        mkdir(plugdir);
        mkdir(logdir);
        mkdir(outdir);
    }
    public static LoadersController lcon =null;
    public static ParsersController pcon =null;


    public static void readdata(){
        /*try(BufferedReader buff = new BufferedReader(new FileReader(dir+"save.data"))){
        lcon.index =  Integer.parseInt(buff.readLine());
        String[]a = buff.readLine().split(" ");
        for (String s : a) {
            int i=Integer.parseInt(s);
            lcon.getTasks().add(new Task(lcon.baseurl+(1+(i)* lcon.interval)+'-'+(1+(i+1)* lcon.interval),null, Task.Tasks.DOWNLOAD));
        }}catch (Exception e){
            System.out.println("Save not found");
        }*/
        try {
            Gson gs = new Gson();
          JsonElement jp = new JsonParser().parse(new FileReader(dir+"save.json"));
           Save s =  gs.fromJson(new FileReader(dir+"save.json"), Save.class);
           System.out.println(s.index);
           lcon.index = s.index;
           lcon.chind = s.chind;
        } catch (Exception e) {

        }
    }
    public static void mkdir(String dir){
        try {
            Files.createDirectories(Paths.get(dir));
        } catch (IOException e) {
            System.out.println("Ошибка при создании директории - "+dir);
        }
    }
    public static void activate(){}
}
