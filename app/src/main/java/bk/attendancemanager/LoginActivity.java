package bk.attendancemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import bk.attendancemanager.HomeActivityAndFragments.HomeActivity;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void initiateLogin(View view) {
        startActivity(new Intent(this, HomeActivity.class));
    }

    public void startSignUp(View view) {
        startActivity(new Intent(this, SignupActivity.class));
    }
}
