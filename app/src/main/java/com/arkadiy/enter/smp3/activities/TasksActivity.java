package com.arkadiy.enter.smp3.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arkadiy.enter.smp3.DialogCloseTask;
import com.arkadiy.enter.smp3.R;

import java.util.ArrayList;

public class TasksActivity extends AppCompatActivity implements DialogCloseTask.DialogCloseTaskListener {

    private ListView listViewTask;
    private ArrayList<String> itemsList;
    private ArrayAdapter<String> adapter;
    private TextView amountOfTasksTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        final String[] listViewString = {"Empty the trash","To make a drink","Check the drinking stock","Perform alcohol filling","Check out alcohol inventory","To order electrical appliances","Check for sausages","Arrange office","Submit availability for next week","Transfer money to the cashier first","Download the junk","Arrange office","Submit availability for next week","Transfer money to the cashier first","Download the junk","Arrange office","Submit availability for next week","Transfer money to the cashier first","Download the junk"};



        listViewTask = (ListView)findViewById(R.id.listViewTask);
        amountOfTasksTextView = (TextView)findViewById(R.id.amountOfTasks_TextView);
        itemsList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(TasksActivity.this,android.R.layout.simple_list_item_1,itemsList);

        for(int i = 0 ; i < listViewString.length ; i++)
        {
            itemsList.add(listViewString[i]);
        }
        adapter.notifyDataSetChanged();
        listViewTask.setAdapter(adapter);
        amountOfTasksTextView.setText("Amount of open tasks: "+Integer.toString(listViewTask.getCount()));
        listViewTask.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DialogCloseTask dialogCloseTask = new DialogCloseTask();
                String nameTask = itemsList.get(i);
                dialogCloseTask.show(getSupportFragmentManager(),nameTask);

            }
        });
    }
    @Override
    public void applyText(String reason ,String tag) {
        Toast.makeText(getApplicationContext(),reason,Toast.LENGTH_SHORT).show();
        itemsList.remove(tag);
        listViewTask.setAdapter(adapter);
        amountOfTasksTextView.setText("Amount of open tasks: "+Integer.toString(listViewTask.getCount()));
    }
}
