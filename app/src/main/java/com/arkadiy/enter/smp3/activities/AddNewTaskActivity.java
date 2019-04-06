package com.arkadiy.enter.smp3.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.arkadiy.enter.smp3.R;
import com.arkadiy.enter.smp3.config.AppConfig;
import com.arkadiy.enter.smp3.dataObjects.Task;
import com.arkadiy.enter.smp3.dataObjects.Users;
import com.arkadiy.enter.smp3.services.UserServices;
import com.arkadiy.enter.smp3.utils.Constants;

import java.util.ArrayList;
import java.util.Calendar;

public class AddNewTaskActivity extends AppCompatActivity  {
    private EditText endDateTask;
    private EditText startDateTask;
    private EditText nameTaskEditText;
    private EditText taskDecriptionEditText;
    private Calendar mCurrentDate;
    private Calendar mCurrentTime;
    private Button creatNewTaskButton;
    private Spinner workersSpinner;
    private Spinner departmantsSpinner;
    private ArrayList<String> workersItemsList;
    private ArrayList<String> departmentsItemsList;
    private ArrayAdapter arrayAdapterWorkers;
    private ArrayAdapter arrayAdapterDepartments;
    private Task task;
    private RequestQueue requestQueue;
    private Object[] array;
    private ArrayList<Users>currentUsers;
    Users selecteduser;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
        toBuildActivity();
        getDepartmants();

        startDateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               //startDateTask.setText("");
                getDate(startDateTask);

            }
        });

        endDateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                endDateTask.setText("");
                getDate(endDateTask);
            }
        });

        creatNewTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String start = startDateTask.getText().toString();
                String end = endDateTask.getText().toString();
                String name = nameTaskEditText.getText().toString() ;
                String dec = taskDecriptionEditText.getText().toString();
                task = new Task(selecteduser.getUserId(),start,end,name,dec);

                UserServices.sendTask(task.getTask(),requestQueue,AddNewTaskActivity.this);
            }
        });

//        departmantsSpinner.setOnItemSelectedListener(this);
//
        departmantsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                   int departmentNumber= LogInActivity.MYDEPARTMENTS.get(array[i]); //getting department number from MYDEPARTMENTS as integer
                    setUsersByDepartmentSelected(departmentNumber); //sending this integer to method
                }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        workersSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              selecteduser= currentUsers.get(i); //selected user
                Toast.makeText(getApplicationContext(),String.valueOf(selecteduser.getUserId()),Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


//        departmantsSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

//        });



    }
//

    private void setUsersByDepartmentSelected(int i){
        arrayAdapterWorkers.clear(); //empty all array
        this.currentUsers=MainActivity.USERS.get(i);
        int size = MainActivity.USERS.get(i).size();
        for(int j = 0 ; j <size; j++){ //Run through all array of this department and get username
            arrayAdapterWorkers.add(MainActivity.USERS.get(i).get(j).getUserName());
            //MainActivity.USERS.get(i).get(j)  <--here you have user object
            }
                workersSpinner.setAdapter(arrayAdapterWorkers);
        }

    private void getDepartmants() {
        array= LogInActivity.MYDEPARTMENTS.keySet().toArray();
        for (int i=0;i<array.length;i++){
           arrayAdapterDepartments.add(array[i]);
        }
        departmantsSpinner.setAdapter(arrayAdapterDepartments);
    }


    private void getDate(final EditText editText){

        mCurrentDate = Calendar.getInstance();
        mCurrentTime = Calendar.getInstance();
        int year = mCurrentDate.get(Calendar.YEAR);
        int month = mCurrentDate.get(Calendar.MONTH);
        int day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
        final int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        final int minute = mCurrentTime.get(Calendar.MINUTE);
        editText.setText("");

        DatePickerDialog datePickerDialog = new DatePickerDialog(AddNewTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDateSet(DatePicker datePicker, final int selectYear, final int selectMonth, final int selectDay) {

                 //editText.setText(selectDay+"-"+selectMonth+"-"+selectYear);
                 mCurrentDate.set(selectYear,selectMonth,selectDay);

                TimePickerDialog timePickerDialog = new TimePickerDialog(AddNewTaskActivity.this, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectHour, int selectMinute) {

                        editText.setText(selectDay+"-"+selectMonth+"-"+selectYear +" "+selectHour+":"+selectMinute);
                        mCurrentTime = Calendar.getInstance();
                        mCurrentDate.set(Calendar.HOUR_OF_DAY, selectHour);
                        mCurrentDate.set(Calendar.MINUTE, selectMinute);
                    }
                },hour,minute,true);
                timePickerDialog.show();
            }
        }, year, month, day);
        datePickerDialog.show();

    }

    private void toBuildActivity(){
        nameTaskEditText = (EditText)findViewById(R.id.nameTask_EditText);
        taskDecriptionEditText = (EditText)findViewById(R.id.taskDecription_EditText);
        startDateTask = (EditText)findViewById(R.id.editStartDate_EditText);
        endDateTask = (EditText)findViewById(R.id.editEnd_EditText);
        requestQueue = Volley.newRequestQueue(AddNewTaskActivity.this);
        workersSpinner = (Spinner)findViewById(R.id.workers_Spinner);
        departmantsSpinner = (Spinner)findViewById(R.id.departmants_Spinner);
        creatNewTaskButton = (Button)findViewById(R.id.creatNewTask_button);
        workersItemsList = new ArrayList<>();
        departmentsItemsList = new ArrayList<>();
        UserServices.sendData(AppConfig.GET_MY_USERS,null,requestQueue,AddNewTaskActivity.this,Constants.METHOD_GET,null);
        arrayAdapterDepartments = new ArrayAdapter<String>(AddNewTaskActivity.this,android.R.layout.simple_spinner_dropdown_item,departmentsItemsList);
        arrayAdapterWorkers = new ArrayAdapter<String>(AddNewTaskActivity.this,android.R.layout.simple_spinner_dropdown_item,workersItemsList);

    }
}
