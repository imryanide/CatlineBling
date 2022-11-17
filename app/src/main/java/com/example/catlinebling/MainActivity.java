package com.example.catlinebling;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    boolean isMeowing = false;
    ImageSwitcher imageSwitcher;
    Button findButton;
    TextView headText;
    ImageView sillyCat;
    ImageView emojiView;
    VideoView explosionView;
    MediaPlayer hotlineMP;
    MediaPlayer meowMP;
    private static final String explosion = "explosion";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Check if action bar is hidden, if it is not, hide it.
        if(getSupportActionBar()!=null){
            this.getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_main);


        // View Initializations
        imageSwitcher = (ImageSwitcher) findViewById(R.id.catImageSwitcher);
        findButton = (Button) findViewById(R.id.findOut);
        headText = (TextView) findViewById(R.id.headingcat);
        sillyCat = (ImageView) findViewById(R.id.sillyCat);
        emojiView = (ImageView) findViewById(R.id.emoji);
        explosionView = (VideoView) findViewById(R.id.explosion);


        // Create animations for Emoji
        RotateAnimation rotanim = new RotateAnimation(0.0f,360.0f, Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        rotanim.setInterpolator(new LinearInterpolator());
        rotanim.setRepeatCount(Animation.INFINITE);
        rotanim.setDuration(1000);

        ObjectAnimator oa1 = ObjectAnimator.ofFloat(emojiView, "scaleX", 1f, 0f);
        ObjectAnimator oa2 = ObjectAnimator.ofFloat(emojiView, "scaleX", 0f, 1f);
        oa1.setInterpolator(new DecelerateInterpolator());
        oa2.setInterpolator(new AccelerateDecelerateInterpolator());
        oa1.setDuration(1500);
        oa2.setDuration(1500);
        oa1.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                oa2.start();
            }
        });
        oa2.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                oa1.start();
            }
        });

        // Create animations for Silly Cat
        Animation surpanim = AnimationUtils.loadAnimation(this,R.anim.wiggle);
        surpanim.setRepeatCount(Animation.INFINITE);
        surpanim.setDuration(1500);



        // ImageSwitcher factory method
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                return new ImageView(getApplicationContext());
            }
        });

        // Set source and animations for imageSwitcher
        imageSwitcher.setImageResource(R.drawable.catserious);
        imageSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
        imageSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));

        // Initialize MediaPlayers
        hotlineMP = MediaPlayer.create(this, R.raw.hotlinebling);
        meowMP = MediaPlayer.create(this, R.raw.catmeowing);

        // Initialize VideoPlayer
        explosionView.setVideoURI(Uri.parse("android.resource://com.example.catlinebling/"+R.raw.tallexplosion));



        // Button Listener
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isMeowing)
                {
                    // Start Video of Explosion
                    findButton.setVisibility(View.INVISIBLE);
                    explosionView.setVisibility(View.VISIBLE);
                    explosionView.start();
                    explosionView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            explosionView.setVisibility(
                                    View.INVISIBLE

                            );
                            findButton.setVisibility(View.VISIBLE);
                            headText.setText(R.string.bingchilling);
                            headText.setAllCaps(false);

                            sillyCat.setVisibility(View.VISIBLE);
                            emojiView.setVisibility(View.VISIBLE);

                            imageSwitcher.setImageResource(R.drawable.catmeowing);
                            findButton.setText(R.string.hellyea);

                            hotlineMP.setVolume(80.0F, 80.0F);
                            hotlineMP.start();
                            hotlineMP.setLooping(true);

                            meowMP.setVolume(10.0F,10.0F);
                            meowMP.start();

                            oa1.start();
                            sillyCat.startAnimation(surpanim);

                            isMeowing  = true;
                        }
                    });


                }
                else{
                    explosionView.stopPlayback();
                    explosionView.setVisibility(View.INVISIBLE);

                    headText.setText(R.string.what_da_cat_doin);
                    headText.setAllCaps(false);

                    emojiView.clearAnimation();
                    sillyCat.clearAnimation();

                    sillyCat.setVisibility(View.INVISIBLE);
                    emojiView.setVisibility(View.INVISIBLE);

                    imageSwitcher.setImageResource(R.drawable.catserious);
                    findButton.setText(R.string.find_out);

                    hotlineMP.pause();
                    meowMP.pause();

                    isMeowing = false;


                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        hotlineMP.release();
        meowMP.release();
        explosionView.stopPlayback();
    }
}