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
    //public Response createRespuesta(@FormParam("respuesta") String respuesta1, @Context UriInfo uriInfo) throws URISyntaxException {
    public Response createRespuesta(@FormParam("temaid") String temaid, @FormParam("userid") String userid, @FormParam("respuesta") String respuesta, @Context UriInfo uriInfo) throws URISyntaxException {
        if(userid==null || temaid==null || respuesta==null)
            throw new BadRequestException("all parameters are mandatory");

        RespuestaDAO respuestaDAO = new RespuestaDAOImpl();
        Respuesta respuestas = null;
        AuthToken authenticationToken = null;


        try {
         //   respuestas = respuestaDAO.createRespuesta(respuestas.getTemaid(), securityContext.getUserPrincipal().getName(), respuesta);
             respuestas = respuestaDAO.createRespuesta(userid, temaid, respuesta);
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        URI uri = new URI(uriInfo.getAbsolutePath().toString() + "/" + respuestas.getId());
        return Response.created(uri).type(GrouptalkMediaType.GROUPTALK_RESPUESTA).entity(respuesta).build();
    }

    /*@Path("/{id}")
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
                throw new NotFoundException("Respuesta with id = " + id + " doesn't exist");


            EntityTag eTag = new EntityTag(Long.toString(respuesta.getLastModified()));


            Response.ResponseBuilder rb = request.evaluatePreconditions(eTag);


            if (rb != null) {
                return rb.cacheControl(cacheControl).tag(eTag).build();
            }

            rb = Response.ok(respuesta).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }*/

    @Path("/{id}")
    @GET
    @Produces(GrouptalkMediaType.GROUPTALK_RESPUESTA)
    public Respuesta getRespuestaById(@PathParam("id") String id) {
        // Create cache-control
        Respuesta respuestas = null;

        try {
            respuestas = (new RespuestaDAOImpl().getRespuestaById(id));
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
        if (respuestas == null) {
            throw new NotFoundException("answer with id = " + id + " doesn't exist");
        }
        return respuestas;

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