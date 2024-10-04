package pe.edu.vallegrande.teacher.application.service;

import pe.edu.vallegrande.teacher.domain.dto.TeacherRequest;
import pe.edu.vallegrande.teacher.domain.dto.TeacherResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ITeacherService {

    Flux<TeacherResponse> getAll();
    Flux<TeacherResponse> getActive();
    Flux<TeacherResponse> getInactive();
    Mono<TeacherResponse> getByDocumentNumber(String dni);
    Mono<TeacherResponse> getById(String id);
    Mono<TeacherResponse> create(TeacherRequest teacherRequest);
    Mono<TeacherResponse> update(String id, TeacherRequest teacherRequest);
    Mono<Void> modifyStatus(String id, String status);
    Mono<Void> modifyPassword(String id, String password);
    Mono<Void> delete(String id);
    Mono<Boolean> validateTokenAndRoles(String token, List<String> requiredRoles);

}
