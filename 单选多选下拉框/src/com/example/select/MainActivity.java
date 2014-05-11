package com.example.select;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

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
		private RadioGroup radioGroup;
		private List<CheckBox> checkboxs = new ArrayList<CheckBox>();
		private CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				CheckBox checkBox = (CheckBox) buttonView;
				System.out.println("isChecked=" + isChecked + ",value="
						+ checkBox.getText());// 输出单选框的值
			}
		};

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			final View rootView = inflater.inflate(R.layout.fragment_main,
					container, false);
			radioGroup = (RadioGroup) rootView.findViewById(R.id.radioGroup);
			radioGroup
					.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
						public void onCheckedChanged(RadioGroup group,
								int checkedId) {
							RadioButton radioButton = (RadioButton) rootView
									.findViewById(checkedId);
							CharSequence text = radioButton.getText();
							Toast.makeText(getActivity(), text, 0).show();
						}
					});

			checkboxs
					.add((CheckBox) rootView.findViewById(R.id.checkboxdotNet));
			checkboxs.add((CheckBox) rootView.findViewById(R.id.checkboxjava));
			checkboxs.add((CheckBox) rootView.findViewById(R.id.checkboxphp));
			for (CheckBox c : checkboxs) {
				c.setOnCheckedChangeListener(listener);
			}
			checkboxs.get(1).setChecked(true);// 设置成选中状态
			Button button = (Button) rootView.findViewById(R.id.checkboxButton);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					List<String> values = new ArrayList<String>();
					for (CheckBox box : checkboxs) {
						if (box.isChecked()) {
							values.add(box.getText().toString());
						}
					}
					Toast.makeText(getActivity(), values.toString(), 1).show();
				}
			});

			// 第二个参数为下拉列表框每一项的界面样式，该界面样式由Android系统提供，当然您也可以自定义
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
					android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			adapter.add("java");
			adapter.add("dotNet");
			adapter.add("php");
			Spinner spinner = (Spinner) rootView.findViewById(R.id.spinner);
			spinner.setAdapter(adapter);
			spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> adapterView,
						View view, int position, long id) {
					Object item = adapterView.getItemAtPosition(position);
					Toast.makeText(getActivity(), item.toString(), 0).show();
				}
				

				@Override
				public void onNothingSelected(AdapterView<?> view) {
					//Object item = view.getAdapter().getItem(position);
					//Toast.makeText(getActivity(), "Nothing choose", 0).show();
				}
			});

			return rootView;
		}
	}

}
