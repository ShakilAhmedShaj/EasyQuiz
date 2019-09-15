package com.decima.lab.easyquiz.model;

/**
 * Created by Shakil Ahmed Shaj on 14-Sep-19.
 * shakilahmedshaj@gmail.com
 */
public class Question {

    private String answer;
    private boolean answerTrue;

    public Question() {

    }

    public Question(String answer, boolean answerTrue) {
        this.answer = answer;
        this.answerTrue = answerTrue;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    @Override
    public String toString() {
        return "Question{" +
                "answer='" + answer + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }
}
