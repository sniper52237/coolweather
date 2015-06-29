package com.wj.coolweather.app.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	
	public static void sendHttpRequest(final String address, final HttpCallBackListener listener) {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				URL url;
				HttpURLConnection conn = null;
				BufferedReader reader = null;
				try {
					url = new URL(address);
					conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(8000);
					conn.setReadTimeout(8000);
					
					StringBuilder sb = new StringBuilder();
					reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String line = null;
					
					while ((line = reader.readLine()) != null ) {
						sb.append(line);
					}
					
					if (listener != null) {
						listener.onFinish(sb.toString());
					}
					
				} catch (Exception e) {
					if (listener != null) {
						listener.onError(e);
					}
				} finally {
					if (reader != null) {
						try {
							reader.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					
					if (conn != null) {
						conn.disconnect();
					}
				}
			}
		}).start();
	}
}
