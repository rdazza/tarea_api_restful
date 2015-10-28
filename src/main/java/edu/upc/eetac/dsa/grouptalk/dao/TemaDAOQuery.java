package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by ruben on 28/10/15.
 */
public interface TemaDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_TEMA = "insert into tema (id, userid, grupoid, nombre, comentario) values (UNHEX(?), unhex(?), unhex(?), ?, ?)";
    public final static String GET_TEMA_BY_ID = "select hex(tema.id) as id, hex(tema.userid) as userid, tema.content, tema.subject, tema.creation_timestamp, tema.last_modified, users.fullname from tema, users where tema.id=unhex(?) and users.id=tema.userid";
    public final static String GET_TEMAS = "select hex(id) as id, hex(userid) as userid, hex(grupoid) as grupoid, nombre, comentario, creation_timestamp, last_modified, users.fullname from tema, users where users.id=tema.userid";
    public final static String UPDATE_TEMA = "update tema set comentario=? where id=unhex(?) ";
    public final static String DELETE_TEMA = "delete from tema where id=unhex(?)";

}