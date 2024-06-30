package com.daveace.greenspaces.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public interface ContentTestUtil {

     static <T> String createContent(T body) throws Exception {
        return new ObjectMapper()
                .configure(SerializationFeature.INDENT_OUTPUT, true)
                .writeValueAsString(body);
    }
}
