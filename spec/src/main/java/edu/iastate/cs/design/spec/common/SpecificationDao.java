package edu.iastate.cs.design.spec.common;

import javax.persistence.EntityManager;
import java.util.List;

public class SpecificationDao implements ISpecificationDao {

    private EntityManager entityManager;

    public SpecificationDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void insertPendingSpecification(Specification specification) {
        // TODO
    }

    public Specification removeNextPendingSpecification() {
        // TODO
        return null;
    }

    public void insertFinalizedSpecification(Specification specification) {
        // TODO
    }

    public List<Specification> getFinalizedSpecifications() {
        // TODO
        return null;
    }
}
