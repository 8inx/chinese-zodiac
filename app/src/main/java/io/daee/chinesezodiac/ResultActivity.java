package io.daee.chinesezodiac;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class ResultActivity extends AppCompatActivity {

    private int selectedYear;
    private String[] listZodiac;
    private int imageDrawableId;
    private String zodiac;
    private String birthYears;
    private String personalityTraits;
    private String element;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        this.listZodiac = new String[]{"rat", "ox", "tiger", "rabbit", "dragon", "snake", "horse", "sheep", "monkey", "rooster", "dog", "pig"};

        Intent intent = getIntent();
        Calendar calendar = Calendar.getInstance();
        this.selectedYear = intent.getIntExtra(MainActivity.EXTRA_STRING, calendar.get(Calendar.YEAR));

        this.zodiac = getZodiacSign(this.selectedYear);
        this.element = getElement(this.selectedYear);

        generateResults(this.zodiac);
        displayResults();

        animatePortal();
        animateClouds();
    }

    private void generateResults(String zodiac) {

        switch (zodiac) {
            case "rat":
                setResults(R.drawable.zodiac_rat, getString(R.string.personality_traits_rat));
                break;
            case "ox":
                setResults(R.drawable.zodiac_ox, getString(R.string.personality_traits_ox));
                break;
            case "tiger":
                setResults(R.drawable.zodiac_tiger, getString(R.string.personality_traits_tiger));
                break;
            case "rabbit":
                setResults(R.drawable.zodiac_rabbit, getString(R.string.personality_traits_rabbit));
                break;
            case "dragon":
                setResults(R.drawable.zodiac_dragon, getString(R.string.personality_traits_dragon));
                break;
            case "snake":
                setResults(R.drawable.zodiac_snake, getString(R.string.personality_traits_snake));
                break;
            case "horse":
                setResults(R.drawable.zodiac_horse, getString(R.string.personality_traits_horse));
                break;
            case "goat":
                setResults(R.drawable.zodiac_goat, getString(R.string.personality_traits_goat));
                break;
            case "monkey":
                setResults(R.drawable.zodiac_monkey, getString(R.string.personality_traits_monkey));
                break;
            case "rooster":
                setResults(R.drawable.zodiac_rooster, getString(R.string.personality_traits_rooster));
                break;
            case "dog":
                setResults(R.drawable.zodiac_dog, getString(R.string.personality_traits_dog));
                break;
            case "pig":
                setResults(R.drawable.zodiac_pig, getString(R.string.personality_traits_pig));
                break;
            default:
                System.out.println("Invalid zodiac");
        }
    }

    private void setResults(int imageDrawableId, String personalityTraits) {
        this.imageDrawableId = imageDrawableId;
        this.birthYears = getBirthYears(getZodiacIndex(zodiac));
        this.personalityTraits = personalityTraits;
    }

    private String getElement(int year) {
        String yearElement = "";
        int lastDigit = year % 10;

        if(lastDigit == 0 || lastDigit == 1) {
            yearElement = "Metal";
        } else if(lastDigit == 2 || lastDigit == 3) {
            yearElement = "Water";
        } else if(lastDigit == 4 || lastDigit == 5) {
            yearElement = "Wood";
        } else if(lastDigit == 6 || lastDigit == 7) {
            yearElement = "Fire";
        } else if(lastDigit == 8 || lastDigit == 9) {
            yearElement = "Earth";
        }

        return yearElement;
    }

    private String getZodiacSign(int year) {
        return this.listZodiac[(year - 1900) % 12];
    }

    private int getZodiacIndex(String zodiac) {
        int animalIndex = 0;
        for (int i = 0; i < this.listZodiac.length; i++) {
            if (this.listZodiac[i].equals(zodiac)) {
                animalIndex = i;
                break;
            }
        }
        return animalIndex;
    }

    private String getBirthYears (int zodiacIndex) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);

        int currentChineseYear = (currentYear - 4) % 60;

        int startYear = currentYear + (zodiacIndex - currentChineseYear % 12 + 12) % 12;

        ArrayList<String> listYears = new ArrayList<>();

        for (int i = 0; i < 9 ; i++) {
            listYears.add(Integer.toString(startYear));
            startYear -= 12;
        }

        return String.join(", ", listYears);
    }


    private void displayResults () {

        ImageView imageZodiacAnimal = findViewById(R.id.imageZodiacAnimal);
        imageZodiacAnimal.setImageResource(this.imageDrawableId);

        TextView textViewZodiac = findViewById(R.id.textViewZodiac);
        textViewZodiac.setText(this.zodiac);

        TextView textViewBirthYears = findViewById(R.id.textViewBirthYears);
        textViewBirthYears.setText(this.birthYears);

        TextView textViewElement = findViewById(R.id.textViewElement);
        textViewElement.setText(this.element);
    }

    private void animatePortal() {
        ImageView imagePortal = findViewById(R.id.imagePortal);
        ObjectAnimator animator = ObjectAnimator.ofFloat(imagePortal, "rotation", 0, 360);
        animator.setDuration(20000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private void animateClouds() {

        Clouds cloud1 = new Clouds(findViewById(R.id.imageCloud1), 1500, -5f);
        cloud1.animate();

        Clouds cloud2 = new Clouds(findViewById(R.id.imageCloud2), 2000, -15f);
        cloud2.animate();

        Clouds cloud3 = new Clouds(findViewById(R.id.imageCloud3), 2500, -20f);
        cloud3.animate();
    }
}