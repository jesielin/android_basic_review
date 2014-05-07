package com.example.data__contentprovider.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

public class PersonProvider extends ContentProvider {
	private static final String AUTHORITY = "com.example.data__contentprovider.provider.data";
	private static UriMatcher uriMatcher;
	private DataSqliteOpenHelper helper;
	
	private static final int PRESON_INSERT_CODE = 0;	// 操作person表添加的操作的uri匹配码
	private static final int PERSON_DELETE_CODE = 1;
	private static final int PERSON_UPDATE_CODE = 2;
	private static final int PERSON_QUERY_ALL_CODE = 3;
	private static final int PERSON_QUERY_ITEM_CODE = 4;
	
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		
		// 添加一些uri(分机号)
		
		// content://com.itheima28.sqlitedemo.providers.PersonContentProvider/person/insert
		uriMatcher.addURI(AUTHORITY, "person/insert", PRESON_INSERT_CODE);
		
		// content://com.itheima28.sqlitedemo.providers.PersonContentProvider/person/delete
		uriMatcher.addURI(AUTHORITY, "person/delete", PERSON_DELETE_CODE);

		// content://com.itheima28.sqlitedemo.providers.PersonContentProvider/person/update
		uriMatcher.addURI(AUTHORITY, "person/update", PERSON_UPDATE_CODE);
		
		// content://com.itheima28.sqlitedemo.providers.PersonContentProvider/person/queryAll
		uriMatcher.addURI(AUTHORITY, "person/queryAll", PERSON_QUERY_ALL_CODE);
		
		// content://com.itheima28.sqlitedemo.providers.PersonContentProvider/person/query/#
		uriMatcher.addURI(AUTHORITY, "person/query/#", PERSON_QUERY_ITEM_CODE);
	}
	@Override
	public boolean onCreate() {
		helper = new DataSqliteOpenHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		switch(uriMatcher.match(uri)){
		case PERSON_QUERY_ALL_CODE:
			break;
		case PERSON_QUERY_ITEM_CODE:
			break;
		}
		return null;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		case PERSON_QUERY_ALL_CODE: // 返回多条的MIME-type
			return "vnd.android.cursor.dir/person";
		case PERSON_QUERY_ITEM_CODE: // 返回单条的MIME-TYPE
			return "vnd.android.cursor.item/person";
		default:
			break;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if(PRESON_INSERT_CODE == uriMatcher.match(uri)){
			
		}
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		if(PERSON_DELETE_CODE == uriMatcher.match(uri)){
			
		}
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		if(PERSON_UPDATE_CODE == uriMatcher.match(uri)){
			
		}
		return 0;
	}

}
