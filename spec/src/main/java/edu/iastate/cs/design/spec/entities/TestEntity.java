package edu.iastate.cs.design.spec.entities;

import java.io.Serializable;
import javax.persistence.*;

	/**
	 * The persistent class for the TestEntity database table.
	 * 
	 */
	@Entity
	@NamedQueries({
		@NamedQuery(name="TestEntity.getAll", query="SELECT t FROM TestEntity t")
	})
	public class TestEntity implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@Column(name="TEST_ID")
		private int testId;  /*@Id indicates a primary key
								*If the table has two or more primary keys then
								*a composite key class must be made. */
		
		@Column(name="TEST_NAME")
		private String testName; //These are just other fields

		public int getTestId() {
			return testId;
		}

		public void setTestId(int testId) {
			this.testId = testId;
		}

		public String getTestName() {
			return testName;
		}

		public void setTestName(String testName) {
			this.testName = testName;
		}

	}
