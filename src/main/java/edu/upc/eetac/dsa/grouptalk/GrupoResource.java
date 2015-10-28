package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.GrupoDAO;
import edu.upc.eetac.dsa.grouptalk.dao.GrupoDAOImpl;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import edu.upc.eetac.dsa.grouptalk.entity.Grupo;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by ruben on 28/10/15.
 */
@Path("grupos")
public class GrupoResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GrouptalkMediaType.GROUPTALK_GRUPO)
    public Response createSting(@FormParam("nombre") String nombre, @Context UriInfo uriInfo) throws URISyntaxException {
        if (nombre == null)
            throw new BadRequestException("all parameters are mandatory");
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        Grupo grupo = null;
        AuthToken authenticationToken = null;
        try {
            grupo = grupoDAO.createGrupo(securityContext.getUserPrincipal().getName(), nombre);

        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + grupo.getId());
        return Response.created(uri).type(GrouptalkMediaType.GROUPTALK_GRUPO).entity(grupo).build();
    }
}
