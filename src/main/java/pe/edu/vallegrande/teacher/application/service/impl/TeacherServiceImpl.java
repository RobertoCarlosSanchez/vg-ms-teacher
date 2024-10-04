package pe.edu.vallegrande.teacher.application.service.impl;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.vallegrande.teacher.application.service.ITeacherService;
import pe.edu.vallegrande.teacher.domain.dto.TeacherRequest;
import pe.edu.vallegrande.teacher.domain.dto.TeacherResponse;
import pe.edu.vallegrande.teacher.domain.entity.Teacher;
import pe.edu.vallegrande.teacher.domain.mapper.TeacherMapper;
import pe.edu.vallegrande.teacher.domain.repository.TeacherRepository;
import pe.edu.vallegrande.teacher.infrastructure.client.AuthServiceClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements ITeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;
    private final AuthServiceClient authServiceClient;

    @Override
    public Flux<TeacherResponse> getAll() {
        return teacherRepository.findAllByOrderByCreationDateDesc()
                .map(teacherMapper::toTeacherResponse);
    }

    @Override
    public Flux<TeacherResponse> getActive() {
        return teacherRepository.findByStatusOrderByCreationDateDesc("A")
                .map(teacherMapper::toTeacherResponse);
    }

    @Override
    public Flux<TeacherResponse> getInactive() {
        return teacherRepository.findByStatusOrderByCreationDateDesc("I")
                .map(teacherMapper::toTeacherResponse);
    }

    @Override
    public Mono<TeacherResponse> getByDocumentNumber(String dni) {
        return teacherRepository.findByDocumentNumber(dni)
                .map(teacherMapper::toTeacherResponse);
    }

    @Override
    public Mono<TeacherResponse> getById(String id) {
        return teacherRepository.findById(id)
                .map(teacherMapper::toTeacherResponse);
    }

    @Override
    public Mono<TeacherResponse> create(TeacherRequest teacherRequest) {
        Teacher teacher = teacherMapper.toTeacher(teacherRequest);
        teacher.setRole("PROFESOR");

        UserRecord.CreateRequest request = new UserRecord.CreateRequest()
                .setEmail(teacherRequest.getEmail())
                .setPassword(teacherRequest.getDocumentNumber())
                .setDisplayName(teacherRequest.getFirstName() + " " + teacherRequest.getLastName());

        return Mono.fromCallable(() -> FirebaseAuth.getInstance().createUser(request))
                .flatMap(userRecord -> {
                    teacher.setUid(userRecord.getUid());
                    teacher.setPassword(teacher.getDocumentNumber());

                    try {
                        Map<String, Object> claims = new HashMap<>();
                        claims.put("role", "PROFESOR");

                        FirebaseAuth.getInstance().setCustomUserClaims(userRecord.getUid(), claims);
                    } catch (Exception e) {
                        System.err.println("Error setting custom claims: " + e.getMessage());
                        return Mono.error(e);
                    }

                    return teacherRepository.save(teacher);
                })
                .map(teacherMapper::toTeacherResponse)
                .onErrorResume(e -> {
                    System.err.println("Error creating user in Firebase: " + e.getMessage());
                    return Mono.error(e);
                });

    }

    @Override
    public Mono<TeacherResponse> update(String id, TeacherRequest teacherRequest) {
        return teacherRepository.findById(id)
                .flatMap(teacher -> {
                    teacherMapper.updateTeacher(teacher, teacherRequest);
                    return Mono.fromCallable(() -> FirebaseAuth
                                    .getInstance().updateUser(new UserRecord
                                            .UpdateRequest(teacher
                                            .getUid())
                                            .setDisplayName(teacher.getFirstName()+" "+teacher.getLastName())))
                            .then(teacherRepository.save(teacher));
                })
                .map(teacherMapper::toTeacherResponse);
    }

    @Override
    public Mono<Void> modifyStatus(String id, String status) {
        return teacherRepository.findById(id)
                .flatMap(teacher -> {
                    boolean isActive = "A".equals(status);
                    return Mono.fromCallable(() -> FirebaseAuth.getInstance().updateUser(
                            new UserRecord.UpdateRequest(teacher.getUid())
                                    .setDisabled(!isActive)
                    )).then(Mono.defer(() -> {
                        teacher.setStatus(status);
                        return teacherRepository.save(teacher);
                    }));
                })
                .then();
    }

    @Override
    public Mono<Void> modifyPassword(String id, String password) {
        return teacherRepository.findById(id)
                .flatMap(teacher -> {
                    return Mono.fromCallable(() -> FirebaseAuth.getInstance().updateUser(
                            new UserRecord.UpdateRequest(teacher.getUid())
                                    .setPassword(password)
                    )).then(Mono.defer(() -> {
                        teacher.setPassword(password);
                        teacher.setWriteDate(LocalDateTime.now());
                        return teacherRepository.save(teacher);
                    }));
                })
                .onErrorResume(e -> {
                    System.err.println("Error updating password in Firebase: " + e.getMessage());
                    return Mono.error(e);
                })
                .then();
    }

    @Override
    public Mono<Void> delete(String id) {
        return teacherRepository.deleteById(id);
    }

    @Override
    public Mono<Boolean> validateTokenAndRoles(String token, List<String> requiredRoles) {
        return authServiceClient.validateToken(token)
                .flatMap(validationResponse -> {
                    if (validationResponse.isValid() && requiredRoles.contains(validationResponse.getRole())) {
                        return Mono.just(true);
                    }
                    return Mono.just(false);
                });
    }

}
