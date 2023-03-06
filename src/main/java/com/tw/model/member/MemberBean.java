package com.tw.model.member;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "member")
@EntityListeners(AuditingEntityListener.class)
@Component

public class MemberBean {
	@Id
	@Column(name = "MEMBER_NAME")
	private String member_name;


//	@Column(name = "MEMBER_FIRSTNAME")
//	private String member_firstName;
//
//	@Column(name = "MEMBER_LASTNAME")
//	private String member_lastName;

	@Column(name = "MEMBER_PLACE")
	private String member_place;

	@Column(name = "MEMBER_ROLE")
	private String member_role;

	@Column(name = "MEMBER_MAIL")
	private String member_mail;

	@Column(name = "MEMBER_IMAGENAME")
	private String member_imageName;

	@CreatedDate
	@Column(name = "MEMBER_CREATEDATETIME")
	private Date member_createDateTime;

	@Column(name = "MEMBER_IMAGE")
	private byte[] member_image;

	@Column(name = "MEMBER_STATE")
	private char member_state;

	public MemberBean() {
	}

	public String getMember_name() {
		return member_name;
	}

	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}


	public String getMember_place() {
		return member_place;
	}

	public void setMember_place(String member_place) {
		this.member_place = member_place;
	}

	public String getMember_role() {
		return member_role;
	}

	public void setMember_role(String member_role) {
		this.member_role = member_role;
	}

	public String getMember_mail() {
		return member_mail;
	}

	public void setMember_mail(String member_mail) {
		this.member_mail = member_mail;
	}

	public String getMember_imageName() {
		return member_imageName;
	}

	public void setMember_imageName(String member_imageName) {
		this.member_imageName = member_imageName;
	}

	public Date getMember_createDateTime() {
		return member_createDateTime;
	}

	public void setMember_createDateTime(Date member_createDateTime) {
		this.member_createDateTime = member_createDateTime;
	}

	public byte[] getMember_image() {
		return member_image;
	}

	public void setMember_image(byte[] member_image) {
		this.member_image = member_image;
	}

	public char getMember_state() {
		return member_state;
	}

	public void setMember_state(char member_state) {
		this.member_state = member_state;
	}

}
