package com.vinayak.quizforevery.Util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.vinayak.quizforevery.R;

import org.json.JSONException;

import java.util.List;

public class OnlyOneOptionQuiz extends Fragment {

    private LinearLayout radioGroup;
    private RadioButton button1, button2, button3, button4;
    private TextView questionStat;
    private TextView questionText;
    private List<Question> list;

    private OneOptionQuestion question;
    private int currentQuestion, score;

    private Activity activity;
    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Button button = (Button) v;
            disableFrame();
            String answerSelected = (String) button.getText();

            String correctAnswer = question.getCorrectAnswer();
            if (answerSelected.equals(correctAnswer)) {
                score++;
            }
            currentQuestion++;
            if (currentQuestion != list.size())
                setQuestion(currentQuestion);
            else {
                finishStat();
            }


        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity = getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.button_oo_quiz, container, false);
        objectInstantiate(view);
        getdataQuestion();

        resetQuiz();
        return view;
    }

    private void resetQuiz() {
        currentQuestion = 0;
        score = 0;
        setQuestion(currentQuestion);
    }

    private void finishStat() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        alertDialogBuilder.setMessage("You scored " + score + " of " + list.size() + ",  You wanted to try again");
        alertDialogBuilder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(getContext(), "Yes", Toast.LENGTH_SHORT).show();
                resetQuiz();
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(), "No", Toast.LENGTH_SHORT).show();
                finishFragment();
                ((IQuizStatus) activity).fragmentStatus(false);
            }
        });
        alertDialogBuilder.setCancelable(false);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

    private void disableFrame() {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            radioButton.setOnClickListener(null);
        }
    }

    private void finishFragment() {
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            radioButton.setOnClickListener(null);
        }
        radioGroup = null;
        button1 = null;
        button2 = null;
        button3 = null;
        button4 = null;
        questionStat = null;
        questionText = null;
    }

    private void setQuestion(int stat) {

        String status = "Question " + stat + " of " + list.size();

        questionStat.setText(status);
        question = (OneOptionQuestion) list.get(stat);
        questionText.setText(question.getQuestion());

        String[] options = question.getAllAnswer();
        button1.setText(options[0]);
        button2.setText(options[1]);
        button3.setText(options[2]);
        button4.setText(options[3]);

        button1.setChecked(false);
        button2.setChecked(false);
        button3.setChecked(false);
        button4.setChecked(false);

        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
            radioButton.setOnClickListener(buttonClickListener);
        }
    }

    private void getdataQuestion() {
        JsonLoader jsonLoader = new JsonLoader(getContext());
        try {
            list = jsonLoader.readQuestion();
            for (Question q : list) {
                Log.d("question", q.toString());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void objectInstantiate(View view) {
        radioGroup = view.findViewById(R.id.optionLayout);
        button1 = view.findViewById(R.id.optionOne);
        button2 = view.findViewById(R.id.optionTwo);
        button3 = view.findViewById(R.id.optionThree);
        button4 = view.findViewById(R.id.optionFour);
        questionStat = view.findViewById(R.id.questionStatus);
        questionText = view.findViewById(R.id.questionText);
    }

    public interface IQuizStatus {
        void fragmentStatus(Boolean status);
    }
}
