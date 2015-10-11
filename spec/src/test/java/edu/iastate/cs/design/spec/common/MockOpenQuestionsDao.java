package edu.iastate.cs.design.spec.common;

import java.util.ArrayList;
import java.util.List;

public class MockOpenQuestionsDao implements IOpenQuestionsDao {

    List<Integer> openQuestions;

    public MockOpenQuestionsDao() {
        this.openQuestions = new ArrayList<Integer>();
    }

    public void insertOpenQuestion(int questionId) {
        openQuestions.add(questionId);
    }

    public List<Integer> getOpenQuestions() {
        return new ArrayList<Integer>(openQuestions);
    }
}
