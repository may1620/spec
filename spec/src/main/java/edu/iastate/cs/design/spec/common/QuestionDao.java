package edu.iastate.cs.design.spec.common;

import edu.iastate.cs.design.spec.entities.Question;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import java.util.List;

public class QuestionDao implements IQuestionDao {

    private EntityManager entityManager;

    public QuestionDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void insertQuestion(int questionId) {
    	entityManager.getTransaction().begin();
    	Question question = new Question();
    	question.setQuestionId(questionId);
    	question.setOpen(true);
        entityManager.persist(question);
        entityManager.getTransaction().commit();
    }

    public List<Question> getAllQuestions() {
    	TypedQuery<Question> query = entityManager.createNamedQuery("Question.getAllQuestions", Question.class);
    	return query.getResultList();
    }

}
