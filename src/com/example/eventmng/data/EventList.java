package com.example.eventmng.data;

public class EventList {
	private String id;
	private String title;
	private String time;
	private String description;
	private String buildingId;
	private String posterUserId;
	private String buildingTitle;
	private String latitude;
	private String longitude;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getBuildingId() {
		return buildingId;
	}
	public void setBuildingId(String buildingId) {
		this.buildingId = buildingId;
	}
	public String getPosterUserId() {
		return posterUserId;
	}
	public void setPosterUserId(String posterUserId) {
		this.posterUserId = posterUserId;
	}
	public String getBuildingTitle() {
		return buildingTitle;
	}
	public void setBuildingTitle(String buildingTitle) {
		this.buildingTitle = buildingTitle;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Override
	public String toString() {
		return "EventList [id=" + id + ", title=" + title + ", time=" + time
				+ ", description=" + description + ", buildingId=" + buildingId
				+ ", posterUserId=" + posterUserId + ", buildingTitle="
				+ buildingTitle + ", latitude=" + latitude + ", longitude="
				+ longitude + "]";
	}	
}
