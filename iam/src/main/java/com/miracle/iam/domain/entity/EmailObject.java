package com.miracle.iam.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.io.File;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class EmailObject {
    private String from;
    private String to;
    private String subject;
    private String body;
    private Date date;
    private List<File> attachFiles;
}
