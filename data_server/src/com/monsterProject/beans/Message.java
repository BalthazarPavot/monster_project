package com.monsterProject.beans;

public class Message {

	private String chatterName = null;
	private String textmsg = null;
	private String time;

	public Message(String name, String message, String time) {
		this.chatterName = name;
		this.textmsg = message;
		this.time = time;
	}

	public Message() {

	}

	public String getChatterName() {
		return chatterName;
	}

	public String getMessage() {
		return textmsg;
	}

	public void setChatterName(String chatterName) {
		this.chatterName = chatterName;
	}

	public void setTextmsg(String textmsg) {
		this.textmsg = textmsg;
	}

	public void setTime(String time) {
		this.time = time;
	}

}