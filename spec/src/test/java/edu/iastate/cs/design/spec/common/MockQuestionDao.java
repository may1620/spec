package edu.iastate.cs.design.spec.common;

import edu.iastate.cs.design.spec.entities.Question;

import java.util.ArrayList;
import java.util.List;

public class MockQuestionDao implements IQuestionDao {

    List<Question> questions;

    public MockQuestionDao() {
        this.questions = new ArrayList<Question>();
    }

    public void insertQuestion(int questionId) {
        Question question = new Question();
        question.setQuestionId(questionId);
        question.setOpen(true);
        questions.add(question);
    }

    public List<Question> getAllQuestions() {
        return new ArrayList<Question>(questions);
    }
}
