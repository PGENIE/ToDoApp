package companydomain.myapplication;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import static companydomain.myapplication.R.id.ratingBar;

public class AddTodoActivity extends AppCompatActivity implements View.OnClickListener {

    //color default is green
    int color=3;

    EditText todoView;
    TextView dateView2;

    RatingBar iptView;
    TextView iptText;
    String date;

    Button SdateBtn;
    Button addBtn;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        getSupportActionBar().setTitle("ToDo App_Add");

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


        todoView = (EditText) findViewById(R.id.todoinfo);
        dateView2=(TextView)findViewById(R.id.DateText);
 //       dateView = (EditText) findViewById(R.id.dateinfo);

        iptView = (RatingBar) findViewById(ratingBar);
        iptText = (TextView)findViewById(R.id.ratingText);

        SdateBtn=(Button)findViewById(R.id.SetDateBtn);
        addBtn = (Button) findViewById(R.id.AddTodoBtn);


        addBtn.setOnClickListener(this);
        SdateBtn.setOnClickListener(this);

        iptView.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                iptText.setText(""+rating);
            }
        });


    }

    //for getting date information from calendar
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            Toast.makeText(AddTodoActivity.this,"fail",Toast.LENGTH_SHORT);
            return;
        }
        if(requestCode == 1) {
            String resultMsg = data.getStringExtra("result_msg");
            Toast.makeText(AddTodoActivity.this,"결과:"+resultMsg,Toast.LENGTH_SHORT);
            dateView2.setText(resultMsg);
        }
        else{
            Toast.makeText(AddTodoActivity.this,"Not request",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onClick(View v) {

        if(v==SdateBtn){
            Intent intent = new Intent(this, CalendarActivity.class);
            intent.putExtra("color", color);
            startActivityForResult(intent,1);

        }
        else if(v==addBtn) {
            String todo = todoView.getText().toString();
            String date = dateView2.getText().toString();
            String importance = iptText.getText().toString();
            Toast a = Toast.makeText(this, todo + " " + date + " " + importance + " ", Toast.LENGTH_SHORT);
            a.show();
            if(importance=="")
                importance="0.0";

            if (todo == null || todo.equals("")) {
                Toast t = Toast.makeText(this, "'todo' is empty", Toast.LENGTH_SHORT);
                t.show();
            } else {
                SQLiteDatabase db = openOrCreateDatabase(
                        "todo.db",
                        SQLiteDatabase.CREATE_IF_NECESSARY,
                        null
                );

                String sql = "INSERT INTO todo_list (todo,date,importance) VALUES ('" + todo + "','" + date + "','" + importance + "');";

                db.execSQL(sql);
                Intent intent2 = new Intent();
                intent2.putExtra("result_msg", "Insert succeed");
                setResult(RESULT_OK, intent2);

                finish();
            }
        }

    }
}
