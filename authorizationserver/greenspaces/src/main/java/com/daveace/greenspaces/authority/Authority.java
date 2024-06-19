package com.daveace.greenspaces.authority;

import com.daveace.greenspaces.role.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import static com.daveace.greenspaces.util.Constants.INVALID_AUTHORITY;
import static com.daveace.greenspaces.util.Regexp.LETTER_REGEX;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain=true)
@Entity
@Table(name="authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Pattern(regexp = LETTER_REGEX, message = INVALID_AUTHORITY)
    private String authority;

    @ManyToOne
    @JoinColumn(name="role_id")
    @JsonIgnore
    private Role role;

    @Override
    public String toString() {
        return "Authority{" +
                "id=" + id +
                ", authority='" + authority + '\'' +
                '}';
    }
}
