package edu.iastate.cs.design.spec.entities;

import java.io.Serializable;
import javax.persistence.*;

	/**
	 * The persistent class for the Question database table.
	 * 
	 */
	@Entity
	@NamedQueries({
		@NamedQuery(name="Question.getAll", query="SELECT q FROM Question q"),
		
		@NamedQuery(name="Question.getOpenQuestions", query="SELECT q.questionId FROM Question q WHERE "
															+ "q.isOpen == true")
	})
	public class Question implements Serializable {
		private static final long serialVersionUID = 1L;

		@Id
		@Column(name="QUESTION_ID")
		private int questionId;
		
		@Column(name="IS_OPEN")
		private boolean isOpen;

		public int getQuestionId() {
			return questionId;
		}

		public void setQuestionId(int questionId) {
			this.questionId = questionId;
		}

		public boolean isOpen() {
			return isOpen;
		}

		public void setOpen(boolean isOpen) {
			this.isOpen = isOpen;
		}

	}
