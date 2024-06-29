package com.daveace.greenspaces.scope;


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
@Table(name="scopes")
public class Scope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String scope;
    @ManyToOne
    @JoinColumn(name="client_id")
    @JsonBackReference
    private Client client;

    @Override
    public String toString() {
        return "Scope{" +
                "id:" + id +
                ", scope:'" + scope + '\'' +
                '}';
    }

    public enum Scopes {

        OPENID("openid"),
        PROFILE("profile"),
        EMAIL("email"),
        ADDRESS("address"),
        PHONE("phone");

        private final String scope;

        Scopes(String scope) {
            this.scope = scope;
        }

        public String scope() {
            return scope;
        }

    }
}