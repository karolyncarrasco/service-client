package com.bootcamp.client.domain;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(value = "client")
public class Client {
    @Id
    private String id;

    @NotNull
    @Indexed(unique = true)
    private String identityNumber;

    @NotNull
    private IdentityType identityType;

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    private String businessName;

    @NotNull
    private String email;

    @NotNull
    private String phoneNumber;

    @NotNull
    private LocalDate birthday;

    @NotNull
    private ClientType clientType;

}
