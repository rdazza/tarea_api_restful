package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Respuesta;
import edu.upc.eetac.dsa.grouptalk.entity.RespuestaCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ruben on 26/10/15.
 */
public class RespuestaDAOImpl implements RespuestaDAO {

    @Override
    public Respuesta createRespuesta(String temaid, String userid, String respuesta) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RespuestaDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(RespuestaDAOQuery.CREATE_RESPUESTA);
            stmt.setString(1, id);
            stmt.setString(2, temaid);
            stmt.setString(3, userid);
            stmt.setString(4, respuesta);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }
        return getRespuestaById(id);
    }

    @Override
    public Respuesta getRespuestaById(String id) throws SQLException {
        Respuesta respuesta = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RespuestaDAOQuery.GET_RESPUESTA_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                respuesta = new Respuesta();
                respuesta.setId(rs.getString("id"));
                respuesta.setTemaid(rs.getString("temaid"));
                //respuesta.setCreator(rs.getString("fullname"));
                respuesta.setRespuesta(rs.getString("respuesta"));
               // respuesta.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
               // respuesta.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return respuesta;
    }

    @Override
    public RespuestaCollection getRespuestas() throws SQLException {
        RespuestaCollection respuestaCollection = new RespuestaCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(RespuestaDAOQuery.GET_RESPUESTAS);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Respuesta respuesta = new Respuesta();
                respuesta.setId(rs.getString("id"));
                respuesta.setTemaid(rs.getString("temaid"));
                respuesta.setUserid(rs.getString("userid"));
                respuesta.setRespuesta(rs.getString("respuesta"));
                //respuesta.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                //respuesta.setLastModified(rs.getTimestamp("last_modified").getTime());
                /*if (first) {
                    respuestaCollection.setNewestTimestamp(respuesta.getLastModified());
                    first = false;
                }
                respuestaCollection.setOldestTimestamp(respuesta.getLastModified());
                respuestaCollection.getRespuestas().add(respuesta);*/
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return respuestaCollection;
    }
    @Override
    public Respuesta updateRespuesta(String id, String temaid, String respuesta) throws SQLException {
        Respuesta respuesta1 = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RespuestaDAOQuery.UPDATE_RESPUESTA);
            stmt.setString(1, respuesta);
            stmt.setString(2, temaid);
            stmt.setString(3, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                respuesta1 = getRespuestaById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return respuesta1;
    }
    @Override
    public boolean deleteRespuesta(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(RespuestaDAOQuery.DELETE_RESPUESTA);
            stmt.setString(1, id);

            int rows = stmt.executeUpdate();
            return (rows == 1);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }
}
