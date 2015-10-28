package edu.upc.eetac.dsa.grouptalk;

import edu.upc.eetac.dsa.grouptalk.dao.RespuestaDAO;
import edu.upc.eetac.dsa.grouptalk.dao.RespuestaDAOImpl;
import edu.upc.eetac.dsa.grouptalk.entity.AuthToken;
import edu.upc.eetac.dsa.grouptalk.entity.Respuesta;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;

/**
 * Created by ruben on 28/10/15.
 */
@Path("respuestas")
public class RespuestaResource {
    @Context
    private SecurityContext securityContext;

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(GrouptalkMediaType.GROUPTALK_TEMA)
    public Response createRespuesta(@FormParam("respuesta") String respuesta1, @Context UriInfo uriInfo) throws URISyntaxException {
        if (respuesta1 == null)
            throw new BadRequestException("all parameters are mandatory");
        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        Respuesta respuesta = null;
        AuthToken authenticationToken = null;
        try {
            respuesta = respuestaDAO.createRespuesta(respuesta.getTemaid(), securityContext.getUserPrincipal().getName(), respuesta1);

        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + respuesta.getId());
        return Response.created(uri).type(GrouptalkMediaType.GROUPTALK_GRUPO).entity(respuesta).build();
    }
}