package edu.iastate.cs.design.spec.post;

import edu.iastate.cs.design.spec.common.Specification;

import javax.persistence.EntityManager;

public class PendingSpecificationDao implements IPendingSpecificationDao{

    private EntityManager entityManager;

    public PendingSpecificationDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void insertPendingSpecification(Specification specification) {
        // TODO
    }

    public Specification removeNextPendingSpecification() {
        // TODO
        return null;
    }
}
