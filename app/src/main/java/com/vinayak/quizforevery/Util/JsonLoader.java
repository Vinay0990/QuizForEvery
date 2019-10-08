package com.vinayak.quizforevery.Util;

import android.content.Context;

import com.vinayak.quizforevery.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class JsonLoader {
    private String data;

    public JsonLoader(Context context) {
        InputStream inputStream = context.getResources().openRawResource(R.raw.sample);
        data = null;
        try {
            data = readDData(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Question> readQuestion() throws JSONException {
        JSONArray object = new JSONArray(data);
        List<Question> list = new ArrayList<>();

        for (int i = 0; i < object.length() - 1; i++) {
            JSONObject obj = object.getJSONObject(i);
            OneOptionQuestion question = new OneOptionQuestion();
            question.setQuestion(obj.getString("question"));
            question.setCorrectAnswer(obj.getString("answer"));
            String[] arr = {obj.getString("opt1"),
                    obj.getString("opt2"), obj.getString("opt3"),
                    obj.getString("opt4")};
            question.setAllAnswer(arr);
            list.add(question);
        }

        return list;
    }

    private String readDData(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        String line = null;

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        }

        return stringBuilder.toString();
    }
}
