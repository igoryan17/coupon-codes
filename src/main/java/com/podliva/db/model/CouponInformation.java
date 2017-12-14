package com.podliva.db.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@Builder
public class CouponInformation {
    @Id
    private Long id;
    @Indexed(unique = true)
    private String code;
    private String description;
    @Indexed
    private Integer price;
    @Indexed
    private List<String> locations;
}
