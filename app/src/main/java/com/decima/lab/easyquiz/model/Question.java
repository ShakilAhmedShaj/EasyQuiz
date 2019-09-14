package com.decima.lab.easyquiz.model;

/**
 * Created by Shakil Ahmed Shaj on 14-Sep-19.
 * shakilahmedshaj@gmail.com
 */
public class Question {

    private int questionId;
    private boolean answerTrue;

    public Question(int questionId, boolean answerTrue) {
        this.questionId = questionId;
        this.answerTrue = answerTrue;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }
}
