package edu.upc.eetac.dsa.grouptalk.entity;

import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruben on 24/10/15.
 */
public class RespuestaCollection {
    private List<Link> links;
    private long newestTimestamp;
    private long oldestTimestamp;
    private List<Grupo> respuestas = new ArrayList<>();

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

    public List<Grupo> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<Grupo> respuestas) {
        this.respuestas = respuestas;
    }
}
