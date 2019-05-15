package net.truextend.s4api.resources;

import net.truextend.s4api.dto.ClassDto;
import net.truextend.s4api.service.S4ClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/classes")
@Produces(MediaType.APPLICATION_JSON)
public class S4ClassResource {
    @Autowired
    private S4ClassService s4ClassService;


    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addClass(ClassDto classDto) {
        return s4ClassService.saveClass(classDto);
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getClasses() throws Exception {
        return s4ClassService.retrieveAll();
    }

    @GET
    @Path("/{code}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getClass(@PathParam("code") String code) throws Exception {
        return s4ClassService.retrieveClassByCode(code);
    }

    @GET
    @Path("/title/{title}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getClassByTitle(@PathParam("title") String title) throws Exception {
        return s4ClassService.retrieveClassByTitle(title);
    }

    @GET
    @Path("/description/{description}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getClassByDescription(@PathParam("description") String description) throws Exception {
        return s4ClassService.retrieveClassByDescription(description);
    }

    @GET
    @Path("/student/{studentId}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getClassByClassCode(@PathParam("studentId") String studentId) throws Exception {
        return s4ClassService.retrieveClassByStudentId(studentId);
    }

    @PUT
    @Path("/{code}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response updateClass(@PathParam("code") String code, ClassDto classDto) throws Exception {
        return s4ClassService.updateClass(code, classDto);
    }

    @DELETE
    @Path("/{code}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteClass(@PathParam("code") String code) throws Exception {
        return s4ClassService.deleteS4Class(code);
    }
}
