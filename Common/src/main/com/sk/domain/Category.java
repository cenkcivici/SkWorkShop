package com.sk.domain;import javax.persistence.Basic;import javax.persistence.Column;import javax.persistence.Entity;import javax.persistence.Table;@Entity@Table(name = "category")public class Category extends BaseEntity{	private static final long serialVersionUID = 8491496935318382561L;	@Basic	@Column(length=150)	private String name;	@Basic	@Column(length=500)	private String url;	@Basic	@Column(length=1000)	private String description;	public String getUrl() {		return url;	}	public String getName() {		return name;	}	public void setName(String name) {		this.name = name;			}	public void setUrl(String url) {		this.url = url;	}	public void setDescription(String description) {		this.description = description;	}	public String getDescription() {		return description;	}}