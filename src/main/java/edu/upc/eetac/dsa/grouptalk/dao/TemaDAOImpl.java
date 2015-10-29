package edu.upc.eetac.dsa.grouptalk.dao;


import edu.upc.eetac.dsa.grouptalk.entity.Tema;
import edu.upc.eetac.dsa.grouptalk.entity.TemaCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ruben on 26/10/15.
 */

public class TemaDAOImpl implements TemaDAO {

    @Override
    public Tema createTema(String userid, String grupoid, String nombre, String comentario) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(UserDAOQuery.UUID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next())
                id = rs.getString(1);
            else
                throw new SQLException();

            stmt = connection.prepareStatement(TemaDAOQuery.CREATE_TEMA);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, grupoid);
            stmt.setString(4, nombre);
            stmt.setString(5, comentario);

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
        return getTemaById(id);
    }

    @Override
    public Tema getTemaById(String id) throws SQLException {
        Tema tema = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TemaDAOQuery.GET_TEMA_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                tema = new Tema();
                tema.setId(rs.getString("id"));
                tema.setNombre(rs.getString("nombre"));
                tema.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                tema.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return tema;
    }

    @Override
    public TemaCollection getTemas() throws SQLException {
        TemaCollection temaCollection = new TemaCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(TemaDAOQuery.GET_TEMAS);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Tema tema = new Tema();
                tema.setId(rs.getString("id"));
                tema.setGrupoid(rs.getString("grupoid"));
                tema.setUserid(rs.getString("userid"));
                tema.setNombre(rs.getString("nombre"));
                tema.setComentario(rs.getString("comentario"));
                tema.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                tema.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    temaCollection.setNewestTimestamp(tema.getLastModified());
                    first = false;
                }
                temaCollection.setOldestTimestamp(tema.getLastModified());
                temaCollection.getTemas().add(tema);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return temaCollection;
    }

    @Override

    public Tema updateTemas(String id, String userid, String grupoid, String nombre, String comentario) throws SQLException {
        Tema tema = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TemaDAOQuery.UPDATE_TEMA);
            stmt.setString(1, id);
            stmt.setString(2, userid);
            stmt.setString(3, grupoid);
            stmt.setString(4, nombre);
            stmt.setString(5, comentario);


            int rows = stmt.executeUpdate();
            if (rows == 1)
                tema = getTemaById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return tema;
    }

    @Override
    public boolean deleteTema(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(TemaDAOQuery.DELETE_TEMA);
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