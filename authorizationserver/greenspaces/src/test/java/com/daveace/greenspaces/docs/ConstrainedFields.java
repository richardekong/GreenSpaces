package com.daveace.greenspaces.docs;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.util.StringUtils;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

public class ConstrainedFields {

    public static final String CONSTRAINTS = "constraints";
    private final ConstraintDescriptions constraintDescriptions;

    public ConstrainedFields(Class<?> input) {
        this.constraintDescriptions = new ConstraintDescriptions(input);
    }

    public FieldDescriptor withPath(String path) {
        return fieldWithPath(path).attributes(key(CONSTRAINTS).value(
                StringUtils.collectionToDelimitedString(this.constraintDescriptions.descriptionsForProperty(path), ",")
        ));
    }

}
