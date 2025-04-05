package org.wh.reception.entites;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Dimension implements Serializable{

		private static final long serialVersionUID = 4022394393168982324L;
		
		@Id
		@Column(name="dimension_id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		@Column(name="dimension_label",length=30,nullable = false,unique = true)
		private String labelDimension;
		@Column(nullable = false)
		private double width;
		@Column(nullable = false)
		private double length;
		
		public Dimension() {
		}

		public Dimension(String labelDimension, double width, double length) {
			this.labelDimension = labelDimension;
			this.width = width;
			this.length = length;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getLabelDimension() {
			return labelDimension;
		}

		public void setLabelDimension(String labelDimension) {
			this.labelDimension = labelDimension;
		}

		public double getWidth() {
			return width;
		}

		public void setWidth(double width) {
			this.width = width;
		}

		public double getLength() {
			return length;
		}

		public void setLength(double length) {
			this.length = length;
		}

		@Override
		public String toString() {
			return "Dimension [id=" + id + ", labelDimension=" + labelDimension + "]";
		}	

}
