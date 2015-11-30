package edu.iastate.cs.design.spec.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import java.io.Serializable;

@Entity
@NamedQueries({
	@NamedQuery(name="Method.getAll", query="SELECT m FROM Method m"),
	
	//TODO we likely need to add an inProgress field here to let us know if a method is posted yet OR link it with Question entity
	@NamedQuery(name="Method.getNew", query="SELECT m.methodId FROM Method m WHERE m.inProgress = false"),
			
	@NamedQuery(name="Method.getInProgress", query="SELECT m.methodId FROM Method m WHERE m.inProgress = true")
})
public class Method implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="METHOD_ID")
    private int methodId;

    @Column(name="TYPE_ID")
    private int typeId;

    @Column(name="NAME")
    private String name;

    @Column(name="SIGNATURE")
    private String signature;

    @Column(name="SEQUENCE_FILE")
    private String sequenceFile;

    @Column(name="SEQUENCE_FILE_POSITION")
    private long sequenceFilePosition;

    public Method(int methodId, int typeId, String name, String signature, String sequenceFile, long sequenceFilePosition) {
        this.methodId = methodId;
        this.typeId = typeId;
        this.name = name;
        this.signature = signature;
        this.sequenceFile = sequenceFile;
        this.sequenceFilePosition = sequenceFilePosition;
    }

    // TODO insert by figuring out class info to get class id
    public Method(String name, String signature, String sequenceFile, long sequenceFilePosition) {
        this(0, 0, name, signature, sequenceFile, sequenceFilePosition);
    }
    
    public Method() {
    	
    }

    public int getMethodId() {
        return methodId;
    }

    public int getTypeId() {
        return typeId;
    }

    public String getName() {
        return name;
    }

    public String getSignature() {
        return signature;
    }

    public String getSequenceFile() {
        return sequenceFile;
    }

    public long getSequenceFilePosition() {
        return sequenceFilePosition;
    }

    @Override
    public String toString() {
        return "Method{" +
                "methodId=" + methodId +
                ", typeId=" + typeId +
                ", name='" + name + '\'' +
                ", signature='" + signature + '\'' +
                ", sequenceFile='" + sequenceFile + '\'' +
                ", sequenceFilePosition=" + sequenceFilePosition +
                '}';
    }
}
