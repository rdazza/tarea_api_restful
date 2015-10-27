package edu.upc.eetac.dsa.grouptalk.dao;

import edu.upc.eetac.dsa.grouptalk.entity.Grupo;
import edu.upc.eetac.dsa.grouptalk.entity.GrupoCollection;

import java.sql.SQLException;

/**
 * Created by ruben on 26/10/15.
 */
public interface GrupoDAO {
    public Grupo createGrupo(String nombre) throws SQLException;
    public Grupo getGrupoById(String id) throws SQLException;
    public GrupoCollection getGrupos() throws SQLException;
    public Grupo updateGrupos(String id, String nombre) throws SQLException;
    public boolean deleteGrupo(String id) throws SQLException;
    public void joinGrupo(String userid, String grupoid) throws SQLException;
}
