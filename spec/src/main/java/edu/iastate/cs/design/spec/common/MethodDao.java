package edu.iastate.cs.design.spec.common;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import edu.iastate.cs.design.spec.entities.Method;
import edu.iastate.cs.design.spec.entities.Question;

public class MethodDao implements IMethodDao {
	
	private EntityManager entityManager;
	
	public MethodDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
    public void insertMethod(Method method) {
    	entityManager.getTransaction().begin();
        entityManager.persist(method);
        entityManager.getTransaction().commit();
    }
    
    public Method getNewMethod() {
    	TypedQuery<Method> query = entityManager.createNamedQuery("Method.getNew", Method.class);
    	return query.getResultList().get(0);
    }
    
    public Method getInProgressMethod() {
    	TypedQuery<Method> query = entityManager.createNamedQuery("Method.getInProgress", Method.class);
    	return query.getResultList().get(0);
    }
}
