package xyz.myfur.Controllers;

import xyz.myfur.Controllers.Workers.Loader;
import xyz.myfur.Core;
import xyz.myfur.Data.Task;

import static xyz.myfur.Util.Logger.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;



public class LoadersController extends AbsController<Loader> {
    private Proxy[] proxy;
    private int proxyindex=0;
    public static final char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static int chind  = 0;


    public static  String baseurl = "https://www.facebook.com/directory/groups/";

    public static  int interval = 120;

    public int index = 0;

    public LoadersController(int tc) {
        super(tc);
    }


    @Override
    protected void method() throws Exception {
        for (int i = 0; i < tc; i++) {
            Task last = tasks.pollLast();
            Loader t = workers.get(i);
            if (last==null){
                if (!t.isAlive()){
                    if (t.getTask()!=null)
                        tasks.add(t.getTask());
                    t=new Loader(t.getName());
                    workers.set(i,t);
                    t.start(spawn());
                }else if (t.isStay()){
                    t.setTask(spawn());
                }
            }else {
                if (!t.isAlive()){
                    if (t.getTask()!=null)
                        tasks.add(t.getTask());
                    t=new Loader(t.getName());
                    workers.set(i,t);
                    t.start(last);
                }else if (t.isStay()){
                    t.setTask(last);
                }
            }
    }
    }

    @Override
    protected void onErr(Exception e) {
        System.out.println("Ошибка в главном потоке лоадеров!");
    }

    @Override
    protected void Finally() {}

    @Override
    protected void preStart() {
        loadProxy();
        for (int i = 0; i < tc; i++){
            workers.add(new Loader("Loader-"+i));
        }


    }


    private Task spawn(){
        String link =baseurl+chars[chind]+"-"+(1+(index)*interval)+'-'+(1+(index+1)*interval);
        Task t = new Task(link,null, Task.Tasks.DOWNLOAD);
        index++;
        return t;
    }

    public synchronized void next(char ch){
        if (chars[chind]==ch){
            chind++;
            index = 0;
            System.out.println("New char");
        }

    }
    public int getIndex() {
        return index;
    }
    /**Переписать*/
    private void loadProxy(){
        ArrayList<Proxy> proxy_list = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(Core.class.getResourceAsStream("proxy.data")));
        bufferedReader.lines().forEach((x)->{
            String[] arr = x.split(":");
            proxy_list.add(new Proxy(Proxy.Type.HTTP,new InetSocketAddress(arr[0],Integer.parseInt(arr[1]))));
            //System.out.println("Added proxy "+x);
        });
        proxy = proxy_list.toArray(new Proxy[proxy_list.size()]);
        printInfo("Proxy - "+proxy.length);
    }

    public synchronized Proxy getProxy(){
        addOne();
        return proxy[proxyindex];
    }
    private void addOne(){
        if (proxy.length-proxyindex==1){
            proxyindex=0;
        }else{
            proxyindex++;
        }
    }
}
