package edu.iastate.cs.design.spec.common;

import java.util.List;

import edu.iastate.cs.design.spec.entities.Question;

public interface IOpenQuestionsDao {

    void insertOpenQuestion(int questionId);

    // returns list of question ids
    List<Question> getOpenQuestions();
}
