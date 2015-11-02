package edu.iastate.cs.design.spec.common;

import edu.iastate.cs.design.spec.entities.OpenQuestionEntity;
import edu.iastate.cs.design.spec.entities.Question;
import edu.iastate.cs.design.spec.entities.TestEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class OpenQuestionsDao implements  IOpenQuestionsDao {

    private EntityManager entityManager;

    public OpenQuestionsDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void insertOpenQuestion(int questionId) {
    	entityManager.getTransaction().begin();
    	Question question = new Question();
    	question.setQuestionId(questionId);
    	question.setOpen(true);
        entityManager.persist(question);
        entityManager.getTransaction().commit();
    }

    public List<Question> getOpenQuestions() {
    	TypedQuery<Question> query = entityManager.createNamedQuery("Question.getOpenQuestions", Question.class);
    	return query.getResultList();
    }

}
