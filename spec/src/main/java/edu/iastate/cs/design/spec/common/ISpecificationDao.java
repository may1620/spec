package edu.iastate.cs.design.spec.common;


import java.util.List;

public interface ISpecificationDao {

    void insertPendingSpecification(Specification specification);

    Specification removeNextPendingSpecification();

    void insertFinalizedSpecification(Specification specification);

    List<Specification> getFinalizedSpecifications();
}
