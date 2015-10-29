package edu.upc.eetac.dsa.grouptalk.dao;

/**
 * Created by ruben on 26/10/15.
 */
public class GrupoDAOQuery {

    public final static String UUID = "select REPLACE(UUID(),'-','')";
    public final static String CREATE_GRUPO = "insert into grupo (id, nombre) values (UNHEX(?), ?)";
    //public final static String GET_GRUPO_BY_ID = "select hex(id) as id where grupo.id=unhex(?)";
    public final static String GET_GRUPO_BY_ID = "select * from grupo where hex(id)=(?)";
    public final static String GET_GRUPOS = "select hex(id) as id, nombre, creation_timestamp, last_modified from grupo";
    public final static String UPDATE_GRUPO = "update grupo set nombre=? where id=unhex(?) ";
    public final static String DELETE_GRUPO = "delete from grupo where id=unhex(?)";
    public final static String JOIN_GRUPO = "insert into relacionesgrupo (userid, grupoid) values (unhex(?), unhex(?))";
}
