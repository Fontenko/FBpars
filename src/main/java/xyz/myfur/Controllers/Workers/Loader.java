package xyz.myfur.Controllers.Workers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import sun.nio.ch.Net;
import xyz.myfur.Core;
import xyz.myfur.Data.Response;
import xyz.myfur.Util.Settings;


import java.io.IOException;
import java.net.Proxy;


import static xyz.myfur.Util.Logger.*;
public class Loader extends Worker {
    private Proxy proxy;

    public Loader(String name) {
        super(name);
    }

    @Override
    protected void method() throws IOException {
        proxy = Settings.lcon.getProxy();
        Document doc = Jsoup.connect(task.link).proxy(proxy).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/65.0.3325.181 Safari/537.36").get();
        sendMsg(new Response(doc,task.link, Response.Responces.OK));
    }

    @Override
    protected void onErr(Exception e) {
        printErr("Что-то не так в загрузчиках!");
        printErr(e.getMessage());
        sendMsg(new Response(null,task.link, Response.Responces.BAD));
    }

    @Override
    protected void Finally() {

    }



}
