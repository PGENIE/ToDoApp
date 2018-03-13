package companydomain.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import static companydomain.myapplication.R.id.update_ratingbar;

public class Updateordelete extends AppCompatActivity {

    int REQUEST_ACT=1;
    String resultMsg;
    SQLiteDatabase db;
    int id;
    int color;
    String iptListen;

   // TextView ipt_edit = (TextView)findViewById(R.id.updateimportance);;
  //  RatingBar newipt = (RatingBar) findViewById(update_ratingbar);

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateordelete);
        getSupportActionBar().setTitle("ToDo App_Update/Delete");

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

        id=getIntent().getIntExtra("id",1);
        db = openOrCreateDatabase(
                "todo.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null

        );

        Cursor c1 = db.rawQuery("SELECT * FROM todo_list where id="+id+";", null);
        String name;
        String date;
        String ipt;

        c1.moveToNext();
        name = c1.getString(1);
        date = c1.getString(2);
        ipt = c1.getString(3);

        EditText edit = (EditText)findViewById(R.id.updatetodo);
        edit.setText(name);

        TextView date_edit=(TextView)findViewById(R.id.updatedate);
        date_edit.setText(date);

        final TextView ipt_edit = (TextView)findViewById(R.id.updateimportance);
        ipt_edit.setText(ipt);

        RatingBar newipt = (RatingBar) findViewById(update_ratingbar);
       // ipt_edit = (TextView)findViewById(R.id.updateimportance);

        newipt.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                ipt_edit.setText(""+rating);
            }
        });

    }

    public void onClickDelete(View view) {
        String sql = "DELETE from todo_list where id="+id+";";

        db.execSQL(sql);
        Intent intent3 = new Intent();
        intent3.putExtra("result_msg", "update/delete succeed");
        setResult(RESULT_OK, intent3);
        finish();
    }

    public void onClickUpdate(View view) {


        TextView txt=(TextView)findViewById(R.id.updatetodo);
        db.execSQL("UPDATE todo_list SET todo='" + txt.getText() + "' WHERE id='" + id + "';");
        txt=(TextView)findViewById(R.id.updatedate);
        db.execSQL("UPDATE todo_list SET date='" + txt.getText() + "' WHERE id='" + id + "';");
        txt=(TextView)findViewById(R.id.updateimportance);
        db.execSQL("UPDATE todo_list SET importance='" + txt.getText() + "' WHERE id='" + id + "';");

        Intent intent3 = new Intent();
        intent3.putExtra("result_msg", "update/delete succeed");
        setResult(RESULT_OK, intent3);

        finish();
    }

    public void onClickChangeDate(View v){
        Intent intent4 = new Intent(Updateordelete.this, CalendarActivity.class);
        intent4.putExtra("color", color);
        startActivityForResult(intent4, REQUEST_ACT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            Toast.makeText(Updateordelete.this, "Not Succeed", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == REQUEST_ACT) {
            resultMsg = data.getStringExtra("result_msg");
            Toast.makeText(Updateordelete.this, "Change date : "+resultMsg , Toast.LENGTH_SHORT).show();
            TextView date_edit=(TextView)findViewById(R.id.updatedate);
            date_edit.setText(resultMsg);
        } else {
            Toast.makeText(Updateordelete.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
}


