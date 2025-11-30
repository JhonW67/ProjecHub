package com.ProjectHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDTO {
    private String comment;
    private Integer grade;
    private String professorName;

    public EvaluationDTO(BigDecimal grade, String comment, String name) {
    }
}
