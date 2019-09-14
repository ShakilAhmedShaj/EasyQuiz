package com.decima.lab.easyquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.decima.lab.easyquiz.model.Question;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button falseButton;
    private Button trueButton;
    private ImageButton nextButton;
    private ImageButton prevButton;
    private TextView questionTextView;

    private int currentQuestionIndex = 0;

    //questions

    private Question[] questionBank = new Question[]{

            new Question(R.string.q1, true),
            new Question(R.string.q2, true),
            new Question(R.string.q3, false),
            new Question(R.string.q4, false)

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Question question = new Question(R.string.q1, true);

        falseButton = findViewById(R.id.false_button);
        trueButton = findViewById(R.id.true_button);
        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        questionTextView = findViewById(R.id.answer_text_view);

        falseButton.setOnClickListener(this); //register our buttons to listen to click events
        trueButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.false_button:
                checkAnswer(false);
                break;

            case R.id.true_button:
                checkAnswer(true);
                break;

            case R.id.next_button:
                currentQuestionIndex = (currentQuestionIndex + 1) % questionBank.length;
                updateQuestion();
                break;


            case R.id.prev_button:
                currentQuestionIndex = currentQuestionIndex > 0 ? --currentQuestionIndex : 0;
                questionTextView.setText(questionBank[currentQuestionIndex].getQuestionId());
                break;

        }
    }

    private void updateQuestion() {

        questionTextView.setText(questionBank[currentQuestionIndex].getQuestionId());
    }

    private void checkAnswer(boolean userChoice) {

        boolean answerIsTrue = questionBank[currentQuestionIndex].isAnswerTrue();
        int toastMessageId = 0;

        if (userChoice == answerIsTrue) {
            toastMessageId = R.string.right_ans_text;
        } else {
            toastMessageId = R.string.wrong_ans_text;
        }

        Toast.makeText(this, toastMessageId, Toast.LENGTH_SHORT).show();
    }
}

