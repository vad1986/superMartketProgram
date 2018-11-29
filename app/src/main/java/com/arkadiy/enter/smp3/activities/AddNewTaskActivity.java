package com.arkadiy.enter.smp3.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.arkadiy.enter.smp3.R;
import com.arkadiy.enter.smp3.dataObjects.Task;

import java.util.ArrayList;
import java.util.Calendar;

public class AddNewTaskActivity extends AppCompatActivity {
    private EditText endDateTask;
    private EditText startDateTask;
    private EditText startTimeTask;
    private EditText endTimeTask;
    private EditText nameTaskEditText;
    private EditText taskDecriptionEditText;
    private Calendar mCurrentDate;
    private Button creatNewTaskButton;
    private boolean flagDateError;
    private Spinner spinner;
    private String[] workers;
    private ArrayList<String> itemsList;
    private ArrayAdapter arrayAdapterWorkers;
    private Task task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        workers = new String[]{"All","Arkadiy", "Dani", "Vadim", "Gil", "Ali", "Alon","Arkadiy", "Dani", "Vadim", "Gil", "Ali", "Alon","Arkadiy", "Dani", "Vadim", "Gil", "Ali", "Alon","Arkadiy", "Dani", "Vadim", "Gil", "Ali", "Alon","Arkadiy", "Dani", "Vadim", "Gil", "Ali", "Alon","Arkadiy", "Dani", "Vadim", "Gil", "Ali", "Alon","Arkadiy", "Dani", "Vadim", "Gil", "Ali", "Alon","Arkadiy", "Dani", "Vadim", "Gil", "Ali", "Alon","Arkadiy", "Dani", "Vadim", "Gil", "Ali", "Alon","Arkadiy", "Dani", "Vadim", "Gil", "Ali", "Alon"};
        flagDateError = false;
        setContentView(R.layout.activity_add_new_task);
        nameTaskEditText = (EditText)findViewById(R.id.nameTask_EditText);
        taskDecriptionEditText = (EditText)findViewById(R.id.taskDecription_EditText);
        startDateTask = (EditText)findViewById(R.id.editStartDate_EditText);
        endDateTask = (EditText)findViewById(R.id.editEnd_EditText);
        startTimeTask = (EditText)findViewById(R.id.editStartTime_editText);
        endTimeTask = (EditText)findViewById(R.id.editEndtTime_editText);
        spinner = (Spinner)findViewById(R.id.workers_Spinner);
        creatNewTaskButton = (Button)findViewById(R.id.creatNewTask_button);
        itemsList = new ArrayList<>();
        arrayAdapterWorkers = new ArrayAdapter<String>(AddNewTaskActivity.this,android.R.layout.simple_spinner_dropdown_item,itemsList);
        for(int i = 0 ; i <workers.length; i++){
            arrayAdapterWorkers.add(workers[i]);
        }
        spinner.setAdapter(arrayAdapterWorkers);

        startTimeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            startTimeTask.setText("");
            getTime(startTimeTask);
            }
        });
        endTimeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endTimeTask.setText("");
                getTime(endTimeTask);
            }
        });

        startDateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startDateTask.setText("");
                getDate(startDateTask);
            }
        });

        endDateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                endDateTask.setText("");
                getDate(endDateTask);
            }
        });

        creatNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start = startDateTask.getText().toString()+" "+startTimeTask.getText().toString();
                String end = endDateTask.getText().toString()+" "+endTimeTask.getText().toString();
                String name = nameTaskEditText.getText().toString() ;
                String dec = taskDecriptionEditText.getText().toString();

                task = new Task(2,start,end,name,dec,AddNewTaskActivity.this);
            }
        });


    }

    private void getTime(final EditText editText){

        mCurrentDate = Calendar.getInstance();
        int hour = mCurrentDate.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentDate.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectHour, int selectMinute) {
                editText.setText(selectHour+":"+selectMinute);
                mCurrentDate.set(selectHour,selectMinute);
            }
        },hour,minute,true);
        timePickerDialog.show();
        return;
    }


    private void getDate(final EditText editText){

        mCurrentDate = Calendar.getInstance();
        int year = mCurrentDate.get(Calendar.YEAR);
        int month = mCurrentDate.get(Calendar.MONTH);
        int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int selectYear, int selectMonth, int selectDay) {
                 editText.setText(selectDay+"-"+selectMonth+"-"+selectYear);
                mCurrentDate.set(selectYear,selectMonth,selectDay);

            }
        }, year, month, day);
        datePickerDialog.show();
        return;

    }
}
