package com.example.dialog;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
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
	public static class PlaceholderFragment extends Fragment implements
			OnClickListener {

		private Button dialog;
		private Button single_dialog;
		private Button single_circle_dialog;
		private int single_checked = 1;
		private Button multi_dialog;
		private boolean[] checked = {false,true,true};
		private Button progress_dialog;
		private Button progress_circle_dialog;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			dialog = (Button) rootView.findViewById(R.id.dialog);
			single_dialog = (Button) rootView.findViewById(R.id.single_dialog);
			single_circle_dialog = (Button) rootView
					.findViewById(R.id.single_circle_dialog);
			multi_dialog = (Button) rootView.findViewById(R.id.multi_dialog);
			progress_dialog = (Button) rootView.findViewById(R.id.progress_dialog);
			progress_circle_dialog = (Button) rootView.findViewById(R.id.progress_circle_dialog);
			progress_circle_dialog.setOnClickListener(this);
			progress_dialog.setOnClickListener(this);
			multi_dialog.setOnClickListener(this);
			single_circle_dialog.setOnClickListener(this);
			single_dialog.setOnClickListener(this);
			dialog.setOnClickListener(this);
			return rootView;
		}

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.dialog:
				showDialog();
				break;
			case R.id.single_dialog:
				showSingleDialog();
				break;
			case R.id.single_circle_dialog:
				showSingleCircleDialog();
				break;
			case R.id.multi_dialog:
				showMultiDialog();
				break;
			case R.id.progress_dialog:
				showProgressDialog();
				break;
			case R.id.progress_circle_dialog:
				showProgressCircleDialog();
				break;
			}
		}
		
		private void showProgressCircleDialog(){
			final ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "标题", "看他转不转..");
			Timer timer = new Timer();
			TimerTask timerTast = new TimerTask() {
				
				@Override
				public void run() {
					progressDialog.dismiss();
				}
			};
			timer.schedule(timerTast, 3000);
			
		}
		
		private void showProgressDialog(){
			final ProgressDialog progressDialog = new ProgressDialog(getActivity());
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.setTitle("标题");
			progressDialog.setMessage("正文..");
			progressDialog.show();
			progressDialog.setMax(100);
			final Handler handler = new Handler(){
				public void handleMessage(android.os.Message msg) {
					progressDialog.dismiss();
					Toast.makeText(getActivity(), "完成！", 0).show();
				}
			};
			final Timer timer = new Timer();
			TimerTask timerTask = new TimerTask() {
				int i = 0;
				@Override
				public void run() {
					progressDialog.setProgress(i+=5);
					if(i > 100){
						this.cancel();
						timer.cancel();
						handler.sendEmptyMessage(0);
						//progressDialog.dismiss();
						//Toast.makeText(getActivity(), "完成！", 0).show();
					}
				}
			};
			timer.schedule(timerTask, 0, 200);
			//ProgressDialog progressDialog = ProgressDialog.show(getActivity(), "进度对话框", "看他转不转...");
		}

		private void showMultiDialog() {
			final String[] items = { "java", ".net", "php" };
			
			new AlertDialog.Builder(getActivity())
					.setCancelable(false)
					.setTitle("选择语言")
					.setMultiChoiceItems(items,
							checked,
							new DialogInterface.OnMultiChoiceClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which, boolean isChecked) {
									//if (isChecked) {
										//Toast.makeText(getActivity(),
										//		items[which],
										//		Toast.LENGTH_SHORT).show();
										checked[which] = isChecked;
									//}
								}
							})
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int j) {
									StringBuffer sb = new StringBuffer();
									for(int i = 0 ; i < checked.length ; i++){
										System.out.println(i);
										if(checked[i]){
											sb.append(items[i]+",");
										}
									}
									String text = "";
									if(sb.length()>0)
										text = sb.substring(0, sb.length()-1);
									Toast.makeText(getActivity(), text, 0).show();
									dialoginterface.dismiss();
								}
							}).show();// 显示对话框

		}

		private void showSingleCircleDialog() {
			final String[] items = { "java", ".net", "php" };
			new AlertDialog.Builder(getActivity())
					.setTitle("选择语言")
					.setSingleChoiceItems(items, single_checked,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int item) {
									Toast.makeText(getActivity(), items[item],
											Toast.LENGTH_SHORT).show();
									single_checked = item;
									dialog.cancel();
								}
							}).show();// 显示对话框

		}

		private void showSingleDialog() {
			final String[] items = { "java", ".net", "php" };
			new AlertDialog.Builder(getActivity()).setTitle("选择语言")
					.setItems(items, new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int item) {
							Toast.makeText(getActivity(), items[item],
									Toast.LENGTH_SHORT).show();
						}
					}).show();// 显示对话框
		}

		private void showDialog() {
			new AlertDialog.Builder(getActivity())
					.setTitle("是否退出")
					.setCancelable(false)
					// 设置不能通过“后退”按钮关闭对话框
					.setMessage("点击确定退出")
					.setPositiveButton("确认",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									Toast.makeText(getActivity(), "点击了确定", 0)
											.show();
								}
							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {
									Toast.makeText(getActivity(), "点击了取消", 0)
											.show();
									dialog.cancel();
								}
							}).show();// 显示对话框
		}
	}

}
