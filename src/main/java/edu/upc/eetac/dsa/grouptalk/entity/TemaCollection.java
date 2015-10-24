package edu.upc.eetac.dsa.grouptalk.entity;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruben on 24/10/15.
 */
public class TemaCollection {
    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Grupo> temas = new ArrayList<>();

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public long getNewestTimestamp() {
        return newestTimestamp;
    }

    public void setNewestTimestamp(long newestTimestamp) {
        this.newestTimestamp = newestTimestamp;
    }

    public long getOldestTimestamp() {
        return oldestTimestamp;
    }

    public void setOldestTimestamp(long oldestTimestamp) {
        this.oldestTimestamp = oldestTimestamp;
    }

    public List<Grupo> getTemas() {
        return temas;
    }

    public void setTemas(List<Grupo> temas) {
        this.temas = temas;
    }
}
