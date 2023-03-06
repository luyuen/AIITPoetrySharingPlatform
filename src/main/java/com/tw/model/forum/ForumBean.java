package com.tw.model.forum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "forum")
@EntityListeners(AuditingEntityListener.class)
@Component
public class ForumBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FORUM_ID")
	private int forum_id;

	@Column(name = "FORUM_NAME")
	private String forum_name;

	@Transient
	@Column(name = "FORUM_MEMBER")
	private String forum_member;

	@CreatedDate
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "FORUM_CREATEDATETIME")
	private Date forum_createdatetime;

	@LastModifiedDate
//	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "FORUM_LASTDATETIME")
	private Date forum_lastdatetime;

	@Column(name = "FORUM_IMAGENAME")
	private String forum_imageName;

	@Column(name = "FORUM_IMAGE")
	private byte[] forum_image;

	@Column(name = "FORUM_CONTENT")
	private String forum_content;
	@Column(name = "FORUM_STATE")
	private char forum_state;
	
	public ForumBean() {

	}
	public ForumBean(String forum_name, String forum_imageName, byte[] forum_image, String forum_content, char forum_state) {
		this.forum_name = forum_name;
		this.forum_imageName = forum_imageName;
		this.forum_image = forum_image;
		this.forum_content = forum_content;
		this.forum_state = forum_state;
	}
	public int getForum_id() {
		return forum_id;
	}
	public void setForum_id(int forum_id) {
		this.forum_id = forum_id;
	}
	public String getForum_name() {
		return forum_name;
	}
	public void setForum_name(String forum_name) {
		this.forum_name = forum_name;
	}
	public String getForum_member() {
		return forum_member;
	}
	public void setForum_member(String forum_member) {
		this.forum_member = forum_member;
	}
	public Date getForum_createdatetime() {
		return forum_createdatetime;
	}
	public void setForum_createdatetime(Date forum_createdatetime) {
		this.forum_createdatetime = forum_createdatetime;
	}
	
	public Date getForum_lastdatetime() {
		return forum_lastdatetime;
	}
	public void setForum_lastdatetime(Date forum_lastdatetime) {
		this.forum_lastdatetime = forum_lastdatetime;
	}
	public String getForum_imageName() {
		return forum_imageName;
	}
	public void setForum_imageName(String forum_imageName) {
		this.forum_imageName = forum_imageName;
	}
	public byte[] getForum_image() {
		return forum_image;
	}
	public void setForum_image(byte[] forum_image) {
		this.forum_image = forum_image;
	}
	public String getForum_content() {
		return forum_content;
	}
	public void setForum_content(String forum_content) {
		this.forum_content = forum_content;
	}
	public char getForum_state() {
		return forum_state;
	}
	public void setForum_state(char forum_state) {
		this.forum_state = forum_state;
	}

	
}
