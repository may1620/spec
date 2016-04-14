package edu.iastate.cs.design.spec.analyze;

import org.eclipse.jdt.core.dom.MethodDeclaration;

import java.util.List;

public interface ExceptionDocProcessor {

    void process(String exceptionType, String documentation, List<String> paramTypes, List<String> paramNames, MethodDeclaration methodNode);
}
