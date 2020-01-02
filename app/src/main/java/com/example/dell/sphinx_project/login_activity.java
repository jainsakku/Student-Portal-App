package com.example.dell.sphinx_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class login_activity extends AppCompatActivity
{
    Button login,siginup;
    EditText id,pass;
    RadioButton admin,teacher,student;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);
        siginup=(Button) findViewById(R.id.button3);
        login = (Button) findViewById(R.id.button1);
        pass = (EditText) findViewById(R.id.EditText2);
        id = (EditText) findViewById(R.id.editText1);
        student = (RadioButton) findViewById(R.id.student);
        teacher = (RadioButton) findViewById(R.id.teacher);
        admin = (RadioButton) findViewById(R.id.admin);
        id.setText("");
        pass.setText("");
        siginup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    startActivity(new Intent(getApplicationContext(),sigin_up_Activity.class));
                    return;
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                }

            }
        });
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {

                // TODO Auto-generated method stub
                String eid =  id.getText().toString();
                String pwd = pass.getText().toString();
                if(eid.equals(""))
                {
                    Toast.makeText(login_activity.this,"Emai Id || Roll Number Can't Be Remain Empty",Toast.LENGTH_LONG).show();
                    return;
                }
                if(pwd.equals(""))
                {
                    Toast.makeText(login_activity.this,"Password can't Be Empty",Toast.LENGTH_LONG).show();
                    return;
                }

				/*try{
				List<AdminManager> list = AdminManager.getRecords(getApplicationContext(), "SELECT * FROM adminmaster");
				Toast.makeText(getApplicationContext(), list.get(0).getAdminname(), Toast.LENGTH_LONG).show();
				}
				catch (Exception e) {
					Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
				}
					// TODO: handle exception
				}
				*/
                if(admin.isChecked())
                {
                    // validate admin if yes tan launch its profile
                    try{
                        if(AdminManager.validate(login_activity.this, eid, pwd) && ( !StudentManager.validate(getApplicationContext(),eid,pwd)) && !TeacherManager.validate(getApplicationContext(),eid,pwd))
                        {
                            SharedPreferences pref = getSharedPreferences("User", MODE_PRIVATE);
                            SharedPreferences.Editor edit = pref.edit();
                            edit.putString("user", "a");
                            edit.putString("id", eid);
                            edit.putString("pwd", pwd);
                            edit.commit();
                            Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
                            // validate admin if yes tan launch its profile
                            Intent i = new Intent(login_activity.this,AdminActivity.class);
                            startActivity(i);
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "Enter Valid Email ID && Password For Successfully Login For Admin", Toast.LENGTH_LONG).show();
                        }
                    }catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                        // TODO: handle exception
                    }
                }
                else
                {
                    if(student.isChecked())
                    {
                        // validate student if yes tan launch its profile
                        try{
                            if(StudentManager.validate(login_activity.this,eid,pwd) && ( !TeacherManager.validate(getApplicationContext(),eid,pwd)) && !AdminManager.validate(getApplicationContext(),eid,pwd))
                            {
                                SharedPreferences pref = getSharedPreferences("User", MODE_PRIVATE);
                                SharedPreferences.Editor edit = pref.edit();
                                edit.putString("user", "s");
                                edit.putString("id", eid);
                                edit.putString("pwd", pwd);
                                edit.commit();
                                Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
                                // validate student if yes tan launch its profile
                                Intent i = new Intent(login_activity.this,studentActivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Enter Valid Roll Number && Password For Successfully Login As Student", Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"Student"+ e.toString(), Toast.LENGTH_LONG).show();
                            // TODO: handle exception
                        }
                    }
                    else
                    {
                        if(teacher.isChecked())
                        {
                            //Toast.makeText(getApplicationContext(),eid+" "+pwd,Toast.LENGTH_LONG ).show();
                            // validate teacher if yes tan launch its profile
                            try{

                                //if(TeacherManager.validate(login_activity.this, eid, pwd))
                                //{
                                  //  Toast.makeText(getApplicationContext(),"TRUE",Toast.LENGTH_LONG ).show();
                                //}
                                if(TeacherManager.validate(login_activity.this, eid, pwd)  && ( !StudentManager.validate(getApplicationContext(),eid,pwd)) && !AdminManager.validate(getApplicationContext(),eid,pwd))
                                {
                                    SharedPreferences pref = getSharedPreferences("User", MODE_PRIVATE);
                                    SharedPreferences.Editor edit = pref.edit();
                                    edit.putString("user", "t");
                                    edit.putString("id", eid);
                                    edit.putString("pwd", pwd);
                                    edit.commit();

                                    Toast.makeText(getApplicationContext(), "Found", Toast.LENGTH_LONG).show();
                                    // validate teacher if yes tan launch its profile
                                    Intent i = new Intent(login_activity.this,teacherActivity.class);
                                    startActivity(i);
                                }
                                else
                                {

                                    Toast.makeText(getApplicationContext(), "Enter Valid Email ID && Password For Successfully Login For Teacher", Toast.LENGTH_LONG).show();
                                }
                            }catch (Exception e)
                            {
                                Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                                // TODO: handle exception
                            }

                        }
                    }
                }
            }
        });

    }
}
