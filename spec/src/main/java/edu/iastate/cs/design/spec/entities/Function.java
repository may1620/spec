package edu.iastate.cs.design.spec.entities;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: Function
 *
 */
@Entity

public class Function implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	private String functionId;
	
	private String fullClasspath;

	public String getFunctionId() {
		return functionId;
	}

	public void setFunctionId(String functionId) {
		this.functionId = functionId;
	}

	public String getFullClasspath() {
		return fullClasspath;
	}

	public void setFullClasspath(String fullClasspath) {
		this.fullClasspath = fullClasspath;
	}
	
	
   
}
