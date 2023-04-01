package com.tw.model.opus;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "opus")
@EntityListeners(AuditingEntityListener.class)
@Component
public class OpusBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OPUS_ID")
	private int opus_id;

	@Column(name = "OPUS_NAME")
	private String opus_name;

	@Column(name = "OPUS_MEMBER")
	private String opus_member;
	@CreatedDate
	@Column(name = "OPUS_CREATEDATETIME")
	private Date opus_createDateTime;
	@LastModifiedDate
	@Column(name = "OPUS_LASTDATETIME")
	private Date opus_lastDateTime;

	@Column(name = "OPUS_IMAGE")
	private byte[] opus_image;
	@Column(name = "OPUS_IMAGENAME")
	private String opus_imageName;

	@Column(name = "OPUS_AUDIO")
	private byte[] opus_audio;
	@Column(name = "OPUS_AUDIONAME")
	private String opus_audioName;

	@Column(name = "OPUS_CONTENT")
	private String opus_content;

	@Column(name = "OPUS_STATE")
	private char opus_state;

	public OpusBean() {

	}

	public OpusBean(String opus_name, String opus_imagename, byte[] opus_image, String opus_audionmae,
			byte[] opus_audio, char opus_state, String opus_content) {
		this.opus_name = opus_name;
		this.opus_imageName = opus_imagename;
		this.opus_audioName = opus_audionmae;
		this.opus_audio = opus_audio;
		this.opus_image = opus_image;
		this.opus_content = opus_content;
		this.opus_state = opus_state;

	}

	@Override
	public boolean equals(Object obj) {

		if (obj instanceof OpusBean) {
			OpusBean ob = (OpusBean) obj;
			return this.opus_image.equals(ob.opus_image);
		}
		return super.equals(obj);
	}

	public int getOpus_id() {
		return opus_id;
	}

	public void setOpus_id(int opus_id) {
		this.opus_id = opus_id;
	}

	public String getOpus_name() {
		return opus_name;
	}

	public void setOpus_name(String opus_name) {
		this.opus_name = opus_name;
	}

	public String getOpus_member() {
		return opus_member;
	}

	public void setOpus_member(String opus_member) {
		this.opus_member = opus_member;
	}

	public Date getOpus_createDateTime() {
		return opus_createDateTime;
	}

	public void setOpus_createDateTime(Date opus_creatDeateTime) {
		this.opus_createDateTime = opus_creatDeateTime;
	}

	public Date getOpus_lastDateTime() {
		return opus_lastDateTime;
	}

	public void setOpus_lastDateTime(Date opus_lastDateTime) {
		this.opus_lastDateTime = opus_lastDateTime;
	}

	public byte[] getOpus_image() {
		return opus_image;
	}

	public void setOpus_image(byte[] opus_image) {
		this.opus_image = opus_image;
	}

	public String getOpus_imageName() {
		return opus_imageName;
	}

	public void setOpus_imageName(String opus_imageName) {
		this.opus_imageName = opus_imageName;
	}

	public byte[] getOpus_audio() {
		return opus_audio;
	}

	public void setOpus_audio(byte[] opus_audio) {
		this.opus_audio = opus_audio;
	}

	public String getOpus_audioName() {
		return opus_audioName;
	}

	public void setOpus_audioName(String opus_audioName) {
		this.opus_audioName = opus_audioName;
	}

	public String getOpus_content() {
		return opus_content;
	}

	public void setOpus_content(String opus_content) {
		this.opus_content = opus_content;
	}

	public char getOpus_state() {
		return opus_state;
	}

	public void setOpus_state(char opus_state) {
		this.opus_state = opus_state;
	}

}
