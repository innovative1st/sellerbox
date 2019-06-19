package com.seller.box.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EDI_BOX_TYPE", catalog = "SELLER")
public class EdiBoxType implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "BOX_ID")
	private Long boxId;
	@Column(name = "BOX_NAME")
	private String boxName;
	@Column(name = "BOX_TYPE")
	private String boxType;
	@Column(name = "BOX_SIZE")
	private String boxSize;
	@Column(name = "INTERNAL_LENGTH_CM")
	private double internalLengthCM;
	@Column(name = "INTERNAL_WIDTH_CM")
	private double internalWidthCM;
	@Column(name = "INTERNAL_HEIGHT_CM")
	private double internalHeigthCM;
	@Column(name = "EXTERNAL_LENGTH_CM")
	private double externalLengthCM;
	@Column(name = "EXTERNAL_WIDTH_CM")
	private double externalWidthCM;
	@Column(name = "EXTERNAL_HEIGHT_CM")
	private double externalHeightCM;
	@Column(name = "LENGTH_IN")
	private double lengthIN;
	@Column(name = "WIDTH_IN")
	private double widthIN;
	@Column(name = "HEIGHT_IN")
	private double heightIN;
	@Column(name = "WEIGHT_KG")
	private double weightKG;
	public Long getBoxId() {
		return boxId;
	}
	public void setBoxId(Long boxId) {
		this.boxId = boxId;
	}
	public String getBoxName() {
		return boxName;
	}
	public void setBoxName(String boxName) {
		this.boxName = boxName;
	}
	public String getBoxType() {
		return boxType;
	}
	public void setBoxType(String boxType) {
		this.boxType = boxType;
	}
	public String getBoxSize() {
		return boxSize;
	}
	public void setBoxSize(String boxSize) {
		this.boxSize = boxSize;
	}
	public double getInternalLengthCM() {
		return internalLengthCM;
	}
	public void setInternalLengthCM(double internalLengthCM) {
		this.internalLengthCM = internalLengthCM;
	}
	public double getInternalWidthCM() {
		return internalWidthCM;
	}
	public void setInternalWidthCM(double internalWidthCM) {
		this.internalWidthCM = internalWidthCM;
	}
	public double getInternalHeigthCM() {
		return internalHeigthCM;
	}
	public void setInternalHeigthCM(double internalHeigthCM) {
		this.internalHeigthCM = internalHeigthCM;
	}
	public double getExternalLengthCM() {
		return externalLengthCM;
	}
	public void setExternalLengthCM(double externalLengthCM) {
		this.externalLengthCM = externalLengthCM;
	}
	public double getExternalWidthCM() {
		return externalWidthCM;
	}
	public void setExternalWidthCM(double externalWidthCM) {
		this.externalWidthCM = externalWidthCM;
	}
	public double getExternalHeightCM() {
		return externalHeightCM;
	}
	public void setExternalHeightCM(double externalHeightCM) {
		this.externalHeightCM = externalHeightCM;
	}
	public double getLengthIN() {
		return lengthIN;
	}
	public void setLengthIN(double lengthIN) {
		this.lengthIN = lengthIN;
	}
	public double getWidthIN() {
		return widthIN;
	}
	public void setWidthIN(double widthIN) {
		this.widthIN = widthIN;
	}
	public double getHeightIN() {
		return heightIN;
	}
	public void setHeightIN(double heightIN) {
		this.heightIN = heightIN;
	}
	public double getWeightKG() {
		return weightKG;
	}
	public void setWeightKG(double weightKG) {
		this.weightKG = weightKG;
	}
}
