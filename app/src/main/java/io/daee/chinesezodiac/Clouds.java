package io.daee.chinesezodiac;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.media.Image;
import android.widget.ImageView;

public class Clouds {

    private ImageView imageView;
    private float levitationY;
    private int duration;

    public Clouds (ImageView imageView, int duration, float levitationY) {
        this.imageView = imageView;
        this.duration = duration;
        this.levitationY = levitationY;
    }

    public void animate() {
        ObjectAnimator cloudAnimator = ObjectAnimator.ofFloat(this.imageView, "translationY", 0f, levitationY);
        cloudAnimator.setDuration(this.duration);
        cloudAnimator.setRepeatCount(ValueAnimator.INFINITE);
        cloudAnimator.setRepeatMode(ValueAnimator.REVERSE);
        cloudAnimator.start();
    }

}
