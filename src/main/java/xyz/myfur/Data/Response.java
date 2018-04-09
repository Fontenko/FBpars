package xyz.myfur.Data;

import com.sun.istack.internal.Nullable;
import org.jsoup.nodes.Document;

public class Response {
    @Nullable
    public Document doc;
    public String link;
    public Responces res;

    public Response(Document doc, String link, Responces res) {
        this.doc = doc;
        this.link = link;
        this.res = res;
    }

    public enum Responces{
        BAD,
        OK
    }

}
