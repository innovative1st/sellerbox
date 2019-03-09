package com.seller.box.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EDI_SHIPMENT_INVOICE", catalog = "SELLER")
public class EdiShipmentInvoice implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "PURCHASE_ORDER_NUMBER", unique = true)
	private String shipmentId;
	@Column(name = "INVOICE_FILE_PATH")
	private String invoiceFilepath;
	
	public String getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
	public String getInvoiceFilepath() {
		return invoiceFilepath;
	}
	public void setInvoiceFilepath(String invoiceFilepath) {
		this.invoiceFilepath = invoiceFilepath;
	}
}
