package xyz.myfur.Controllers.Workers;

import lombok.Data;
import xyz.myfur.Core;
import xyz.myfur.Data.Response;
import xyz.myfur.Data.Task;



@Data
public abstract class Worker extends Thread implements Runnable {

    protected Task task;
    protected boolean die=false;
    protected boolean stay=true;

    public Worker(String name) {
        super(name);
    }

    protected abstract void method() throws Exception;
    protected abstract void onErr(Exception e);
    protected abstract void Finally();

    @Override
    public void run()
    {
        while (true)
        {
            if (die)break;
            if (stay||task==null)
            {
                stay=true;
                Thread.yield();
                continue;
            }
            try
            {
                method();
            }catch (Exception e)
            {
                onErr(e);
            }finally
            {
                stay=true;
                Finally();
                task=null;
            }
        }
    }

    public void setTask(Task t){
        this.task=t;
        stay=false;
    }

    public void die(){
        die=true;
        interrupt();
        task=null;
    }
    public void start(Task t) {
        setTask(t);
        super.start();
    }
    protected void sendMsg(Response msg){
        Core.post(msg);
    }
}
