package com.decima.lab.easyquiz.data;

import com.decima.lab.easyquiz.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
