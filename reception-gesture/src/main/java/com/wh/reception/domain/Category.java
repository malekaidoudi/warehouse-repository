/**
 * 
 * @author WH
 * @version 1.0
 * @since 2023-10-01
 */
package com.wh.reception.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="categories")
public class Category implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String labelCategory;

	public Category() {
	}

	public Category(String labelCategory) {
		this.labelCategory = labelCategory;
	}
	
	@Id @Column(name="category_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="category_label",length=30,nullable = false,unique = true)
	public String getLabelCategory() {
		return labelCategory;
	}

	public void setLabelCategory(String labelCategory) {
		this.labelCategory = labelCategory;
	}

	@Override
	public String toString() {
		return "Categories [id=" + id + ", labelCategory=" + labelCategory + "]";
	}


}
