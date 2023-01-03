package com.bootcamp.client.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "identityType")
public class IdentityType {
    @Id
    private String id;

    @NotNull
    private String description;

    @NotNull
    private Boolean status;

    @NotNull
    private LocalDate creationDate;

    @NotNull
    private String creationUser;

    @NotNull
    private LocalDate modifiedDate;

    @NotNull
    private String modifiedUser;
}
