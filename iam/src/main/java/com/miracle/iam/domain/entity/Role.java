package com.miracle.iam.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
    private Integer id;

    private String description;

    private String name;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;



}