package edu.iastate.cs.design.spec.post;

import edu.iastate.cs.design.spec.common.Specification;

import java.util.LinkedList;

public class MockPendingSpecificationDao implements IPendingSpecificationDao {

    private LinkedList<Specification> pendingSpecifications;

    public MockPendingSpecificationDao() {
        pendingSpecifications = new LinkedList<Specification>();
    }

    public void insertPendingSpecification(Specification specification) {
        pendingSpecifications.addLast(specification);
}

    public Specification removeNextPendingSpecification() {
        return pendingSpecifications.removeFirst();
    }
}
