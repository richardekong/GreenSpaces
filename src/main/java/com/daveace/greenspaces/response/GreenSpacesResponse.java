package com.daveace.greenspaces.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain=true)
public class GreenSpacesResponse {

    private int status;
    private String message;
    private long time;

}
