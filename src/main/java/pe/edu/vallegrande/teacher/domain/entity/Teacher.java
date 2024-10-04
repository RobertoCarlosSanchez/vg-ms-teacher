package pe.edu.vallegrande.teacher.domain.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "teacher")
public class Teacher {

    @MongoId
    @Field("_id")
    private String id;
    @Field("uid")
    private String uid;
    @Field("first_name")
    private String firstName;
    @Field("last_name")
    private String lastName;
    @Field("document_type")
    private String documentType;
    @Field("document_number")
    private String documentNumber;
    @Field("date_birth")
    private LocalDate dateBirth;
    @Field("role")
    private String role;
    @Field("email")
    private String email;
    @Field("password")
    private String password;
    @Field("cell_phone")
    private String cellPhone;
    @Field("gender")
    private String gender;
    @Field("date_hire")
    private LocalDate dateHire;
    @Field("creation_date")
    private LocalDateTime creationDate=LocalDateTime.now();
    @Field("write_date")
    private LocalDateTime writeDate=LocalDateTime.now();
    @Field("status")
    private String status="A";

}
