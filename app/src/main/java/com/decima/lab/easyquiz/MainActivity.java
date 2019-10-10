package com.decima.lab.easyquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.decima.lab.easyquiz.data.AnswerListAsyncResponse;
import com.decima.lab.easyquiz.data.QuestionBank;
import com.decima.lab.easyquiz.model.Question;
import com.decima.lab.easyquiz.model.Score;
import com.decima.lab.easyquiz.util.Prefs;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView questionTextview;
    private TextView questionCounterTextview;
    private Button trueButton;
    private Button falseButton;
    private ImageButton nextButton;
    private ImageButton prevButton;

    private TextView highestScoreTextView;

    private int currentQuestionIndex = 0;
    private List<Question> questionList;

    private TextView scoreTextView;
    private Button shareButton;

    private int scoreCounter = 0;
    private Score score;

    private Prefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        score = new Score();
        prefs = new Prefs(MainActivity.this);

        nextButton = findViewById(R.id.next_button);
        prevButton = findViewById(R.id.prev_button);
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        questionCounterTextview = findViewById(R.id.counter_text);
        questionTextview = findViewById(R.id.question_textview);

        scoreTextView = findViewById(R.id.score_text);
        shareButton = findViewById(R.id.shareButton);

        highestScoreTextView = findViewById(R.id.highest_score);

        scoreTextView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore())));

        highestScoreTextView.setText(MessageFormat.format(" Highest Score: {0}", String.valueOf(prefs.getHighScore())));


        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {

                questionCounterTextview.setText(currentQuestionIndex + " / " + questionArrayList.size());

                Log.d("Inside", "processFinished: " + questionArrayList);

                questionTextview.setText(questionArrayList.get(currentQuestionIndex).getAnswer());

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.prev_button:
                if (currentQuestionIndex > 0) {
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                    updateQuestion();
                }
                break;
            case R.id.next_button:
                goNext();
                updateQuestion();
                break;
            case R.id.true_button:
                checkAnswer(true);
                updateQuestion();
                break;
            case R.id.false_button:
                checkAnswer(false);
                updateQuestion();
                break;
        }

    }

    private void checkAnswer(boolean userChooseCorrect) {
        boolean answerIsTrue = questionList.get(currentQuestionIndex).isAnswerTrue();

        int toastMessageId = 0;
        if (userChooseCorrect == answerIsTrue) {

            addPoints();

            fadeView();
            toastMessageId = R.string.correct_answer;
        } else {
            shakeAnimation();
            removePoints();
            //Log.d("Find Score", "removePoints: " + score.getScore());
            toastMessageId = R.string.wrong_answer;
        }
        Toast.makeText(MainActivity.this, toastMessageId,
                Toast.LENGTH_SHORT)
                .show();


    }

    private void addPoints() {

        scoreCounter += 100;
        score.setScore(scoreCounter);
        Log.d("Find Score", "addPoints: " + score.getScore());

        scoreTextView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore())));


    }

    private void removePoints() {

        scoreCounter -= 100;
        if (scoreCounter > 0) {
            score.setScore(scoreCounter);
            scoreTextView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore())));
        } else {
            scoreCounter = 0;
            score.setScore(scoreCounter);
            scoreTextView.setText(MessageFormat.format("Current Score: {0}", String.valueOf(score.getScore())));
            //Log.d("Score Bad", "deductPoints: " + score.getScore());
        }


        // Log.d("Score:", "addPoints: " + score.getScore());


    }

    private void fadeView() {

        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);

        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(alphaAnimation);

        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                goNext();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    private void goNext() {

        currentQuestionIndex = (currentQuestionIndex + 1) % questionList.size();
        updateQuestion();


    }

    private void shakeAnimation() {

        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.shake_animation);
        final CardView cardView = findViewById(R.id.cardView);
        cardView.setAnimation(shake);

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                goNext();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }

    private void updateQuestion() {

        String question = questionList.get(currentQuestionIndex).getAnswer();
        questionTextview.setText(question);
        questionCounterTextview.setText(currentQuestionIndex + " / " + questionList.size());
    }

    @Override
    protected void onPause() {
        prefs.saveHighScore(score.getScore());
        //prefs.setState(currentQuestionIndex);

        super.onPause();
    }
}

