package com.daveace.greenspaces.entity;

import com.daveace.greenspaces.util.IDGenerator;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

@Setter
@Getter
@ToString
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@Accessors(chain=true)
public class BaseEntity<T extends BaseEntity<T>>  {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name="UUID", type = IDGenerator.class)
    protected String id;

    @SuppressWarnings("unchecked")
    public T setId(String id){
        this.id = id;
        return (T) this;
    }

}

