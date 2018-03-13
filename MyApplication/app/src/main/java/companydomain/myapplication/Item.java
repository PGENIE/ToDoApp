package companydomain.myapplication;

import android.graphics.drawable.Drawable;

/**
 * Created by YUJIN on 2017-11-11.
 */


public class Item {
    int id;
    String name;
    String date;
    Drawable ipt;

    public Item(int id, String name, String date, Drawable ipt){
        this.id=id;
        this.name=name;
        this.date=date;
        this.ipt=ipt;
    }
    public Item(int id, String name, Drawable ipt){
        this.id=id;
        this.name=name;
        this.ipt=ipt;
    }
    public Item(int id, String name){
        this.name=name;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id=id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public String getDate(){
        return date;
    }
    public void setDate(String date){
        this.date=date;
    }

    public Drawable getIpt(){
        return ipt;
    }
    public void setIpt(Drawable ipt){
        this.ipt=ipt;
    }

}