package com.tw.model.briefImage;

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
@Table(name = "briefimage")
@EntityListeners(AuditingEntityListener.class)
@Component
public class BriefImageBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "BRIEFIMAGE_ID")
	private int briefImage_id;
	@Column(name = "BRIEFIMAGE_NAME")
	private String briefImage_name;
	@ManyToOne(targetEntity =BriefImageBean.class)
	@JoinColumn(name = "BRIEFIMAGE_MEMBER")
	private String briefImage_member;
	@CreatedDate
	@Column(name = "BRIEFIMAGE_CREATEDATETIME")
	private Date briefImage_createDateTime;
	@LastModifiedDate
	@Column(name = "BRIEFIMAGE_LASTDATETIME")
	private Date briefImage_lastDateTime;
	@Column(name = "BRIEFIMAGE_CONTENT")
	private String briefImage_content;
	@Column(name = "BRIEFIMAGE_STATE")
	private char briefImage_state;
	
	public BriefImageBean() {
	}

	public int getBriefImage_id() {
		return briefImage_id;
	}

	public void setBriefImage_id(int briefImage_id) {
		this.briefImage_id = briefImage_id;
	}

	public String getBriefImage_name() {
		return briefImage_name;
	}

	public void setBriefImage_name(String briefImage_name) {
		this.briefImage_name = briefImage_name;
	}

	public String getBriefImage_member() {
		return briefImage_member;
	}

	public void setBriefImage_member(String briefImage_member) {
		this.briefImage_member = briefImage_member;
	}

	public Date getBriefImage_createDateTime() {
		return briefImage_createDateTime;
	}

	public void setBriefImage_createDateTime(Date briefImage_createDateTime) {
		this.briefImage_createDateTime = briefImage_createDateTime;
	}

	public Date getBriefImage_lastDateTime() {
		return briefImage_lastDateTime;
	}

	public void setBriefImage_lastDateTime(Date briefImage_lastDateTime) {
		this.briefImage_lastDateTime = briefImage_lastDateTime;
	}

	public String getBriefImage_content() {
		return briefImage_content;
	}

	public void setBriefImage_content(String briefImage_content) {
		this.briefImage_content = briefImage_content;
	}

	public char getBriefImage_state() {
		return briefImage_state;
	}

	public void setBriefImage_state(char briefImage_state) {
		this.briefImage_state = briefImage_state;
	}
	

}
