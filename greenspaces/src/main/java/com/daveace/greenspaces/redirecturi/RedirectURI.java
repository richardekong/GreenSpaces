package com.daveace.greenspaces.redirecturi;

import com.daveace.greenspaces.client.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@Accessors(chain=true)
@Entity
@Table(name="redirect_uris")
public class RedirectURI {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uri; 
    @ManyToOne
    @JoinColumn(name="client_id")
    @JsonBackReference
    private Client client;

    @Override
    public String toString() {
        return "RedirectURI{" +
                "id:" + id +
                ", uri:'" + uri + '\'' +
                '}';
    }
}
