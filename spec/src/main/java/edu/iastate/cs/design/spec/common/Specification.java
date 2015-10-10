package edu.iastate.cs.design.spec.common;

import java.util.List;

/**
 * This class will represent a specification for a specific method on a class. For now, we will represent the
 * specifications as strings. If the specifications need to be more strongly modeled later, we change them to objects.
 */
public class Specification {

    private String fullPackageName;
    private String className;
    private String methodName;
    private List<String> preconditions;
    private List<String> postconditions;

    public Specification(String fullPackageName,
                         String className,
                         String methodName,
                         List<String> preconditions,
                         List<String> postconditions) {
        this.fullPackageName = fullPackageName;
        this.className = className;
        this.methodName = methodName;
        this.preconditions = preconditions;
        this.postconditions = postconditions;
    }

    public String getFullPackageName() {
        return fullPackageName;
    }

    public String getClassName() {
        return className;
    }

    public String getMethodName() {
        return methodName;
    }

    public List<String> getPreconditions() {
        return preconditions;
    }

    public List<String> getPostconditions() {
        return postconditions;
    }
}
