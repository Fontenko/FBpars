package xyz.myfur.Controllers;

import lombok.Data;

import xyz.myfur.Controllers.Workers.Worker;
import xyz.myfur.Data.Task;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
@Data
public abstract class AbsController<T extends Worker> extends Thread implements Runnable{
        //Количество рабочих потоков
        protected int tc;
        //состояние сна и смерти потока
        protected boolean stay=false;
        protected boolean die=false;
        //рабочие и задания
        protected final ArrayList<T>workers = new ArrayList<>();
        protected final Deque<Task> tasks = new ArrayDeque<>();
        //Абстрактные методы состояния
        protected abstract void method() throws Exception;
        protected abstract void onErr(Exception e);
        protected abstract void Finally();
        protected abstract void preStart();

        public AbsController(int tc) {
                this.tc = tc;
        }

        //Основной цикл
        @Override
        public void run() {
                preStart();
                while (!die)
                {
                        if (stay){
                                Thread.yield();
                                continue;
                        }
                        try{
                                method();
                        }catch (Exception e){
                                onErr(e);
                        }
                        finally{
                                Finally();
                        }
                }
        }

        public void killKids() {
                die=true;
                for (Worker worker : workers) {
                        worker.die();
                }

        }
}
