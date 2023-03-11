package com.tw.model.audio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "AUDIO")
@EntityListeners(AuditingEntityListener.class)
@Component
public class AudioBean {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUDIO_ID")
	private int audio_id;
	@Column(name = "AUDIO_NAME")
	private String audio_name;
	@Column(name = "AUDIO_ORIGINALNAME")
	private String audio_originalName;
	@Column(name = "AUDIO_FILE")
	private byte[] audio_file;
	@Column(name = "AUDIO_TYPE")
	private String audio_type;
//	@ManyToOne(targetEntity = AudioBean.class)
	@JoinColumn(name = "AUDIO_MEMBER")
	private String audio_member;
	@CreatedDate
	@Column(name = "AUDIO_CREATEDATETIME")
	private Date audio_createDateTime;
	@LastModifiedDate
	@Column(name = "AUDIO_LASTDATETIME")
	private Date audio_lastDateTime;
	@Column(name = "AUDIO_CONTENT")
	private String audio_content;
	@Column(name = "AUDIO_STATE")
	private char audio_state;

	public AudioBean() {

	}

	public int getAudio_id() {
		return audio_id;
	}

	public void setAudio_id(int audio_id) {
		this.audio_id = audio_id;
	}

	public String getAudio_name() {
		return audio_name;
	}

	public void setAudio_name(String audio_name) {
		this.audio_name = audio_name;
	}
	

	public String getAudio_originalName() {
		return audio_originalName;
	}

	public void setAudio_originalName(String audio_originalName) {
		this.audio_originalName = audio_originalName;
	}

	public byte[] getAudio_file() {
		return audio_file;
	}

	public void setAudio_file(byte[] audio_file) {
		this.audio_file = audio_file;
	}
	

	public String getAudio_type() {
		return audio_type;
	}

	public void setAudio_type(String audio_type) {
		this.audio_type = audio_type;
	}

	public String getAudio_member() {
		return audio_member;
	}

	public void setAudio_member(String audio_member) {
		this.audio_member = audio_member;
	}

	public Date getAudio_createDateTime() {
		return audio_createDateTime;
	}

	public void setAudio_createDateTime(Date audio_createDateTime) {
		this.audio_createDateTime = audio_createDateTime;
	}

	public Date getAudio_lastDateTime() {
		return audio_lastDateTime;
	}

	public void setAudio_lastDateTime(Date audio_lastDateTime) {
		this.audio_lastDateTime = audio_lastDateTime;
	}

	public String getAudio_content() {
		return audio_content;
	}

	public void setAudio_content(String audio_content) {
		this.audio_content = audio_content;
	}

	public char getAudio_state() {
		return audio_state;
	}

	public void setAudio_state(char audio_state) {
		this.audio_state = audio_state;
	}

}
