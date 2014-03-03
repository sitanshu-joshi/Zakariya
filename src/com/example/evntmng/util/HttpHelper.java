package com.example.evntmng.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.util.Constant;

public class HttpHelper {

	/**
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static String getLoginResposne(String username, String password) {
		String pasringString = "";
		try {
			String url = String.format(Constant.login, username, password);
			System.out.println(url);
			DefaultHttpClient client = new DefaultHttpClient();  

			HttpGet get = new HttpGet(url);
			HttpResponse responseGet = client.execute(get);  
			HttpEntity resEntityGet = responseGet.getEntity();  
			if (resEntityGet != null) {  
				// do something with the response
				String response = EntityUtils.toString(resEntityGet);
				System.out.println("GET RESPONSE"+response);
				pasringString = response;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pasringString;
	}
	/**
	 * 
	 * @param username
	 * @param password
	 * @param campus
	 * @param lat
	 * @param log
	 * @return
	 */
	public static String getSignupResposne(String username, String password, String campus, double lat, double log) {
		String pasringString = "";
		try {

			String url = String.format(Constant.login, username, password);
			System.out.println(url);
			DefaultHttpClient client = new DefaultHttpClient();  

			HttpGet get = new HttpGet(url);
			HttpResponse responseGet = client.execute(get);  
			HttpEntity resEntityGet = responseGet.getEntity();  
			if (resEntityGet != null) {  
				// do something with the response
				String response = EntityUtils.toString(resEntityGet);
				System.out.println("GET RESPONSE"+response);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pasringString;
	}
	
	/**
	 * 
	 * @return
	 */
	public static String getBuildings(){
		String pasringString = "";
		try{
			DefaultHttpClient client = new DefaultHttpClient();  

			HttpGet get = new HttpGet(Constant.buildings);
			HttpResponse responseGet = client.execute(get);  
			HttpEntity resEntityGet = responseGet.getEntity();  
			if (resEntityGet != null) {  
				// do something with the response
				String response = EntityUtils.toString(resEntityGet);
				System.out.println("GET RESPONSE"+response);
				pasringString = response;
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return pasringString;
	}
	/**
	 * 
	 * @return
	 */
	public static String getEventList(){
		String pasringString = "";
		try{
			DefaultHttpClient client = new DefaultHttpClient();  

			HttpGet get = new HttpGet(Constant.getEvent);
			HttpResponse responseGet = client.execute(get);  
			HttpEntity resEntityGet = responseGet.getEntity();  
			if (resEntityGet != null) {  
				// do something with the response
				String response = EntityUtils.toString(resEntityGet);
				System.out.println("GET RESPONSE"+response);
				pasringString = response;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pasringString;

	}
	public static String getComment(String eventId, String comment, String userid){
		String pasringString = "";
		try{
			DefaultHttpClient client = new DefaultHttpClient();  
			String url = String.format(Constant.putComment, eventId, comment, userid);
			System.out.println(url);
			HttpGet get = new HttpGet(url);
			HttpResponse responseGet = client.execute(get);  
			HttpEntity resEntityGet = responseGet.getEntity();  
			if (resEntityGet != null) {  
				// do something with the response
				String response = EntityUtils.toString(resEntityGet);
				System.out.println("GET RESPONSE"+response);
				pasringString = response;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pasringString;
	}

	public static String getcommentsFromId(String id){
		String pasringString = "";
		try{
			DefaultHttpClient client = new DefaultHttpClient();  
			String url = String.format(Constant.getcomments, id);
			System.out.println(url);
			HttpGet get = new HttpGet(url);
			HttpResponse responseGet = client.execute(get);  
			HttpEntity resEntityGet = responseGet.getEntity();  
			if (resEntityGet != null) {  
				// do something with the response
				String response = EntityUtils.toString(resEntityGet);
				System.out.println("GET RESPONSE"+response);
				pasringString = response;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return pasringString;
	}
	
	/**
	 * THis will convert HttpResponse into real response Body.
	 * @param httpResponse
	 * @return
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private static String getResponse(HttpResponse httpResponse) throws IllegalStateException, IOException{
		HttpEntity httpEntity  = httpResponse.getEntity();
		InputStream inputStream = httpEntity.getContent();
		String responsMessage  = convertStreamToString(inputStream);		
		return responsMessage;
	}
	/**
	 * This will return response as String.
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static String convertStreamToString(InputStream is)
			throws IOException {
		/*
		 * To convert the InputStream to String we use the
		 * Reader.read(char[] buffer) method. We iterate until the
		 * Reader return -1 which means there's no more data to
		 * read. We use the StringWriter class to produce the string.
		 */
		if (is != null) {
			Writer writer = new StringWriter();
			char[] buffer = new char[8*1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				is.close();
			}
			return writer.toString();
		} else {        
			return "";
		}
	}
}
