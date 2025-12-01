package com.ProjectHub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class EvaluationDTO {
    private Integer grade;      // ou BigDecimal, se você preferir
    private String comment;
    private String professorName;

}
