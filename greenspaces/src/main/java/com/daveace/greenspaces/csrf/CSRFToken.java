package com.daveace.greenspaces.csrf;

import com.daveace.greenspaces.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Range;

import java.time.LocalTime;

@Setter
@Getter
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@Accessors(chain = true)
@Entity
@Table(name = "csrf_token")
public class CSRFToken extends BaseEntity<CSRFToken> {

    private String clientId;

    private String token;

    @Column(name = "issued_at")
    private final LocalTime issuedAt = LocalTime.now();

    @Column(name = "expires_in")
    @Range(min=1, max=60, message="Expiry duration must be 1 to 60 minutes")
    private long expiresIn;

    @Override
    public String toString() {
        return "CSRFToken{" +
                "clientId='" + clientId + '\'' +
                ", token='" + token + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
