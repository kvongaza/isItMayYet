package com.c301t3.c301t3app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * This is the Welcome screen of the App, the User only sees it when starting the app.
 * Allows for quick login or to start searching as a free user.
 */
public class WelcomeActivity extends AppCompatActivity {

    private WelcomeActivity activity = this;
    // private final TaskPasser taskPasser = new TaskPasser();
    private TaskPasser taskPasser;
    private Intent loginIntent;
    private Intent mainMenuIntent;
    /* Here is a good site with a good tutorial for back button and info sharing between activities.
    * https://google-developer-training.gitbooks.io/android-developer-fundamentals-course-practicals/content/en/Unit%201/21_p_create_and_start_activities.html*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskPasser = new TaskPasser();

        Button mainButton = findViewById(R.id.button_GoToMainMenu);
        Button loginButton = findViewById(R.id.loginBtn);

        mainButton.setOnClickListener(new View.OnClickListener() {
            /**
             * @param v the button that is being pressed, the Start Searching button.
             *          This will bring the user to the main menu.
             */
            public void onClick(View v) {
                setResult(RESULT_OK);
                //Toast.makeText(getApplicationContext(), v.toString(),Toast.LENGTH_SHORT).show();
                mainMenuIntent = new Intent(activity, MainMenuActivity.class);
                startActivity(mainMenuIntent);

            }

        });

    }

    /**
     * @param view The Login button that brings the user right to the login page.
     */
    public void loginClick(View view) {
        //Toast.makeText(getApplicationContext(), "Login clicked",Toast.LENGTH_SHORT).show();
        if (ApplicationController.getCurrUser()==null){
            loginIntent = new Intent(activity, SimpleLoginActivity.class);
            startActivity(loginIntent);
        }

        else {
            startActivity(mainMenuIntent);
        }

    }

}

