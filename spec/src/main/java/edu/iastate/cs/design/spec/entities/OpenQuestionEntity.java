package edu.iastate.cs.design.spec.entities;

import javax.persistence.Id;
import java.io.Serializable;

public class OpenQuestionEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private int questionId;
    
    private String functionId; //Ties a certain question to a specific function

    public OpenQuestionEntity(int questionId) {
       this.questionId = questionId;
    }

    public int getQuestionId() {
        return questionId;
    }

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}
}
