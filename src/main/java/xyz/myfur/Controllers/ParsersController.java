package xyz.myfur.Controllers;

import xyz.myfur.Controllers.Workers.Parser;
import xyz.myfur.Data.Task;




public class ParsersController extends AbsController<Parser> {

    public ParsersController(int tc) {
        super(tc);
    }

    @Override
    protected void method() throws Exception {

        for (int i = 0; i < tc; i++) {
            Task last = tasks.pollLast();
            Parser t = workers.get(i);
            if (!t.isAlive()&&last!=null){
                t=new Parser(t.getName());
                workers.set(i,t);
                t.start(last);
            }else if (t.isStay()&&last!=null){
                t.setTask(last);
            }
        }
    }

    @Override
    protected void onErr(Exception e) {

    }

    @Override
    protected void Finally() {

    }

    @Override
    protected void preStart() {
        for (int i = 0; i < tc; i++) {
            workers.add(new Parser("Parser-"+i));
        }
    }




}
