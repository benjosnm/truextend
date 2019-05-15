package net.truextend.s4api.resources;

import net.truextend.s4api.dto.StudentDto;
import net.truextend.s4api.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/students")
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

    @Autowired
    private StudentService studentService;

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response addStudent(StudentDto student) {
        return studentService.saveStudent(student);
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getStudents() throws Exception {
        return studentService.retrieveAll();
    }

    @GET
    @Path("/{id}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getStudent(@PathParam("id") long id) throws Exception {
        return studentService.retrieveStudentById(id);
    }

    @GET
    @Path("/firstName/{firstName}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getClassByFirstName(@PathParam("firstName") String firstName) throws Exception {
        return studentService.retrieveClassByFirstName(firstName);
    }

    @GET
    @Path("/lastName/{lastName}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getClassByLastName(@PathParam("lastName") String lastName) throws Exception {
        return studentService.retrieveClassByLastName(lastName);
    }

    @GET
    @Path("/class/{classCode}")
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getClassByClassCode(@PathParam("classCode") String classCode) throws Exception {
        return studentService.retrieveClassByClassCode(classCode);
    }

    @PUT
    @Path("/{id}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response updateStudent(@PathParam("id") long id, StudentDto student) throws Exception {
        return studentService.updateStudent(id, student);
    }

    @DELETE
    @Path("/{id}")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response deleteStudent(@PathParam("id") long id) throws Exception {
        return studentService.deleteStudent(id);
    }
}
