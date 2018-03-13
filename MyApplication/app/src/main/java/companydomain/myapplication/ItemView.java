package companydomain.myapplication;

/**
 * Created by YUJIN on 2017-11-11.
 */


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ItemView extends LinearLayout {

    TextView item_id;
    TextView item_name;
    TextView item_date;
    ImageView item_ipt;

    public ItemView(Context context){
        super(context);
        init(context);
    }

    public ItemView(Context context, AttributeSet attrs){
        super(context,attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.item,this,true);

        item_id = (TextView)findViewById(R.id.item_id);
        item_name = (TextView)findViewById(R.id.item_name);
        item_date=(TextView)findViewById(R.id.item_date);
        item_ipt = (ImageView) findViewById(R.id.item_ipt);
    }

    public void setId(int id){
        item_id.setText(String.valueOf(id));
    }
    public void setName(String name){
        item_name.setText(name);
    }
    public void setDate(String date){
        item_date.setText(date);
    }
    public void setIpt(Drawable ipt){
        item_ipt.setImageDrawable(ipt);
    }
}