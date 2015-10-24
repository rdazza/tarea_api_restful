package edu.upc.eetac.dsa.grouptalk.entity;

import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by ruben on 24/10/15.
 */
public class Respuesta {
    @InjectLinks({})
    private List<Link> links;

    private String id;
    private String temaid;
    private String userid;
    private String Respuesta;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTemaid() {
        return temaid;
    }

    public void setTemaid(String temaid) {
        this.temaid = temaid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRespuesta() {
        return Respuesta;
    }

    public void setRespuesta(String respuesta) {
        Respuesta = respuesta;
    }
}
