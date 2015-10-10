package edu.iastate.cs.design.spec.post;


import edu.iastate.cs.design.spec.common.Specification;

public interface IPendingSpecificationDao {

    void insertPendingSpecification(Specification specification);

    Specification removeNextPendingSpecification();
}
