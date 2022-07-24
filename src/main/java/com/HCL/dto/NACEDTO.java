package com.HCL.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NACEDTO {


    @JsonProperty("Order")
    private Long order;

    @JsonProperty("Level")
    private Integer level;

    @JsonProperty("Code")
    private String code;

    @JsonProperty("Parent")
    private String parent;

    @JsonProperty("Description")
    private String description;

    @JsonProperty("This item includes")
    private String itemIncludes;

    @JsonProperty("This item also includes")
    private String itemAlsoIncludes;

    @JsonProperty("Rulings")
    private String rulings;

    @JsonProperty("The item excludes")
    private String itemExcludes;

    @JsonProperty("Reference to ISIC Rev. 4")
    private String referenceToISICRev;


}
