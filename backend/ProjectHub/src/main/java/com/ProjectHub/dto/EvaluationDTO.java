package com.ProjectHub.dto;

import java.math.BigDecimal;

public class EvaluationDTO {
    private String comment;
    private Integer grade;
    private String professorName;

    public EvaluationDTO() {}

    public EvaluationDTO(String comment, Integer grade, String professorName) {
        this.comment = comment;
        this.grade = grade;
        this.professorName = professorName;
    }

    public EvaluationDTO(String comment, BigDecimal grade, String name) {
    }

    public String getComment() { return comment; }
    public void setComment(String comment) { this.comment = comment; }

    public Integer getGrade() { return grade; }
    public void setGrade(Integer grade) { this.grade = grade; }

    public String getProfessorName() { return professorName; }
    public void setProfessorName(String professorName) { this.professorName = professorName; }
}
