package com.example.eventmng.data;

public class Comment {
	private String id;
	private String event_id;
	private String comment;
	private String poster_user_id;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEvent_id() {
		return event_id;
	}
	public void setEvent_id(String event_id) {
		this.event_id = event_id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getPoster_user_id() {
		return poster_user_id;
	}
	public void setPoster_user_id(String poster_user_id) {
		this.poster_user_id = poster_user_id;
	}
	
	
}
