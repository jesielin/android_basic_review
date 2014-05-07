package com.example.data_sql.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PersonDao {
	private DataSqliteOpenHelper helper;
	
	public PersonDao(Context context){
		helper = new DataSqliteOpenHelper(context);
	}
	
	//Ôö
	public long addPerson(Person p){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", p.getName());
		values.put("age", p.getAge());
		long insert = db.insert("person", null, values);
		db.close();
		return insert;
	}
	
	//É¾
	public int deleteById(int id){
		SQLiteDatabase db = helper.getWritableDatabase();
		int delete = db.delete("person", "personid=?", new String[]{String.valueOf(id)});
		db.close();
		return delete;
	}
	
	//¸Ä
	public int update(Person p){
		SQLiteDatabase db = helper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("name", p.getName());
		values.put("age", p.getAge());
		int update = db.update("person", values, "personid=?", new String[]{String.valueOf(p.getPersonid())});
		db.close();
		return update;
	}
	
	//²é
	public List<Person> findAll(){
		List<Person> list = new ArrayList<Person>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor cursor = db.query("person", null, null, null, null, null, null);
		Person p = null;
		while(cursor.moveToNext()){
			String name = cursor.getString(cursor.getColumnIndex("name"));
			int id = cursor.getInt(cursor.getColumnIndex("personid"));
			int age = cursor.getInt(cursor.getColumnIndex("age"));
			p = new Person();
			p.setPersonid(id);
			p.setName(name);
			p.setAge(age);
			list.add(p);
		}
		cursor.close();
		db.close();
		return list;
	}
}
