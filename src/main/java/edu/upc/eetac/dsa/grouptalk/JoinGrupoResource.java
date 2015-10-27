package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.GrupoDAO;
import edu.upc.eetac.dsa.grouptalk.dao.GrupoDAOImpl;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import sun.security.util.SecurityConstants;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by ruben on 27/10/15.
 */
    @Path("grupo")
    public class JoinGrupoResource {
    @Context
    private SecurityContext securityContext;

    @Path("/{id}")
    @POST
    @Produces(GrouptalkMediaType.GROUPTALK_GRUPO)
    public Response JoinGrupo(@PathParam("id") String id, @Context UriInfo uriInfo) throws URISyntaxException {
        if (id == null) {
            throw new BadRequestException("todos los parámetros son obligatorios");
        }
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        AuthToken authenticationToken = null;
        try {
            grupoDAO.joinGrupo(this.securityContext.getUserPrincipal().getName(), id);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return Response.noContent().build();
    }
}



