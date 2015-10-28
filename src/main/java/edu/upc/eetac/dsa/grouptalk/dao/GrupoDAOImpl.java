package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Grupo;
import edu.upc.eetac.dsa.grouptalk.entity.GrupoCollection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by ruben on 26/10/15.
 */

public class GrupoDAOImpl implements GrupoDAO {

    @Override
    public Grupo createGrupo(String userid, String nombre) throws SQLException {
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

            stmt = connection.prepareStatement(GrupoDAOQuery.CREATE_GRUPO);
            stmt.setString(1, id);
            stmt.setString(2, nombre);
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
        return getGrupoById(id);
    }

    @Override
    public Grupo getGrupoById(String id) throws SQLException {
        Grupo grupo = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GrupoDAOQuery.GET_GRUPO_BY_ID);
            stmt.setString(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                grupo = new Grupo();
                grupo.setId(rs.getString("id"));
                grupo.setNombre(rs.getString("nombre"));
                grupo.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                grupo.setLastModified(rs.getTimestamp("last_modified").getTime());
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return grupo;
    }

    @Override
    public GrupoCollection getGrupos() throws SQLException {
        GrupoCollection grupoCollection= new GrupoCollection();

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GrupoDAOQuery.GET_GRUPOS);

            ResultSet rs = stmt.executeQuery();
            boolean first = true;
            while (rs.next()) {
                Grupo grupo = new Grupo();
                grupo.setId(rs.getString("id"));
                grupo.setNombre(rs.getString("nombre"));
                grupo.setCreationTimestamp(rs.getTimestamp("creation_timestamp").getTime());
                grupo.setLastModified(rs.getTimestamp("last_modified").getTime());
                if (first) {
                    grupoCollection.setNewestTimestamp(grupo.getLastModified());
                    first = false;
                }
                grupoCollection.setOldestTimestamp(grupo.getLastModified());
                grupoCollection.getGrupos().add(grupo);
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
        return grupoCollection;
    }

    @Override
    public Grupo updateGrupos(String id, String nombre) throws SQLException {
        Grupo grupo = null;

        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GrupoDAOQuery.UPDATE_GRUPO   );
            stmt.setString(1,nombre);
            stmt.setString(2, id);

            int rows = stmt.executeUpdate();
            if (rows == 1)
                grupo = getGrupoById(id);
        } catch (SQLException e) {
            throw e;
        } finally {
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }

        return grupo;
    }

    @Override
    public boolean deleteGrupo(String id) throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        try {
            connection = Database.getConnection();

            stmt = connection.prepareStatement(GrupoDAOQuery.DELETE_GRUPO);
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

    @Override
    public void joinGrupo (String userid, String grupoid) throws SQLException{
        Connection connection = null;
        PreparedStatement stmt = null;
        String id = null;
        try{
            connection = Database.getConnection();
            stmt = connection.prepareStatement(GrupoDAOQuery.JOIN_GRUPO);
            stmt.setString(1, userid);
            stmt.setString(2, grupoid);

            System.out.println(stmt.toString());
            stmt.executeUpdate();
        }
        catch (SQLException exception){
            throw exception;
        }
        finally {
            if (stmt != null) {
                stmt.close();
            }
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        }


    }
}