package com.nicster34.mealplan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CalendarActivity extends AppCompatActivity {

    CalendarView calendarView;
    TextView myDate;
    String date;
    DateFormat formatter;
    Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        myDate = (TextView) findViewById(R.id.myDate);
        back = findViewById(R.id.send);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                date = (month + 1) + "/" + dayOfMonth + "/" + year;
                DateFormat temp = new SimpleDateFormat("MM/d/yyyy");
                formatter = new SimpleDateFormat("MMMM d yyyy");
                try {
                    date = formatter.format(temp.parse(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                myDate.setText(date);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDateSubmit();
            }
        });
    }

    protected void onDateSubmit() {
        Intent intent = getIntent();
        intent.putExtra("newDate", date);
        setResult(Activity.RESULT_OK, intent);
        this.finish();
    }

}
