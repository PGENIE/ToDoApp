package companydomain.myapplication;


import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    int REQUEST_ACT=1;
    Button todo;
    ListView listView;
    Adapter adapter;
    String resultMsg;
    int color=3;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {

        int id = menuItem.getItemId();

        switch(id){
            case R.id.menu_orange:
                Toast.makeText(getApplicationContext(),"menu_orange clicked",Toast.LENGTH_SHORT).show();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFffc46f));
                getWindow().setStatusBarColor(Color.rgb(244, 142, 40));
                color=1;
                return true;
            case R.id.menu_yellow:
                Toast.makeText(getApplicationContext(),"menu_yellow clicked",Toast.LENGTH_SHORT).show();
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFFfff36e));
                getWindow().setStatusBarColor(Color.rgb(242, 191, 51));
                color=2;
                return true;
            case R.id.menu_green:
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(0xFF71C386));
                getWindow().setStatusBarColor(Color.rgb(91, 157, 108));
                color=3;
                Toast.makeText(getApplicationContext(),"menu_green clicked",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(menuItem);


    }


//adapter for listview
    class Adapter extends BaseAdapter {
        ArrayList<Item> myitem = new ArrayList<Item>();

        @Override
        public int getCount(){
            return myitem.size();
        }
        public void addItem(Item item){
            myitem.add(item);

        }
        public Object getItem(int position){
            return myitem.get(position);
        }
        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertview, ViewGroup viewGroup){
            ItemView view = new ItemView(getApplicationContext());
            Item item = myitem.get(position);

            view.setId(item.getId());
            view.setName(item.getName());
            view.setDate(item.getDate());
            view.setIpt(item.getIpt());

            return view;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SQLiteDatabase db = openOrCreateDatabase(
                "todo.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );
        db.execSQL("CREATE TABLE IF NOT EXISTS todo_list " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,todo TEXT not null, date TEXT, importance text);");


        if (resultCode != RESULT_OK) {
            Toast.makeText(MainActivity.this, "Not Succeed", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == REQUEST_ACT) {
            resultMsg = data.getStringExtra("result_msg");
            Toast.makeText(MainActivity.this, resultMsg , Toast.LENGTH_SHORT).show();
            Cursor c1 = db.rawQuery("SELECT * FROM todo_list;", null);

            int id;
            String name;
            String date;
            String ipt;
            Drawable img_ipt;
            Resources res=getResources();

            adapter = new Adapter();
            while (c1.moveToNext()) {
                id = c1.getInt(0);
                name = c1.getString(1);
                date = c1.getString(2);
                ipt = c1.getString(3);

                if(ipt==null) {
                    img_ipt=res.getDrawable(R.drawable.star0);
                }
                else if(Float.parseFloat(ipt)==1)
                {
                    img_ipt=res.getDrawable(R.drawable.star1);
                }
                else if(Float.parseFloat(ipt)==2)
                {
                    img_ipt=res.getDrawable(R.drawable.star2);
                }
                else if(Float.parseFloat(ipt)==3)
                {
                    img_ipt=res.getDrawable(R.drawable.star3);
                }
                else if(Float.parseFloat(ipt)==4)
                {
                    img_ipt=res.getDrawable(R.drawable.star4);
                }
                else if(Float.parseFloat(ipt)==5)
                {
                    img_ipt=res.getDrawable(R.drawable.star5);
                }
                else
                {
                    img_ipt=res.getDrawable(R.drawable.star0);
                }

                adapter.addItem(new Item(id, name, date, img_ipt));
                adapter.notifyDataSetChanged();
            }
            c1.close();
            listView.setAdapter(adapter);

        } else {
            Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }
    }
    SwipeRefreshLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("ToDo App");


       SQLiteDatabase db = openOrCreateDatabase(
                "todo.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );
       // db.execSQL("DROP TABLE todo_list");
        db.execSQL("CREATE TABLE IF NOT EXISTS todo_list " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT,todo TEXT not null, date TEXT, importance text);");

        todo = (Button) findViewById(R.id.addTodoBtn);
        todo.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listview);

        Cursor c1 = db.rawQuery("SELECT * FROM todo_list;", null);
        int id;
        String name;
        String date;
        String ipt;
        Drawable img_ipt;
        Resources res=getResources();

        adapter=new Adapter();
        while (c1.moveToNext()) {
            id = c1.getInt(0);
            name = c1.getString(1);
            date = c1.getString(2);
            ipt = c1.getString(3);
            Log.i("aa",ipt);
            if(ipt==null) {
                img_ipt=res.getDrawable(R.drawable.star0);
            }
            else if(Float.parseFloat(ipt)==1)
            {
                img_ipt=res.getDrawable(R.drawable.star1);
            }
            else if(Float.parseFloat(ipt)==2)
            {
                img_ipt=res.getDrawable(R.drawable.star2);
            }
            else if(Float.parseFloat(ipt)==3)
            {
                img_ipt=res.getDrawable(R.drawable.star3);
            }
            else if(Float.parseFloat(ipt)==4)
            {
                img_ipt=res.getDrawable(R.drawable.star4);
            }
            else if(Float.parseFloat(ipt)==5)
            {
                img_ipt=res.getDrawable(R.drawable.star5);
            }
            else
            {
                img_ipt=res.getDrawable(R.drawable.star0);
            }

            adapter.addItem(new Item(id, name, date, img_ipt));
        }
        c1.close();


        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id){
                Item item = (Item) adapter.getItem(position);

                Intent intent3 = new Intent(MainActivity.this, Updateordelete.class);
                intent3.putExtra("color", color);
                intent3.putExtra("id",item.getId());
                startActivityForResult(intent3,REQUEST_ACT);
            }
        });
    }

    @Override
    public void onClick(View v) {

        if (v == todo) {
            //Intent intent = new Intent(this, AddTodoActivity.class);
            // startActivity(intent);
            Intent intent2 = new Intent(MainActivity.this, AddTodoActivity.class);
            intent2.putExtra("color", color);
            startActivityForResult(intent2, REQUEST_ACT);

        }

    }



    public void RadioOnclick(View view)
    {
        SQLiteDatabase db = openOrCreateDatabase(
                "todo.db",
                SQLiteDatabase.CREATE_IF_NECESSARY,
                null
        );

        listView = (ListView) findViewById(R.id.listview);

        Cursor c1;
        int id;
        String name;
        String date;
        String ipt;
        Drawable img_ipt;
        Resources res=getResources();

        //show star image depends on the importance number
        switch (view.getId())
        {
            case R.id.radio1:
                c1 = db.rawQuery("SELECT * FROM todo_list;", null);
                adapter=new Adapter();
                while (c1.moveToNext()) {
                    id = c1.getInt(0);
                    name = c1.getString(1);
                    date = c1.getString(2);
                    ipt = c1.getString(3);

                    if((Float.parseFloat(ipt)==1))
                    {
                        img_ipt=res.getDrawable(R.drawable.star1);
                    }
                    else if(Float.parseFloat(ipt)==2)
                    {
                        img_ipt=res.getDrawable(R.drawable.star2);
                    }
                    else if(Float.parseFloat(ipt)==3)
                    {
                        img_ipt=res.getDrawable(R.drawable.star3);
                    }
                    else if(Float.parseFloat(ipt)==4)
                    {
                        img_ipt=res.getDrawable(R.drawable.star4);
                    }
                    else if(Float.parseFloat(ipt)==5)
                    {
                        img_ipt=res.getDrawable(R.drawable.star5);
                    }
                    else
                    {
                        img_ipt=res.getDrawable(R.drawable.star0);
                    }


                    adapter.addItem(new Item(id, name, date, img_ipt));
                }
                c1.close();


                listView.setAdapter(adapter);
                break;
            case R.id.radio2:

                c1 = db.rawQuery("SELECT * FROM todo_list order by date;", null);

                adapter=new Adapter();
                while (c1.moveToNext()) {
                    id = c1.getInt(0);
                    name = c1.getString(1);
                    date = c1.getString(2);
                    ipt = c1.getString(3);

                    if((Float.parseFloat(ipt)==1))
                    {
                        img_ipt=res.getDrawable(R.drawable.star1);
                    }
                    else if(Float.parseFloat(ipt)==2)
                    {
                        img_ipt=res.getDrawable(R.drawable.star2);
                    }
                    else if(Float.parseFloat(ipt)==3)
                    {
                        img_ipt=res.getDrawable(R.drawable.star3);
                    }
                    else if(Float.parseFloat(ipt)==4)
                    {
                        img_ipt=res.getDrawable(R.drawable.star4);
                    }
                    else if(Float.parseFloat(ipt)==5)
                    {
                        img_ipt=res.getDrawable(R.drawable.star5);
                    }
                    else
                    {
                        img_ipt=res.getDrawable(R.drawable.star0);
                    }


                    adapter.addItem(new Item(id, name, date, img_ipt));
                }
                c1.close();


                listView.setAdapter(adapter);
                break;
            case R.id.radio3:
                c1 = db.rawQuery("SELECT * FROM todo_list order by importance desc;", null);

                adapter=new Adapter();
                while (c1.moveToNext()) {
                    id = c1.getInt(0);
                    name = c1.getString(1);
                    date = c1.getString(2);
                    ipt = c1.getString(3);

                    if((Float.parseFloat(ipt)==1))
                    {
                        img_ipt=res.getDrawable(R.drawable.star1);
                    }
                    else if(Float.parseFloat(ipt)==2)
                    {
                        img_ipt=res.getDrawable(R.drawable.star2);
                    }
                    else if(Float.parseFloat(ipt)==3)
                    {
                        img_ipt=res.getDrawable(R.drawable.star3);
                    }
                    else if(Float.parseFloat(ipt)==4)
                    {
                        img_ipt=res.getDrawable(R.drawable.star4);
                    }
                    else if(Float.parseFloat(ipt)==5)
                    {
                        img_ipt=res.getDrawable(R.drawable.star5);
                    }
                    else
                    {
                        img_ipt=res.getDrawable(R.drawable.star0);
                    }


                    adapter.addItem(new Item(id, name, date, img_ipt));
                }
                c1.close();


                listView.setAdapter(adapter);
                break;
        }

    }
}



