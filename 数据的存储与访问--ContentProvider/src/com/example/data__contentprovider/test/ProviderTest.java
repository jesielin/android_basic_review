package com.example.data__contentprovider.test;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.test.AndroidTestCase;

import com.example.data__contentprovider.provider.Person;

public class ProviderTest extends AndroidTestCase{
	
	public void testInsert() throws Exception{
		Uri uri = Uri.parse("content://com.example.data__contentprovider.provider.data/person/insert");
		Person p = new Person();
		p.setAge(13);
		p.setName("ะกร๗");
		
		ContentValues values = new ContentValues();
		values.put("age", 33);
		values.put("name", "saaaa");
		ContentResolver resolver = getContext().getContentResolver();
		
		Uri insert = resolver.insert(uri, values);
		
	}
	
	public void testQuery() throws Exception{
		Uri uri = Uri.parse("content://com.example.data__contentprovider.provider.data/person/query/1");
		ContentResolver resolver = getContext().getContentResolver();
		Cursor cursor = resolver.query(uri, null, null, null, null);
		while(cursor != null && cursor.moveToNext()){
			System.out.println("name:"+cursor.getString(cursor.getColumnIndex("name")));
			System.out.println("age:"+cursor.getInt(cursor.getColumnIndex("age")));
			System.out.println("id:"+cursor.getInt(cursor.getColumnIndex("personid")));
		}
		cursor.close();
	}
	
	public void testDelete() throws Exception{
		Uri uri = Uri.parse("content://com.example.data__contentprovider.provider.data/person/delete");
		ContentResolver resolver = getContext().getContentResolver();
		resolver.delete(uri, "personid=?", new String[]{String.valueOf(2)});
	}
	
	public void testQueryAll() throws Exception{
		Uri uri = Uri.parse("content://com.example.data__contentprovider.provider.data/person/queryAll");
		ContentResolver resolver = getContext().getContentResolver();
		Cursor cursor = resolver.query(uri, null, null, null, null);
		while(cursor != null && cursor.moveToNext()){
			System.out.println("name:"+cursor.getString(cursor.getColumnIndex("name")));
			System.out.println("age:"+cursor.getInt(cursor.getColumnIndex("age")));
			System.out.println("id:"+cursor.getInt(cursor.getColumnIndex("personid")));
		}
		SystemClock.sleep(3000);
		cursor.close();
	}
	
	public void testUpdate() throws Exception{
		Uri uri = Uri.parse("content://com.example.data__contentprovider.provider.data/person/update");
		ContentResolver resolver = getContext().getContentResolver();
		ContentValues values = new ContentValues();
		values.put("name", "AAAAAAAAAA");
		values.put("age", 1000);
		resolver.update(uri, values, "personid=?", new String[]{String.valueOf(1)});
	}
}
