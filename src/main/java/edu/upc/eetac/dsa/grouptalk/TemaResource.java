package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.TemaDAO;
import edu.upc.eetac.dsa.grouptalk.dao.TemaDAOImpl;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import edu.upc.eetac.dsa.grouptalk.entity.Tema;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by ruben on 28/10/15.
 */
@Path("temas")
public class TemaResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GrouptalkMediaType.GROUPTALK_TEMA)
    public Response createSting(@FormParam("nombre") String nombre, @FormParam("comentario") String comentario, @Context UriInfo uriInfo) throws URISyntaxException {
        if (nombre == null)
            throw new BadRequestException("all parameters are mandatory");
        TemaDAO temaDAO = new TemaDAOImpl();
        Tema tema = null;
        AuthToken authenticationToken = null;
        try {
            tema = temaDAO.createTema(securityContext.getUserPrincipal().getName(), tema.getGrupoid(), nombre, comentario);

        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + tema.getId());
        return Response.created(uri).type(GrouptalkMediaType.GROUPTALK_GRUPO).entity(tema).build();
    }
}