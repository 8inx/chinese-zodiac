package io.daee.chinesezodiac;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_STRING = "io.daee.chinesezodiac.EXTRA_STRING";
    private EditText inputDate;

    private int selectedYear;
    private int sliderDelay = 6000;
    private boolean isRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transparentStatusBar();
        animateDatePickerButton();
        setTriviaSlider();

        inputDate = findViewById(R.id.inputDate);
        initializeCurrentDate();

        inputDate.setInputType(InputType.TYPE_NULL);
        inputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog();
            }
        });

        Button btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openResultActivity();
            }
        });
    }

    private void initializeCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        this.selectedYear = year;
        inputDate.setText(getFormattedDate(month,day,year));
    }

    private void openResultActivity() {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(EXTRA_STRING, this.selectedYear);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String dateString = getFormattedDate(monthOfYear, dayOfMonth, year);
                        inputDate.setText(dateString);
                        selectedYear = year;
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void setTriviaSlider() {

        ArrayList<String> itemList = getTriviaList();
        SliderAdapter sliderAdapter = new SliderAdapter(itemList);

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(sliderAdapter);
        viewPager.setCurrentItem(1);

        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                handler.postDelayed(this, sliderDelay);
            }
        };

        isRunning = true;
        handler.postDelayed(runnable, sliderDelay);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    viewPager.setCurrentItem(itemList.size() - 2, false);
                } else if (position == itemList.size() - 1) {
                    viewPager.setCurrentItem(1, false);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    isRunning = false;
                    handler.removeCallbacks(runnable);

                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    if(!isRunning) {
                        isRunning = true;
                        handler.postDelayed(runnable, sliderDelay);
                    }
                }
            }
        });
    }

    private String getFormattedDate(int month, int day, int year) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);

        String formattedDate = dateFormat.format(calendar.getTime());
        return  formattedDate;

    }

    private void transparentStatusBar() {
        if(Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 23) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getSupportActionBar().hide();
        }
    }

    private void animateDatePickerButton() {
        ImageView datePickerButtonImage = findViewById(R.id.datePickerButtonImage);
        ObjectAnimator animator = ObjectAnimator.ofFloat(datePickerButtonImage, "rotation", 0, 360);
        animator.setDuration(20000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }

    private ArrayList<String> getTriviaList() {
        ArrayList<String> list = new ArrayList<>();
        list.add(getString(R.string.trivia_item_1));
        list.add(getString(R.string.trivia_item_2));
        list.add(getString(R.string.trivia_item_3));
        list.add(getString(R.string.trivia_item_4));
        list.add(getString(R.string.trivia_item_5));
        list.add(getString(R.string.trivia_item_6));

        list.add(0, list.get(list.size() - 1));
        list.add(list.get(1));

        return list;
    }

}