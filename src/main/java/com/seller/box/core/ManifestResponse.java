package com.seller.box.core;

import java.io.Serializable;
import java.util.Date;

public class ManifestResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long ediOrderId;
	private String shipmentId;
	private String requestId;
	private String manifestStatus;
	private String manifestErrorMessage;
	private String manifestExceptionMessage;
	private String carrierName;
	private String barcode;
	private String trackingId;
	private Date pickupDate;
	private String manifestBy;
	private String manifestDate;
	private boolean canManifest;
	private String manifestId;
	private String shiplabelFilepath;
	private String shiplabelPrintStatus;
	private String invoiceFilepath;
	private String invoicePrintStatus;
	private String giftPrintStatus;
	private String psName;
	private String psIpAddess;
	private int psListenPort;
	public Long getEdiOrderId() {
		return ediOrderId;
	}
	public void setEdiOrderId(Long ediOrderId) {
		this.ediOrderId = ediOrderId;
	}
	public String getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getManifestStatus() {
		return manifestStatus;
	}
	public void setManifestStatus(String manifestStatus) {
		this.manifestStatus = manifestStatus;
	}
	public String getManifestErrorMessage() {
		return manifestErrorMessage;
	}
	public void setManifestErrorMessage(String manifestErrorMessage) {
		this.manifestErrorMessage = manifestErrorMessage;
	}
	public String getManifestExceptionMessage() {
		return manifestExceptionMessage;
	}
	public void setManifestExceptionMessage(String manifestExceptionMessage) {
		this.manifestExceptionMessage = manifestExceptionMessage;
	}
	public String getCarrierName() {
		return carrierName;
	}
	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getTrackingId() {
		return trackingId;
	}
	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}
	public Date getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}
	public String getManifestBy() {
		return manifestBy;
	}
	public void setManifestBy(String manifestBy) {
		this.manifestBy = manifestBy;
	}
	public String getManifestDate() {
		return manifestDate;
	}
	public void setManifestDate(String manifestDate) {
		this.manifestDate = manifestDate;
	}
	public boolean isCanManifest() {
		return canManifest;
	}
	public void setCanManifest(boolean canManifest) {
		this.canManifest = canManifest;
	}
	public String getManifestId() {
		return manifestId;
	}
	public void setManifestId(String manifestId) {
		this.manifestId = manifestId;
	}
	public String getShiplabelFilepath() {
		return shiplabelFilepath;
	}
	public void setShiplabelFilepath(String shiplabelFilepath) {
		this.shiplabelFilepath = shiplabelFilepath;
	}
	public String getShiplabelPrintStatus() {
		return shiplabelPrintStatus;
	}
	public void setShiplabelPrintStatus(String shiplabelPrintStatus) {
		this.shiplabelPrintStatus = shiplabelPrintStatus;
	}
	public String getInvoiceFilepath() {
		return invoiceFilepath;
	}
	public void setInvoiceFilepath(String invoiceFilepath) {
		this.invoiceFilepath = invoiceFilepath;
	}
	public String getInvoicePrintStatus() {
		return invoicePrintStatus;
	}
	public void setInvoicePrintStatus(String invoicePrintStatus) {
		this.invoicePrintStatus = invoicePrintStatus;
	}
	public String getGiftPrintStatus() {
		return giftPrintStatus;
	}
	public void setGiftPrintStatus(String giftPrintStatus) {
		this.giftPrintStatus = giftPrintStatus;
	}
	public String getPsName() {
		return psName;
	}
	public void setPsName(String psName) {
		this.psName = psName;
	}
	public String getPsIpAddess() {
		return psIpAddess;
	}
	public void setPsIpAddess(String psIpAddess) {
		this.psIpAddess = psIpAddess;
	}
	public int getPsListenPort() {
		return psListenPort;
	}
	public void setPsListenPort(int psListenPort) {
		this.psListenPort = psListenPort;
	}
	@Override
	public String toString() {
		return "ManifestResponse [ediOrderId=" + ediOrderId + ", shipmentId=" + shipmentId + ", requestId=" + requestId
				+ ", manifestStatus=" + manifestStatus + ", manifestErrorMessage=" + manifestErrorMessage
				+ ", manifestExceptionMessage=" + manifestExceptionMessage + ", carrierName=" + carrierName
				+ ", barcode=" + barcode + ", trackingId=" + trackingId + ", pickupDate=" + pickupDate + ", manifestBy="
				+ manifestBy + ", manifestDate=" + manifestDate + ", canManifest=" + canManifest + ", manifestId="
				+ manifestId + ", shiplabelFilepath=" + shiplabelFilepath + ", shiplabelPrintStatus="
				+ shiplabelPrintStatus + ", invoiceFilepath=" + invoiceFilepath + ", invoicePrintStatus="
				+ invoicePrintStatus + ", giftPrintStatus=" + giftPrintStatus + ", psName=" + psName + ", psIpAddess="
				+ psIpAddess + ", psListenPort=" + psListenPort + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}
	
}
