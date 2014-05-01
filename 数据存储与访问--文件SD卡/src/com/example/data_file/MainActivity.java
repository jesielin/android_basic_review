package com.example.data_file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		
		@ViewInject(R.id.et_in)
		EditText et_in;
		@ViewInject(R.id.et_out)
		EditText et_out;
		
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			ViewUtils.inject(this,rootView);
			
			return rootView;
		}
		
		//存储数据
		@OnClick(R.id.bn_save)
		public void save(View view){
			Editable in = et_in.getText();
			if(TextUtils.isEmpty(in)){
				Toast.makeText(getActivity(), "数据不能为空", 0).show();
				return;
			}
			try {
				String externalStorageState = Environment.getExternalStorageState();
				if(Environment.MEDIA_UNMOUNTED.equals(externalStorageState)){
					Toast.makeText(getActivity(), "未找到SD卡", 0).show();
					return;
				}
				File externalStorageDirectory = Environment.getExternalStorageDirectory();
				File outFile = new File(externalStorageDirectory,"itcast.txt");
				PrintWriter pw = new PrintWriter(new FileOutputStream(outFile),true);
				pw.println(in);
				pw.close();
				Toast.makeText(getActivity(), "保存成功", 0).show();
				et_in.setText("");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		//获取数据
		@OnClick(R.id.bn_get)
		public void get(View view){
			try {
				String externalStorageState = Environment.getExternalStorageState();
				if(Environment.MEDIA_UNMOUNTED.equals(externalStorageState)){
					Toast.makeText(getActivity(), "未找到SD卡", 0).show();
					return;
				}
				File externalStorageDirectory = Environment.getExternalStorageDirectory();
				File inFile = new File(externalStorageDirectory,"itcast.txt");
				BufferedReader bufr = new BufferedReader(new FileReader(inFile));
				String line = null;
				et_out.setText("");
				while((line = bufr.readLine()) != null){
					et_out.append(line+"\n");
				}
				bufr.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
