package edu.upc.eetac.dsa.grouptalk.entity;

import org.glassfish.jersey.linking.InjectLinks;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by ruben on 24/10/15.
 */
public class Tema {
    @InjectLinks({})
    private List<Link> links;

    private String id;
    private String userid;
    private String grupoid;
    private String nombre;
    private String comentario;

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

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getGrupoid() {
        return grupoid;
    }

    public void setGrupoid(String grupoid) {
        this.grupoid = grupoid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
