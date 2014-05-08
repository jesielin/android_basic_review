package com.example.internet.test;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import android.os.Environment;
import android.test.AndroidTestCase;

public class NetTest extends AndroidTestCase {
	
	//HttpUrlConnection实现http请求数据
	public void testDownload() throws Exception{
		URL url = new URL("http://photocdn.sohu.com/20100125/Img269812337.jpg");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5* 1000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() != 200) throw new RuntimeException("请求url失败");
		InputStream is = conn.getInputStream();
		File file = new File(Environment.getExternalStorageDirectory(),"pic.jpg");
		FileOutputStream outStream = new FileOutputStream(file);
		byte[] buffer = new byte[1024];
		int len = -1;
		while( (len = is.read(buffer)) != -1 ){
			outStream.write(buffer, 0, len);
		}
		outStream.close();
		is.close();

	}
	
	//多线程下载，由于代码繁琐，逻辑倒是很清晰，所以待后续补充，暂时先不写了
	public void testMultiDownload(){
		
	}
	
	//https://www.google.com.hk/search?newwindow=1&safe=strict&
	//es_sm=122&biw=1366&bih=643&tbm=isch&sa=1&q=android&oq=android&
	//gs_l=img.3...31512.32297.0.32538.7.6.0.0.0.0.0.0..0.0....0...1c.1j4.43.img..7.0.0.ToH-005lUbI#facrc=_&
	//imgrc=Enxy4lFXQURxjM%253A%3BRUNng3SLRSISaM%3Bhttp%253A%252F%252Favatar.csdn.net%252Fblogpic%252F20140222182141140.jpg%3Bhttp%253A%252F%252Fblog.csdn.net%252Fsingwhatiwanna%252Farticle%252Fdetails%252F17841165%3B960%3B854
	/*public void testRequestDownload() throws Exception{
		String requestUrl = "https://www.google.com.hk/search";
		Map<String, String> requestParams = new HashMap<String, String>();
		requestParams.put("newwindow", "1");
		requestParams.put("safe", "strict");
		requestParams.put("es_sm", "122");
		requestParams.put("biw", "1366");
		requestParams.put("bih", "643");
		requestParams.put("tbm", "isch");
		requestParams.put("sa", "1");
		requestParams.put("q", "android");
		requestParams.put("oq", "android");
		requestParams.put("imgrc", "img.3...31512.32297.0.32538.7.6.0.0.0.0.0.0..0.0....0...1c.1j4.43.img..7.0.0.ToH-005lUbI#facrc=_");
		requestParams.put("gs_l", "img.3...31512.32297.0.32538.7.6.0.0.0.0.0.0..0.0....0...1c.1j4.43.img..7.0.0.ToH-005lUbI#facrc=_");
		
		
		 StringBuilder params = new StringBuilder();
		for(Map.Entry<String, String> entry : requestParams.entrySet()){
			params.append(entry.getKey());
			params.append("=");
			params.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
			params.append("&");
		}
		if (params.length() > 0) params.deleteCharAt(params.length() - 1);
		byte[] data = params.toString().getBytes();
		URL realUrl = new URL(requestUrl);
		HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
		conn.setDoOutput(true);//发送POST请求必须设置允许输出
		conn.setUseCaches(false);//不使用Cache
		conn.setRequestMethod("POST");	        
		conn.setRequestProperty("Connection", "Keep-Alive");//维持长连接
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Content-Length", String.valueOf(data.length));
		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		DataOutputStream outStream = new DataOutputStream(conn.getOutputStream());
		outStream.write(data);
		outStream.flush();
		if( conn.getResponseCode() == 200 ){
		        String result = readAsString(conn.getInputStream(), "UTF-8");
		        outStream.close();
		        System.out.println(result);
		}
	}*/
}
