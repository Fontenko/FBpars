package xyz.myfur.Util;

public class Logger {
    public static void printErr(String s){
        System.err.println(s);
    }
    public static void printInfo(String s){
        System.out.println("[Info]->"+s);
    }
    public static void print(String s){
        System.out.println("[Global]->"+s);
    }

}
