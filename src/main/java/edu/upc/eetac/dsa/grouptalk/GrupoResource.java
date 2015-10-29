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
    public Response createGrupo(@FormParam("nombre") String nombre, @Context UriInfo uriInfo) throws URISyntaxException {
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
        String id= grupo.getId();


          URI uri = new URI(uriInfo.getAbsolutePath().toString() );
        System.out.println(uri);
        return Response.created(uri).type(GrouptalkMediaType.GROUPTALK_GRUPO).entity(grupo).build();

    }

    @Path("/{id}")
    @GET
    @Produces(GrouptalkMediaType.GROUPTALK_GRUPO)
    public Response getGrupo(@PathParam("id") String id, @Context Request request) {
        // Create cache-control
        CacheControl cacheControl = new CacheControl();
        Grupo grupo = null;
        GrupoDAO grupoDAO = new GrupoDAOImpl();
        try {
            grupo = grupoDAO.getGrupoById(id);
            if (grupo == null)
                throw new NotFoundException("Grupo con id = " + id + " no existe");

            // Calculate the ETag on last modified date of user resource
            EntityTag eTag = new EntityTag(Long.toString(grupo.getLastModified()));

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
            rb = Response.ok(grupo).cacheControl(cacheControl).tag(eTag);
            return rb.build();
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }
/*
    @Path("/{id}")
    @PUT
    @Consumes(GrouptalkMediaType.GROUPTALK_GRUPO)
    @Produces(GrouptalkMediaType.GROUPTALK_GRUPO)
    public Grupo updateGrupo(@PathParam("id") String id, Grupo grupo) {
        if(grupo == null)
            throw new BadRequestException("entity is null");
        if(!id.equals(grupo.getId()))
            throw new BadRequestException("path parameter id and entity parameter id doesn't match");

        //String userid = securityContext.getUserPrincipal().getName();
        //if(!userid.equals(sting.getUserid()))
          //  throw new ForbiddenException("operation not allowed");

        GrupoDAO grupoDAO = new GrupoDAOImpl();
        try {
            grupo = grupoDAO.updateGrupos(id, grupo.getNombre());
            if(grupo == null)
                throw new NotFoundException("Grupo con id = "+id+" no existe");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
        return grupo;
    }*/


    /*
    @Path("/{id}")
    @DELETE
    public void deleteGrupo(@PathParam("id") String id) {
        //String userid = securityContext.getUserPrincipal().getName();
        GrupoDAO stingDAO = new GrupoDAOImpl();
        try {
            String ownerid = grupoDAO.getStingById(id).getUserid();
            if(!userid.equals(ownerid))
                throw new ForbiddenException("operation not allowed");
            if(!stingDAO.deleteSting(id))
                throw new NotFoundException("Sting with id = "+id+" doesn't exist");
        } catch (SQLException e) {
            throw new InternalServerErrorException();
        }
    }*/
}
















