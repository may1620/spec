package edu.iastate.cs.design.spec.entities;

import javax.persistence.Id;
import java.io.Serializable;

public class OpenQuestionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int questionId;

    public OpenQuestionEntity(int questionId) {
       this.questionId = questionId;
    }

    public int getQuestionId() {
        return questionId;
    }
}
