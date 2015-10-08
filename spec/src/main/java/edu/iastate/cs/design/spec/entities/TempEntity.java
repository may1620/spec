package edu.iastate.cs.design.spec.entities;

import java.io.Serializable;
import javax.persistence.*;

/*
 * TODO This package will be used for our entities.
 * Every table in our database should have an entity
 * that is a direct Java object copy of it. This is done
 * using OpenJPA formatting.
 */

	/**
	 * The persistent class for the TempEntity database table.
	 * 
	 */
	@Entity
	@NamedQueries({
		@NamedQuery(name="TempEntity.getSpecById", query="SELECT te FROM TempEntity te WHERE te.tempId = :specId")
	})
	public class TempEntity implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		private String tempId;  /*@Id indicates a primary key
								*If the table has two or more primary keys then
								*a composite key class must be made. */
		
		private String tempName; //These are just other fields

		public String getTempId() {
			return tempId;
		}

		public void setTempId(String tempId) {
			this.tempId = tempId;
		}

		public String getTempName() {
			return tempName;
		}

		public void setTempName(String tempName) {
			this.tempName = tempName;
		}

	}
