package com.example.catlinebling;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    boolean isMeowing = false;
    ImageSwitcher imageSwitcher;
    Button findButton;
    TextView headText;
    ImageView sillyCat;
    ImageView emojiView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getSupportActionBar()!=null){
            this.getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_main);



        imageSwitcher = (ImageSwitcher) findViewById(R.id.catImageSwitcher);
        findButton = (Button) findViewById(R.id.findOut);
        headText = (TextView) findViewById(R.id.headingcat);
        sillyCat = (ImageView) findViewById(R.id.sillyCat);
        emojiView = (ImageView) findViewById(R.id.emoji);

       

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new ImageView(getApplicationContext());
            }
        });

        imageSwitcher.setImageResource(R.drawable.catserious);
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

        MediaPlayer hotlineMP = MediaPlayer.create(this, R.raw.hotlinebling);
        MediaPlayer meowMP = MediaPlayer.create(this, R.raw.catmeowing);


        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isMeowing)
                {
                    headText.setText(R.string.bingchilling);
                    headText.setAllCaps(false);
                    sillyCat.setVisibility(View.VISIBLE);
                    emojiView.setVisibility(View.VISIBLE);
                    imageSwitcher.setImageResource(R.drawable.catmeowing);
                    findButton.setText(R.string.hellyea);

                    hotlineMP.setVolume(25.0F, 25.0F);
                    hotlineMP.start();
                    hotlineMP.setLooping(true);

                    meowMP.setVolume(80.0F,80.0F);
                    meowMP.start();
                    meowMP.setLooping(true);


                    isMeowing  = true;
                }
                else{
                    headText.setText(R.string.what_da_cat_doin);
                    headText.setAllCaps(false);
                    sillyCat.setVisibility(View.INVISIBLE);
                    emojiView.setVisibility(View.INVISIBLE);
                    imageSwitcher.setImageResource(R.drawable.catserious);
                    findButton.setText(R.string.what_da_cat_doin);

                    hotlineMP.pause();
                    meowMP.pause();

                    isMeowing = false;


                }
            }
        });
    }
}