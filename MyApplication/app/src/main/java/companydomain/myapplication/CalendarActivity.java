package companydomain.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {

    String Cdate;
    int color=3;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        getSupportActionBar().setTitle("ToDo App_Calender");
        color=getIntent().getIntExtra("color",1);

        if(color==1){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFffc46f));
            getWindow().setStatusBarColor(Color.rgb(244, 142, 40));
        }
        else if (color==2){
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFfff36e));
            getWindow().setStatusBarColor(Color.rgb(242, 191, 51));
        }
        else{
        }

        MaterialCalendarView materialCalendarView = (MaterialCalendarView) findViewById(R.id.calendarView);

        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setMinimumDate(CalendarDay.from(1900, 1, 1))
                .setMaximumDate(CalendarDay.from(2100, 12, 31))
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Cdate = "" + date;
                String splitStrArr1[] = Cdate.split("\\{");
                String splitStrArr2[] = splitStrArr1[1].split("\\}");
                String splitStrArr3[] = splitStrArr2[0].split("-");
                Cdate=splitStrArr3[0]+"-"+(Integer.parseInt(splitStrArr3[1])+1)+"-"+splitStrArr3[2];
                Toast.makeText(CalendarActivity.this, Cdate, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void CalendarBtn(View v) {
        Intent intent = new Intent();
        intent.putExtra("result_msg", Cdate);
        setResult(RESULT_OK, intent);
        finish();
    }


}

//I refer to https://github.com/prolificinteractive/material-calendarview/blob/master/docs/CUSTOMIZATION_BUILDER.md for calendarActivity.