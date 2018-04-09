package xyz.myfur.Data;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.jsoup.nodes.Document;

public class Task {
    @NotNull
    public String link;
    @Nullable
    public Document doc;
    @NotNull
    public Tasks type;
    public Task(String link, Document doc, Tasks type) {
        this.link = link;
        this.type = type;
        this.doc = doc;
    }

    public enum Tasks{
        DOWNLOAD,
        PARSE,
        GETMAX
    }
}
