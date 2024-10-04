package pe.edu.vallegrande.teacher.domain.mapper;

import org.mapstruct.*;
import pe.edu.vallegrande.teacher.domain.dto.TeacherRequest;
import pe.edu.vallegrande.teacher.domain.dto.TeacherResponse;
import pe.edu.vallegrande.teacher.domain.entity.Teacher;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface TeacherMapper {

    TeacherResponse toTeacherResponse(Teacher teacher);
    Teacher toTeacher(TeacherRequest teacherRequest);
    void updateTeacher(@MappingTarget Teacher teacher, TeacherRequest teacherRequest);

}
