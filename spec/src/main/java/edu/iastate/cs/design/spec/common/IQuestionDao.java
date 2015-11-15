package edu.iastate.cs.design.spec.common;

import java.util.List;

import edu.iastate.cs.design.spec.entities.Question;

public interface IQuestionDao {

    void insertQuestion(int questionId);

    List<Question> getAllQuestions();
}
