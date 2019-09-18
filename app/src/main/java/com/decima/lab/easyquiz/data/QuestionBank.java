package com.decima.lab.easyquiz.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.decima.lab.easyquiz.controller.AppController;
import com.decima.lab.easyquiz.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import static com.decima.lab.easyquiz.controller.AppController.TAG;

/**
 * Created by Shakil Ahmed Shaj on 16-Sep-19.
 * shakilahmedshaj@gmail.com
 */

public class QuestionBank {

    ArrayList<Question> questionArrayList = new ArrayList<>();
    private String url = "https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json";

    public List<Question> getQuestions(final AnswerListAsyncResponse callBack) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "onResponse: " + response);

                        for (int i = 0; i < response.length(); i++) {


                            try {

                                Question question = new Question();
                                question.setAnswer(response.getJSONArray(i).get(0).toString());
                                question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));

//                                adding questions object to arrayList

                                questionArrayList.add(question);


//                                Log.d("questions", "Q " + i + " " + response.getJSONArray(i).get(0));
//                                Log.d("answer", "A " + i + " " + response.getJSONArray(i).getBoolean(1));


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        if (null != callBack) callBack.processFinished(questionArrayList);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);

        return questionArrayList;

    }
}
