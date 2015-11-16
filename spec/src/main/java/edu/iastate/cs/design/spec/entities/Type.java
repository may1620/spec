package edu.iastate.cs.design.spec.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Type implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="TYPE_ID")
    private int typeId;

    @Column(name="NAME")
    private String name;

    @Column(name="PACKAGE")
    private String fullyQualifiedPackage;

    public Type(int typeId, String name, String fullyQualifiedPackage) {
        this.typeId = typeId;
        this.name = name;
        this.fullyQualifiedPackage = fullyQualifiedPackage;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getName() {
        return name;
    }

    public String getFullyQualifiedPackage() {
        return fullyQualifiedPackage;
    }
}
