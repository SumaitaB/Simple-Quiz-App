package com.example.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView questionTextView, scoreTextView;
    private RadioGroup radioGroup;
    private CheckBox checkBox;
    private Spinner spinner;
    private Button nextButton, quitButton;
    private int currentQuestion = 0;
    private int correctAnswers = 0;
    private int totalQuestions = 0;

    private String[] questions = {
            "What is the capital of France?",
            "Which of the following are fruits?",
            "Select the largest planet in our solar system.",
            "Choose the color of the sky on a clear day."
    };

    private String[][] answerChoices = {
            {"London", "Berlin", "Paris", "Madrid"},
            {"Apple", "Carrot", "Banana", "Potato"},
            {"Earth", "Mars", "Jupiter", "Venus"},
            {"Green", "Blue", "Yellow", "Red"}
    };

    private String[] correctAnswersArray = {"Paris", "Banana", "Jupiter", "Blue"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.questionTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        radioGroup = findViewById(R.id.radioGroup);
        checkBox = findViewById(R.id.checkBox);
        spinner = findViewById(R.id.spinner);
        nextButton = findViewById(R.id.nextButton);
        quitButton = findViewById(R.id.quitButton);

        // Initialize UI for the first question
        showQuestion(currentQuestion);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Check answers and move to the next question
                checkAnswer(currentQuestion);
                currentQuestion++;
                if (currentQuestion < questions.length) {
                    showQuestion(currentQuestion);
                } else {
                    // Display quiz completion message
                    questionTextView.setText("Quiz Completed!");
                    radioGroup.setVisibility(View.GONE);
                    checkBox.setVisibility(View.GONE);
                    spinner.setVisibility(View.GONE);
                    nextButton.setVisibility(View.GONE);
                    quitButton.setVisibility(View.GONE);
                }
            }
        });

        quitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Quit the quiz and display the final score
                questionTextView.setText("Quiz Quit!");
                radioGroup.setVisibility(View.GONE);
                checkBox.setVisibility(View.GONE);
                spinner.setVisibility(View.GONE);
                nextButton.setVisibility(View.GONE);
                quitButton.setVisibility(View.GONE);
                scoreTextView.setText("Score: " + correctAnswers + "/" + totalQuestions);
            }
        });
    }

    private void showQuestion(int questionIndex) {
        questionTextView.setText(questions[questionIndex]);

        radioGroup.clearCheck();
        radioGroup.removeAllViews();
        checkBox.setChecked(false);
        spinner.setSelection(0);

        for (String choice : answerChoices[questionIndex]) {
            if (questionIndex == 0) {
                RadioButton radioButton = new RadioButton(this);
                radioButton.setText(choice);
                radioGroup.addView(radioButton);
            }
        }

        if (questionIndex == 1) {
            checkBox.setText(answerChoices[questionIndex][0]);
            checkBox.setVisibility(View.VISIBLE);
        } else {
            checkBox.setVisibility(View.GONE);
        }

        if (questionIndex == 2) {
            ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, answerChoices[questionIndex]);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(spinnerAdapter);
            spinner.setVisibility(View.VISIBLE);
        } else {
            spinner.setVisibility(View.GONE);
        }
        totalQuestions++;
        updateScoreText();
    }

    private void checkAnswer(int questionIndex) {
        String answer = "";

        if (questionIndex == 0) {
            int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
            if (selectedRadioButtonId != -1) {
                RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                answer = selectedRadioButton.getText().toString();
            }
        }

        if (questionIndex == 1) {
            if (checkBox.isChecked()) {
                answer = "Banana";
            }
        }

        if (questionIndex == 2) {
            answer = spinner.getSelectedItem().toString();
        }

        if (questionIndex == 3) {
            if (checkBox.isChecked()) {
                answer = "Blue";
            }
        }

        if (answer.equals(correctAnswersArray[questionIndex])) {
            correctAnswers++;
        }

        updateScoreText();
    }

    private void updateScoreText() {
        scoreTextView.setText("Score: " + correctAnswers + "/" + totalQuestions);
    }
}
