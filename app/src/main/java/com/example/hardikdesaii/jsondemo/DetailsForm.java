package com.example.hardikdesaii.jsondemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetailsForm extends AppCompatActivity
{
    EditText val1,val2,val3;
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_form);
        val1=(EditText)findViewById(R.id.edit1);
        val2=(EditText)findViewById(R.id.edit2);
        val3=(EditText)findViewById(R.id.edit3);

        submit=(Button)findViewById(R.id.btnsubmit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //boolean validate=callValidate();
              //  if(validate==true)
                //{
                    Intent intent=new Intent(DetailsForm.this,MainActivity.class);
                    intent.putExtra("VALUE1",val1.getText().toString());
                    intent.putExtra("VALUE2",val2.getText().toString());
                    intent.putExtra("VALUE3",val3.getText().toString());
                    startActivity(intent);
                //}

            }
        });

    }


}
