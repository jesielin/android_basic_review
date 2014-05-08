package com.example.read_contact.test;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.SystemClock;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.StructuredName;
import android.provider.ContactsContract.Contacts.Data;
import android.provider.ContactsContract.RawContacts;
import android.test.AndroidTestCase;
import android.util.Log;

public class ContactTest extends AndroidTestCase {

	// ��ȡ��ϵ����Ϣ
	public void testReadContacts() throws Exception {
		Cursor cursor = getContext().getContentResolver().query(
				ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		while (cursor.moveToNext()) {
			String contactId = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts._ID));
			String name = cursor.getString(cursor
					.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

			Cursor phones = getContext().getContentResolver().query(
					ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "
							+ contactId, null, null);
			while (phones.moveToNext()) {
				String phoneNumber = phones
						.getString(phones
								.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				Log.i("RongActivity", "phoneNumber=" + phoneNumber);
			}
			phones.close();

			Cursor emails = getContext().getContentResolver().query(
					ContactsContract.CommonDataKinds.Email.CONTENT_URI,
					null,
					ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = "
							+ contactId, null, null);
			while (emails.moveToNext()) {
				// This would allow you get several email addresses
				String emailAddress = emails
						.getString(emails
								.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
				Log.i("RongActivity", "emailAddress=" + emailAddress);
			}
			emails.close();
		}
		cursor.close();
		SystemClock.sleep(3000);
	}

	/**
	 * ������RawContacts.CONTENT_URIִ��һ����ֵ���룬Ŀ���ǻ�ȡϵͳ���ص�rawContactId 
	 * ��ʱ�������data������ݣ�ֻ��ִ�п�ֵ���룬����ʹ�������ϵ����ͨѶ¼����ɼ�
	 */
	public void testAddContact() throws Exception {
		ContentValues values = new ContentValues();
		// ������RawContacts.CONTENT_URIִ��һ����ֵ���룬Ŀ���ǻ�ȡϵͳ���ص�rawContactId
		Uri rawContactUri = this.getContext().getContentResolver()
				.insert(RawContacts.CONTENT_URI, values);
		long rawContactId = ContentUris.parseId(rawContactUri);
		// ��data������������
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE);// ��������
		values.put(StructuredName.GIVEN_NAME, "����ɽ");
		this.getContext()
				.getContentResolver()
				.insert(android.provider.ContactsContract.Data.CONTENT_URI,
						values);
		// ��data����绰����
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE);
		values.put(Phone.NUMBER, "13921009789");
		values.put(Phone.TYPE, Phone.TYPE_MOBILE);
		this.getContext()
				.getContentResolver()
				.insert(android.provider.ContactsContract.Data.CONTENT_URI,
						values);
		// ��data����Email����
		values.clear();
		values.put(Data.RAW_CONTACT_ID, rawContactId);
		values.put(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE);
		values.put(Email.DATA, "liming@itcast.cn");
		values.put(Email.TYPE, Email.TYPE_WORK);
		this.getContext()
				.getContentResolver()
				.insert(android.provider.ContactsContract.Data.CONTENT_URI,
						values);
	}
	
	//���������������,����ͬһ��������
	public void testSave() throws Throwable{
		//�ĵ�λ�ã�reference\android\provider\ContactsContract.RawContacts.html
		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		int rawContactInsertIndex = 0;
		ops.add(ContentProviderOperation.newInsert(RawContacts.CONTENT_URI)
				.withValue(RawContacts.ACCOUNT_TYPE, null)
				.withValue(RawContacts.ACCOUNT_NAME, null)
				.build());
		//�ĵ�λ�ã�reference\android\provider\ContactsContract.Data.html
		ops.add(ContentProviderOperation.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
				.withValue(Data.MIMETYPE, StructuredName.CONTENT_ITEM_TYPE)
				.withValue(StructuredName.GIVEN_NAME, "��ޱ")
				.build());
		ops.add(ContentProviderOperation.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
				 .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
		         .withValue(Data.MIMETYPE, Phone.CONTENT_ITEM_TYPE)
		         .withValue(Phone.NUMBER, "13671323809")
		         .withValue(Phone.TYPE, Phone.TYPE_MOBILE)
		         .withValue(Phone.LABEL, "�ֻ���")
		         .build());
		ops.add(ContentProviderOperation.newInsert(android.provider.ContactsContract.Data.CONTENT_URI)
				 .withValueBackReference(Data.RAW_CONTACT_ID, rawContactInsertIndex)
		         .withValue(Data.MIMETYPE, Email.CONTENT_ITEM_TYPE)
		         .withValue(Email.DATA, "liming@itcast.cn")
		         .withValue(Email.TYPE, Email.TYPE_WORK)
		         .build());
		ContentProviderResult[] results = this.getContext().getContentResolver()
			.applyBatch(ContactsContract.AUTHORITY, ops);
		for(ContentProviderResult result : results){
			System.out.println(result.uri.toString());
		}
	}
}
