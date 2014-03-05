package com.util;

import java.util.List;

import com.example.eventmng.data.Building;

public class Constant {
	public static String BASE_URL = "http://zakaria2.myweb.cs.uwindsor.ca/zakaria/main/index.php?";
//	public static String BASE_URL = "http://192.168.1.100/zaks/main/index.php?";
	public static String login	= BASE_URL+"login&username=%s&password=%s";
	public static String signin = BASE_URL+"username=%s&password=%s&lat=%s&long=%s&role=1";
	public static String getEvent = BASE_URL+"events&building_id=%s";
	public static String buildings = BASE_URL+"buildings";
	public static String addevent = BASE_URL+"title=%s&time=%s&building_id=%s&poster_user_id=%s";
	public static String putComment = BASE_URL+"event_id=%s&comment=%s&poster_user_id=%s";
	public static String getcomments = BASE_URL+"event_id=%s&comments";
	public static String createEven = BASE_URL+"title=%s&description=%s&time=%s&building_id=%s&poster_user_id=%s";
	
	
	public static String userId;
	public static List<Building> lstBuildings;
	
	public static double latitude = 62.2270;
	public static double longitude= 105.3809;
	
}

