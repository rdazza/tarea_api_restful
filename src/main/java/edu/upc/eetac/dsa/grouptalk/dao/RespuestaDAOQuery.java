package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by ruben on 26/10/15.
 */
public interface RespuestaDAOQuery {
    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_RESPUESTA = "insert into respuesta (id, temaid, urerid, respuesta) values (UNHEX(?), unhex(?), unhex(?), ?)";
    public final static String GET_RESPUESTA_BY_ID = "select hex(respuesta.id) as id, hex(respuesta.temaid) as temaid, hex(respuesta.userid) as userid, respuesta.respuesta, respuesta.creation_timestamp, respuesta.last_modified, user.fullname from respuesta, users where respuesta.id=unhex(?) and users.id=respuesta.userid";
    public final static String GET_RESPUESTAS = "select hex(id) as id, hex(userid) as temaid, hex(userid) as userid, respuesta, creation_timestamp, last_modified from respuesta";
    public final static String UPDATE_RESPUESTA = "update respuesta set respuesta=? where id=unhex(?)";
    public final static String DELETE_RESPUESTA = "delete from respuesta where id=unhex(?)";
}