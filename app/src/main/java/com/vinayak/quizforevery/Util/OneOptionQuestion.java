package com.vinayak.quizforevery.Util;

public class OneOptionQuestion implements Question {
    private String question;
    private String correctAnswer;
    private String[] allAnswer;

    @Override
    public String toString() {
        return "OneOptionQuestion{" +
                "question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String[] getAllAnswer() {
        return allAnswer;
    }

    public void setAllAnswer(String[] allAnswer) {
        this.allAnswer = allAnswer;
    }
}
