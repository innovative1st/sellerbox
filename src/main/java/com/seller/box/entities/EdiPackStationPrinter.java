package com.seller.box.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EDI_PACK_STATION_PRINTER", catalog = "SELLER")
public class EdiPackStationPrinter implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PRINTER_ID")
	private Long printerId;
	@Column(name = "PACK_STATION_ID", updatable = false, insertable = false)
	private Long packStationId;
	@Column(name = "PRINTER_TYPE")
	private String printerType;
	@Column(name = "PRINTER_NAME")
	private String printerName;
	@Column(name = "PRINTER_STATUS")
	private int printerStatus;
	@Column(name = "MEDIA_X")
	private double mediaX;
	@Column(name = "MEDIA_Y")
	private double mediaY;
	@Column(name = "MEDIA_W")
	private double mediaW;
	@Column(name = "MEDIA_H")
	private double mediaH;
	public Long getPrinterId() {
		return printerId;
	}
	public void setPrinterId(Long printerId) {
		this.printerId = printerId;
	}
	public Long getPackStationId() {
		return packStationId;
	}
	public void setPackStationId(Long packStationId) {
		this.packStationId = packStationId;
	}
	public String getPrinterType() {
		return printerType;
	}
	public void setPrinterType(String printerType) {
		this.printerType = printerType;
	}
	public String getPrinterName() {
		return printerName;
	}
	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}
	public int getPrinterStatus() {
		return printerStatus;
	}
	public void setPrinterStatus(int printerStatus) {
		this.printerStatus = printerStatus;
	}
	public double getMediaX() {
		return mediaX;
	}
	public void setMediaX(double mediaX) {
		this.mediaX = mediaX;
	}
	public double getMediaY() {
		return mediaY;
	}
	public void setMediaY(double mediaY) {
		this.mediaY = mediaY;
	}
	public double getMediaW() {
		return mediaW;
	}
	public void setMediaW(double mediaW) {
		this.mediaW = mediaW;
	}
	public double getMediaH() {
		return mediaH;
	}
	public void setMediaH(double mediaH) {
		this.mediaH = mediaH;
	}
}
