package com.bootexample.spring.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.cloudinary.StoredFile;

@Entity
public class Photo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3795638324791063833L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Basic
	private String title;

	@Basic
	private String image;

	@Basic
	private Date createAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public StoredFile getUpload() {
		StoredFile file = new StoredFile();
		file.setPreloadedFile(image);
		return file;
	}

	public void setUpload(StoredFile file) {
		this.image = file.getPreloadedFile();
	}
}
