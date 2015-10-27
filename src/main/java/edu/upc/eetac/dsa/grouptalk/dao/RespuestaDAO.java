package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Respuesta;
import edu.upc.eetac.dsa.grouptalk.entity.RespuestaCollection;

import java.sql.SQLException;

/**
 * Created by ruben on 26/10/15.
 */
public interface RespuestaDAO {
    public Respuesta createRespuesta(String temaid, String userid, String respuesta) throws SQLException;
    public Respuesta getRespuestaById(String id) throws SQLException;
    public RespuestaCollection getRespuestas() throws SQLException;
    public Respuesta updateRespuesta(String id, String temaid, String respuesta) throws SQLException;
    public boolean deleteRespuesta(String id) throws SQLException;
}