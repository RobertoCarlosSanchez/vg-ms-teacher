package pe.edu.vallegrande.teacher.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeacherResponse {

    private String id;
    private String firstName;
    private String lastName;
    private String documentType;
    private String documentNumber;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateBirth;
    private String role;
    private String email;
    private String password;
    private String cellPhone;
    private String gender;
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateHire;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime creationDate;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime writeDate;

}
