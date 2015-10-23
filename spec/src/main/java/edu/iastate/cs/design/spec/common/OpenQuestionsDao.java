package edu.iastate.cs.design.spec.common;

import edu.iastate.cs.design.spec.entities.OpenQuestionEntity;

import javax.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;

public class OpenQuestionsDao implements  IOpenQuestionsDao {

    private EntityManager entityManager;

    public OpenQuestionsDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void insertOpenQuestion(int questionId) {
        // TODO
    }

    public List<Integer> getOpenQuestions() {
        return new ArrayList<Integer>();
    }
}
