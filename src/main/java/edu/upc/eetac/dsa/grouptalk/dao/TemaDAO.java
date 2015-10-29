package edu.upc.eetac.dsa.grouptalk.dao;


import edu.upc.eetac.dsa.grouptalk.entity.Tema;
import edu.upc.eetac.dsa.grouptalk.entity.TemaCollection;

import java.sql.SQLException;

/**
 * Created by ruben on 26/10/15.
 */
public interface TemaDAO {
    public Tema createTema(String userid, String grupoid, String nombre, String comentario) throws SQLException;
    public Tema getTemaById(String id) throws SQLException;
    public TemaCollection getTemas() throws SQLException;
    public Tema updateTemas(String id, String nombre) throws SQLException;
    public boolean deleteTema(String id) throws SQLException;
}
