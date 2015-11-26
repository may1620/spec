package edu.iastate.cs.design.spec.common;

import javax.persistence.EntityManager;

import edu.iastate.cs.design.spec.entities.Method;

public class MethodDao implements IMethodDao {
	
	private EntityManager entityManager;
	
	public MethodDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
    public void insertMethod(Method method) {
        // TODO
    }
    
    public Method getNewMethod() {
		return null;
    }
    
    public Method getInProgressMethod() {
		return null;
    	
    }
}
