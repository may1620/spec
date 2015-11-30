package edu.iastate.cs.design.spec.common;


import edu.iastate.cs.design.spec.entities.Type;

import javax.persistence.EntityManager;

public class TypeDao implements ITypeDao {

    private EntityManager entityManager;

    public TypeDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void insertType(Type type) {
        // TODO
    }
}
