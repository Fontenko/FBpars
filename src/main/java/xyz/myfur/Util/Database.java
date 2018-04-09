package xyz.myfur.Util;

import java.io.File;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;

public class Database {
    public static final String CON_STR = "jdbc:sqlite:"+Settings.outdir+"db.database";
    private static Database ourInstance = new Database();
    private static Connection con;
    public static Database getInstance() {
        return ourInstance;
    }
    private static PrintStream ps;

    static {
        try {
            ps = new PrintStream(Files.newOutputStream(Paths.get(Settings.outdir+File.separator+"out.links"),StandardOpenOption.CREATE, StandardOpenOption.APPEND));
        } catch (Exception e) {
            System.exit(2);
        }
    }

    private Database(){
        /*try {
            System.out.println(CON_STR);
            DriverManager.registerDriver(new JDBC());
            con = DriverManager.getConnection(CON_STR);
            printInfo("Success");
        } catch (SQLException e) {
            printErr("Database not found");
            System.exit(2);
        }*/
    }

    public static void addGroup(String link){
        ps.println(link);
       /* try(PreparedStatement prep = con.prepareStatement("INSERT INTO  groups(`link`) VALUES(?)")){
            prep.setObject(1,link);
            prep.execute();
            System.out.print("New");
        }catch (Exception e){
            System.out.println(e.getMessage());
        }*/
    }
}
