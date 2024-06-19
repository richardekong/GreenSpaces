package com.daveace.greenspaces.granttypes;

import com.daveace.greenspaces.client.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@Entity
@Table(name="grant_types")
public class GrantType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String grantType;
    @ManyToOne
    @JoinColumn(name="client_id")
    @JsonBackReference
    private Client client;

    @Override
    public String toString() {
        return "GrantType{" +
                "id=" + id +
                ", grantType='" + grantType + '\'' +
                '}';
    }

    enum GrantTypes {

        AUTHORIZATION_CODE("authorization_code"),
        REFRESH_TOKEN("refresh_token"),
        CLIENT_CREDENTIALS("client_credentials");

        private final String grantType;

        GrantTypes(String grantType) {
            this.grantType = grantType;
        }

        public String grantType() {
            return grantType;
        }

    }
}