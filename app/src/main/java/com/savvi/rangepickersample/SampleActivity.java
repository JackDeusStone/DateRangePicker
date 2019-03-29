package com.savvi.rangepickersample;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.savvi.rangedatepicker.CalendarCellDecorator;
import com.savvi.rangedatepicker.CalendarCellView;
import com.savvi.rangedatepicker.CalendarPickerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class SampleActivity extends AppCompatActivity {

    CalendarPickerView calendar;

    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        activity = this;

        final Calendar nextYear = Calendar.getInstance();
        nextYear.add(Calendar.YEAR, 1);

        final Calendar lastYear = Calendar.getInstance();

        calendar = findViewById(R.id.calendar_view);
//        button = findViewById(R.id.get_selected_dates);

        final ArrayList<Date> arrayList = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            arrayList.add(sdf.parse("3-4-2019"));
            arrayList.add(sdf.parse("7-4-2019"));
            arrayList.add(sdf.parse("12-4-2019"));
            arrayList.add(sdf.parse("18-4-2019"));
            arrayList.add(sdf.parse("20-4-2019"));
            arrayList.add(sdf.parse("22-4-2019"));
            arrayList.add(sdf.parse("26-4-2019"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.init(lastYear.getTime(), nextYear.getTime(), new SimpleDateFormat("LLLL"))
                .inMode(CalendarPickerView.SelectionMode.RANGE)
                .withHighlightedDates(arrayList);
        calendar.setDateSelectableFilter(new CalendarPickerView.DateSelectableFilter() {
            @Override
            public boolean isDateSelectable(Date date) {
                Date currentDate = calendar.getSelectedDate();
                for (Date highlightedDate : arrayList){
                    if (date.equals(highlightedDate)){
                        if (currentDate == null){
                            return true;
                        }
                        long diffInMillisecons = date.getTime() - currentDate.getTime();
                        long diffInDays = TimeUnit.MILLISECONDS.toDays(diffInMillisecons);
                        if (diffInDays <= 14){
                            return true;
                        }
                        calendar.clearSelectedDates();
                        calendar.selectDate(date);
                    }
                }
                return false;
            }
        });
        calendar.setOnInvalidDateSelectedListener(new CalendarPickerView.OnInvalidDateSelectedListener() {
            @Override
            public void onInvalidDateSelected(Date date) {
                Log.d("228322", "error");
            }
        });
        CalendarCellDecorator decorator = new CalendarCellDecorator() {
            @Override
            public void decorate(CalendarCellView cellView, Date date) {
                TextView textView = cellView.getDayOfMonthTextView();
                textView.setTypeface(textView.getTypeface(),
                        cellView.isHighlighted() ? Typeface.BOLD : Typeface.NORMAL);
            }
        };
        List<CalendarCellDecorator> decorators = new ArrayList<>();
        decorators.add(decorator);
        calendar.setDecorators(decorators);
    }
}
