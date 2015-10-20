package edu.iastate.cs.design.spec.common;

import java.util.LinkedList;
import java.util.List;

public class MockSpecificationDao implements ISpecificationDao {

    private LinkedList<Specification> pendingSpecifications;

    public MockSpecificationDao() {
        pendingSpecifications = new LinkedList<Specification>();
    }

    public void insertPendingSpecification(Specification specification) {
        pendingSpecifications.addLast(specification);
}

    public Specification removeNextPendingSpecification() {
        return pendingSpecifications.removeFirst();
    }

    public void insertFinalizedSpecification(Specification specification) {
        // TODO
    }

    public List<Specification> getFinalizedSpecifications() {
        // TODO
        return null;
    }
}
