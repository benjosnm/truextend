package net.truextend.s4api.service;

import net.truextend.s4api.dto.ClassDto;
import net.truextend.s4api.dto.StudentDto;
import net.truextend.s4api.repository.S4ClassRepository;
import net.truextend.s4api.repository.entity.S4ClassEntity;
import net.truextend.s4api.repository.entity.S4StudentEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class S4ClassService {

    @Autowired
    private S4ClassRepository classRepository;

    @Transactional
    public Response saveClass(ClassDto classDto) {
        Response response;
        if (classDto == null || classDto.getCode() == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("Class information is required").build();
        try{
            Response retrieveClass = retrieveClassByCode(classDto.getCode());
            if (retrieveClass.getStatus() == Response.Status.OK.getStatusCode()){
                response = Response.status(Response.Status.BAD_REQUEST).entity("S4 Class already exist").build();
            }else {
                response = persistS4Class(classDto);
            }

        }catch (Exception e){
            response =  Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

        return response;
    }

    @Transactional(readOnly = true)
    public Response retrieveAll() {
        Response response;
        List<S4ClassEntity> allClasses = classRepository.findAll();
        List<ClassDto> classDtoList = new ArrayList<>();

        allClasses.forEach(s4ClassEntity -> {
            ClassDto classDto = new ClassDto(
                    s4ClassEntity.getCode(),
                    s4ClassEntity.getTitle(),
                    s4ClassEntity.getDescription());
            addStudents(classDto,s4ClassEntity);
            classDtoList.add(classDto);
        });

        response = Response.status(Response.Status.OK).entity(classDtoList).build();

        return response;
    }

    @Transactional(readOnly = true)
    public Response retrieveClassByCode(String classCode) {
        Response response;

        try{
            Optional<S4ClassEntity> s4ClassEntity = classRepository.findById(classCode);

            if (!s4ClassEntity.isPresent())
                throw new Exception("Class not found");

            ClassDto dto = new ClassDto(
                    s4ClassEntity.get().getCode(),
                    s4ClassEntity.get().getTitle(),
                    s4ClassEntity.get().getDescription());
            addStudents(dto,s4ClassEntity.get());
            response =  Response.status(Response.Status.OK).entity(dto).build();
        }catch (Exception e){
            response =  Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

        return response;
    }

    @Transactional(readOnly = true)
    public Response retrieveClassByTitle(String title) {
        List<S4ClassEntity> s4ClassEntities = classRepository.findAllByTitle(title);
        return buildResponse(s4ClassEntities);
    }

    @Transactional(readOnly = true)
    public Response retrieveClassByDescription(String description) {
        List<S4ClassEntity> s4ClassEntities = classRepository.findAllByDescription(description);
        return buildResponse(s4ClassEntities);
    }

    @Transactional(readOnly = true)
    public Response retrieveClassByStudentId(String studentId) {
        List<S4ClassEntity> s4ClassEntities = classRepository.findAllByStudentId(Long.valueOf(studentId));
        return buildResponse(s4ClassEntities);
    }

    @Transactional
    public Response updateClass(String code, ClassDto classDto) {
        Response response;
        if (classDto == null || classDto.getCode() == null || code == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("Class information is required").build();
        try{
            Response retrievedClass = retrieveClassByCode(code);
            if (retrievedClass.getStatus() != Response.Status.OK.getStatusCode()){
                response = Response.status(Response.Status.BAD_REQUEST).entity("S4 Class not found").build();
            }else {
                response = persistS4Class(classDto);
            }

        }catch (Exception e){
            response =  Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

        return response;
    }

    @Transactional
    public Response deleteS4Class(String code) {
        Response response;

        if (code == null)
            return Response.status(Response.Status.BAD_REQUEST).entity("Class code is required.").build();
        try{
            Response retrievedClass = retrieveClassByCode(code);

            if (retrievedClass.getStatus() != Response.Status.OK.getStatusCode()){
                response = Response.status(Response.Status.BAD_REQUEST).entity("Class not found.").build();
            }else {
                classRepository.deleteById(code);
                response = Response.status(Response.Status.OK).entity("Class information was deleted.").build();
            }

        }catch (Exception e){
            response =  Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

        return response;
    }

    private Response persistS4Class(ClassDto s4Class) {
        S4ClassEntity entity = new S4ClassEntity();
        entity.setCode(s4Class.getCode());
        entity.setTitle(s4Class.getTitle());
        entity.setDescription(s4Class.getDescription());

        List<StudentDto> studentDtos = s4Class.getStudents();

        if (studentDtos != null){
            List<S4StudentEntity> subjects = new ArrayList<>();
            studentDtos.forEach(studentDto -> subjects.add(new S4StudentEntity(
                    Long.valueOf(studentDto.getId()),
                    studentDto.getFirstName(),
                    studentDto.getLastName())));
            entity.setStudents(subjects);
        }

        entity = classRepository.save(entity);
        ClassDto stored = new ClassDto(entity.getCode(), entity.getTitle(), entity.getDescription());
        return Response.status(Response.Status.OK).entity(stored).build();
    }

    private void addStudents(ClassDto dto, S4ClassEntity s4ClassEntity) {
        List<S4StudentEntity> studentsEntity = s4ClassEntity.getStudents();
        if (studentsEntity == null || studentsEntity.isEmpty())
            return;
        List<StudentDto> students = new ArrayList<>();
        studentsEntity.forEach(studentEntity -> students.add(new StudentDto(
                String.valueOf(studentEntity.getId()),
                studentEntity.getFirstName(),
                studentEntity.getLastName())));
        dto.setStudents(students);
    }

    private Response buildResponse(List<S4ClassEntity> s4ClassEntities) {
        Response response;
        List<ClassDto> classDtoList = new ArrayList<>();

        s4ClassEntities.forEach(s4ClassEntity -> {
            ClassDto classDto = new ClassDto(
                    s4ClassEntity.getCode(),
                    s4ClassEntity.getTitle(),
                    s4ClassEntity.getDescription());
            addStudents(classDto,s4ClassEntity);
            classDtoList.add(classDto);
        });

        response = Response.status(Response.Status.OK).entity(classDtoList).build();

        return response;
    }
}
