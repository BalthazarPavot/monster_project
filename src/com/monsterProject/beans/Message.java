package com.monsterProject.beans;


public class Message
{
	/**
	* string: le nom du chatter	
	*/
	private String chatterName = null;
		private String message = null;

	/**
	* long : la date ou le message a été envoyé
	*/
	private long time;
	
	public Message(String name, String message, long time)
	{
		this.chatterName = name;
		this.message= message;
		this.time= time;
	}
	

	public String getChatterName()
	{
		return chatterName;
	}

	public String getMessage()
	{
		return message;
	}

	public long getTime()
	{
		return time;
	}
}