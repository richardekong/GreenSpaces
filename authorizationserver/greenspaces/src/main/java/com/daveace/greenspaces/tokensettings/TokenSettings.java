package com.daveace.greenspaces.tokensettings;

import com.daveace.greenspaces.client.Client;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain=true)
@Entity
@Table(name="token_settings")
public class TokenSettings {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private String format;
    @Column(name="access_token_ttl")
    private int accessTokenTTL;
    @OneToOne
    @JsonBackReference
    private Client client;

    @Override
    public String toString() {
        return "TokenSettings{" +
                "id:" + id +
                ", format:'" + format + '\'' +
                ", accessTokenTTL:" + accessTokenTTL +
                '}';
    }
}
