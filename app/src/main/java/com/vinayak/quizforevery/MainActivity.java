package com.vinayak.quizforevery;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.vinayak.quizforevery.Util.OnlyOneOptionQuiz;

public class MainActivity extends AppCompatActivity implements OnlyOneOptionQuiz.IQuizStatus {

    private Button playBeginButton;
    private Boolean isFragmentVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playBeginButton = findViewById(R.id.begin_quiz);

        playBeginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFragmentVisible) {
                    displayFragment();
                } else {
                    removeFragment();
                }
            }
        });


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    private void displayFragment() {
        isFragmentVisible = true;
        playBeginButton.setText("Stop");

        FragmentManager manager = getSupportFragmentManager();
        OnlyOneOptionQuiz oneOptionQuiz = new OnlyOneOptionQuiz();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction().
                    remove(fragment).commit();
        }

        manager.beginTransaction()
                .replace(R.id.frame_layout, oneOptionQuiz)
                .commit();
    }

    private void removeFragment() {

        isFragmentVisible = false;
        playBeginButton.setText("Begin");
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction().
                    remove(fragment).commit();
        }
    }

    @Override
    protected void onResume() {

        playBeginButton.setVisibility(View.VISIBLE);
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void fragmentStatus(Boolean status) {
        if (!status) {
            removeFragment();
        }
    }
}
