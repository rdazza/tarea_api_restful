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
        return Response.created(uri).type(GrouptalkMediaType.GROUPTALK_RESPUESTA).entity(respuesta).build();
    }

    @Path("/{id}")
    @GET
    @Produces(GrouptalkMediaType.GROUPTALK_RESPUESTA)
    public Response getRespuesta(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Respuesta respuesta = null;
        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        try {
            respuesta = respuestaDAO.getRespuestaById(id);
            if (respuesta == null)
                throw new NotFoundException("Sting with id = " + id + " doesn't exist");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(respuesta.getLastModified()));

            // Verify if it matched with etag available in http request
            Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);

            // If ETag matches the rb will be non-null;
            // Use the rb to return the response without any further processing
            if (rb != null) {
                return rb.cacheControl(cacheControl).tag(eTag).build();
            }

            // If rb is null then either it is first time request; or resource is
            // modified
            // Get the updated representation and return with Etag attached to it
            rb = Response.ok(respuesta).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(GrouptalkMediaType.GROUPTALK_RESPUESTA)
    @Produces(GrouptalkMediaType.GROUPTALK_RESPUESTA)
    public Respuesta updateRespuesta(@PathParam("id") String id, Respuesta respuesta) {
        if(respuesta == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(respuesta.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(respuesta.getUserid()))
            throw new ForbiddenException("operation not allowed");

        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        try {
            respuesta = respuestaDAO.updateRespuesta(id, respuesta.getTemaid(), respuesta.getRespuesta());
            if(respuesta == null)
                throw new NotFoundException("Respuesta with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return respuesta;
    }
    @Path("/{id}")
    @DELETE
    public void deleteRespuesta(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        try {
            String ownerid = respuestaDAO.getRespuestaById(id).getUserid();
            if(!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if(!respuestaDAO.deleteRespuesta(id))
                throw new NotFoundException("Respuesta with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}