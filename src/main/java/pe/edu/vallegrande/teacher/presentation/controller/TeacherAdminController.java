package pe.edu.vallegrande.teacher.presentation.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.vallegrande.teacher.application.service.ITeacherService;
import pe.edu.vallegrande.teacher.domain.dto.TeacherRequest;
import pe.edu.vallegrande.teacher.domain.dto.TeacherResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v2/directives")
@RequiredArgsConstructor
public class TeacherAdminController {

    private final ITeacherService teacherService;

    private static final List<String> ALLOWED_ROLES = Arrays.asList("DEVELOP", "PROFESOR", "SECRETARIO", "DIRECTOR");

    @GetMapping("/teachers")
    public Mono<ResponseEntity<Flux<TeacherResponse>>> getAll(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return teacherService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return Mono.just(ResponseEntity.ok(teacherService.getAll()));
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @GetMapping("/teachers/active")
    public Mono<ResponseEntity<Flux<TeacherResponse>>> getActive(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return teacherService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return Mono.just(ResponseEntity.ok(teacherService.getActive()));
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @GetMapping("/teachers/inactive")
    public Mono<ResponseEntity<Flux<TeacherResponse>>> getInactive(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return teacherService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return Mono.just(ResponseEntity.ok(teacherService.getInactive()));
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @GetMapping("/teacher/{id}")
    public Mono<ResponseEntity<TeacherResponse>> getById(@PathVariable String id, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return teacherService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return teacherService.getById(id)
                                .map(ResponseEntity::ok)
                                .defaultIfEmpty(ResponseEntity.notFound().build());
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @GetMapping("/teacher/document/{documentNumber}")
    public Mono<ResponseEntity<TeacherResponse>> getByDocumentNumber(@PathVariable String documentNumber, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return teacherService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return teacherService.getByDocumentNumber(documentNumber)
                                .map(ResponseEntity::ok)
                                .defaultIfEmpty(ResponseEntity.notFound().build());
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @PostMapping("/teacher/")
    public Mono<ResponseEntity<TeacherResponse>> create(@RequestBody TeacherRequest teacherRequest, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return teacherService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return teacherService.create(teacherRequest)
                                .map(ResponseEntity::ok);
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @PutMapping("/teacher/{id}")
    public Mono<ResponseEntity<TeacherResponse>> update(@PathVariable String id, @RequestBody TeacherRequest teacherRequest, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return teacherService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return teacherService.update(id, teacherRequest)
                                .map(ResponseEntity::ok);
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @PatchMapping("/teacher/password/{id}")
    public Mono<ResponseEntity<Void>> modifyPassword(@PathVariable String id, @RequestBody String password, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return teacherService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return teacherService.modifyPassword(id, password)
                                .then(Mono.just(ResponseEntity.ok().build()));
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

    @DeleteMapping("/teacher/{id}")
    public Mono<ResponseEntity<Void>> modifyStatus(@PathVariable String id, @RequestBody String status, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        return teacherService.validateTokenAndRoles(token, ALLOWED_ROLES)
                .flatMap(isValid -> {
                    if (isValid) {
                        return teacherService.modifyStatus(id, status)
                                .then(Mono.just(ResponseEntity.ok().build()));
                    } else {
                        return Mono.just(ResponseEntity.status(403).build());
                    }
                });
    }

}
