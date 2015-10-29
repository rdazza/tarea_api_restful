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
    public Response createTema(@FormParam("nombre") String nombre, @FormParam("comentario") String comentario, @Context UriInfo uriInfo) throws URISyntaxException {
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
        return Response.created(uri).type(GrouptalkMediaType.GROUPTALK_TEMA).entity(tema).build();
    }


    @Path("/{id}")
    @GET
    @Produces(GrouptalkMediaType.GROUPTALK_TEMA)
    public Response getTema(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Tema tema = null;
        TemaDAO stingDAO = new TemaDAOImpl();
        try {
            tema = stingDAO.getTemaById(id);
            if (tema == null)
                throw new NotFoundException("Tema con id = " + id + " no existe");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(tema.getLastModified()));

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
            rb = Response.ok(tema).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
/*
    @Path("/{id}")
    @PUT
    @Consumes(GrouptalkMediaType.GROUPTALK_TEMA)
    @Produces(GrouptalkMediaType.GROUPTALK_TEMA)
    public Tema updateTema(@PathParam("id") String id, Tema tema) {
        if(tema == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(tema.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        String userid = securityContext.getUserPrincipal().getName();
        if(!userid.equals(tema.getUserid()))
            throw new ForbiddenException("operation not allowed");

        TemaDAO temaDAO = new TemaDAOImpl();
        try {
            tema = temaDAO.updateTemas(id, tema.getGrupoid(), tema.getNombre(), tema.getComentario());
            if(tema == null)
                throw new NotFoundException("Tema with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return tema;
    }
   */
    @Path("/{id}")
    @DELETE
    public void deleteTema(@PathParam("id") String id) {
        String userid = securityContext.getUserPrincipal().getName();
        TemaDAO temaDAO = new TemaDAOImpl();
        try {
            String ownerid = temaDAO.getTemaById(id).getUserid();
            if(!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if(!temaDAO.deleteTema(id))
                throw new NotFoundException("Tema with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
}