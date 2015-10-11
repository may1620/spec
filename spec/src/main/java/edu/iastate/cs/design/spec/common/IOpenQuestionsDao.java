package edu.iastate.cs.design.spec.common;

import java.util.List;

public interface IOpenQuestionsDao {

    void insertOpenQuestion(int questionId);

    // returns list of question ids
    List<Integer> getOpenQuestions();
}
