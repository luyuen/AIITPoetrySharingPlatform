package com.tw.model.poetryImage;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;


public class PoetryImageBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PORTRYIMAGE_ID")
	private int _id;
	@Column(name = "PORTRYIMAGE_NAME")
	private String poetryImage_name;
	@ManyToOne(targetEntity = PoetryImageBean.class)
	@JoinColumn(name = "POETRYIMAGE_NAME")
	private String poetryImage_member;
	@CreatedDate
	@Column(name = "POETRYIMAGE_CREATEDATETIME")
	private Date poetryImage_createDateTime;
	@LastModifiedDate
	@Column(name = "POETRYIMAGE_LASTDATETIME")
	private Date poetryImage_lastDateTime;
	@Column(name = "POETRYIMAGE_CONTENT")
	private String poetryImage_content;
	@Column(name = "POETRUIMAGE_STATE")
	private char poetryImage_state;
	
	public PoetryImageBean() {
		
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getPoetryImage_name() {
		return poetryImage_name;
	}

	public void setPoetryImage_name(String poetryImage_name) {
		this.poetryImage_name = poetryImage_name;
	}

	public String getPoetryImage_member() {
		return poetryImage_member;
	}

	public void setPoetryImage_member(String poetryImage_member) {
		this.poetryImage_member = poetryImage_member;
	}

	public Date getPoetryImage_createDateTime() {
		return poetryImage_createDateTime;
	}

	public void setPoetryImage_createDateTime(Date poetryImage_createDateTime) {
		this.poetryImage_createDateTime = poetryImage_createDateTime;
	}

	public Date getPoetryImage_lastDateTime() {
		return poetryImage_lastDateTime;
	}

	public void setPoetryImage_lastDateTime(Date poetryImage_lastDateTime) {
		this.poetryImage_lastDateTime = poetryImage_lastDateTime;
	}

	public String getPoetryImage_content() {
		return poetryImage_content;
	}

	public void setPoetryImage_content(String poetryImage_content) {
		this.poetryImage_content = poetryImage_content;
	}

	public char getPoetryImage_state() {
		return poetryImage_state;
	}

	public void setPoetryImage_state(char poetryImage_state) {
		this.poetryImage_state = poetryImage_state;
	}
	
}
