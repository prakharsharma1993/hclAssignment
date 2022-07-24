package com.HCL.entity;


import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "nace")
public class NACE {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private Long orderId;

    private Integer level;

    private String code;

    private String parentId;

    private String description;

    @Lob
    private String itemIncludes;

    @Lob
    private String itemAlsoIncludes;

    @Lob
    private String rulings;

    @Lob
    private String  itemExcludes;

    private String referenceToISICRev;


}
