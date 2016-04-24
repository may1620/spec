package edu.iastate.cs.design.spec.analysis;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.List;

public interface IExceptionDocProcessor {

    void process(String exceptionType, String documentation, List<String> paramTypes, List<String> paramNames, MethodDeclaration methodNode);
}
