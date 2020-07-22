package com.ngoding.githubuserapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ngoding.githubuserapp.R;

public class MainActivity extends AppCompatActivity {

    Animation firstAnim, secondAnim;
    ImageView imgGithubMark, imgGithubLogo;
    TextView tvGithubLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        firstAnim = AnimationUtils.loadAnimation(this, R.anim.first_anim);
        secondAnim = AnimationUtils.loadAnimation(this, R.anim.second_anim);

        imgGithubLogo = findViewById(R.id.img_github_logo);
        tvGithubLogo = findViewById(R.id.tv_github_logo);
        imgGithubMark = findViewById(R.id.img_github_mark);
        imgGithubMark.setAnimation(firstAnim);

        int TIME_DELAY_ANIM = 3000;
        imgGithubMark.postDelayed(new Runnable() {
            @Override
            public void run() {
                imgGithubMark.setVisibility(View.INVISIBLE);
                imgGithubLogo.setAnimation(secondAnim);
                tvGithubLogo.setAnimation(secondAnim);
                imgGithubLogo.setVisibility(View.VISIBLE);
                tvGithubLogo.setVisibility(View.VISIBLE);
            }
        }, TIME_DELAY_ANIM);

        int SPLASH_SCREEN = 5000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }
}