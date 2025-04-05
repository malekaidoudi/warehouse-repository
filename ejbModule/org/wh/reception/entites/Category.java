package org.wh.reception.entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="categories")
public class Category implements Serializable {

		private static final long serialVersionUID = 4432929065168295435L;
		@Id
		@Column(name="category_id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		@Column(name="category_label",length=30,nullable = false,unique = true)
		private String labelCategory;

		public Category() {
		}

		public Category(String labelCategory) {
			this.labelCategory = labelCategory;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

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
