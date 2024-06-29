package com.daveace.greenspaces.authenticationmethod;

import com.daveace.greenspaces.client.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.Accessors;

import static com.daveace.greenspaces.util.Constants.INVALID_AUTHORITY;
import static com.daveace.greenspaces.util.Regexp.LETTER_REGEX;

@Getter
@Setter
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="authentication_methods")
public class AuthenticationMethod {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp = LETTER_REGEX, message = INVALID_AUTHORITY)
    private String method;

    @ManyToOne
    @JoinColumn(name="client_id")
    @JsonBackReference
    private Client client;

    @Override
    public String toString() {
        return "AuthenticationMethod{" +
                "id:" + id +
                ", method:'" + method + '\'' +
                '}';
    }
}
