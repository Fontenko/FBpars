package xyz.myfur.Controllers.Workers;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import xyz.myfur.Data.Response;
import xyz.myfur.Util.Database;
import xyz.myfur.Util.Settings;

import static xyz.myfur.Util.Logger.*;

public class Parser extends Worker {

    public Parser(String name) {
        super(name);
    }

    @Override
    protected void method() throws Exception {
        Document doc = task.doc;
        if (doc.getElementById("captcha") != null)
            throw new Exception("Каптча");
        int ind = 0;
        for (Element element : doc.select("a._8o._8t.lfloat._ohe")) {
            Database.getInstance().addGroup(element.attr("href"));
            ind++;
        }
        if (ind==0){
            int t = task.link.split("-")[0].split("/").length;
            Settings.lcon.next(task.link.split("-")[0].split("/")[t-1].toCharArray()[0]);
        }
        sendMsg(new Response(null, task.link, Response.Responces.OK));
    }

    @Override
    protected void onErr(Exception e) {
        printErr("Что-то не так в парсерах");
        printErr(e.getMessage());
        sendMsg(new Response(null, task.link, Response.Responces.BAD));
    }

    @Override
    protected void Finally() {}

}

