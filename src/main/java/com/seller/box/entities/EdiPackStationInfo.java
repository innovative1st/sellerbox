package com.seller.box.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "EDI_PACK_STATION_INFO", catalog = "SELLER")
public class EdiPackStationInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PACK_STATION_ID")
	private Long packStationId;
	@Column(name = "PACK_STATION_NAME")
	private String packStationName;
	@Column(name = "PACK_STATION_LOCATION")
	private String packStationLocation;
	@Column(name = "PACK_STATION_HOSTNAME")
	private String packStationHostname;
	@Column(name = "PACK_STATION_MACHINENAME")
	private String packStationMachinename;
	@Column(name = "PACK_STATION_IPADDRESS")
	private String packStationIpaddress;
	@Column(name = "IS_ACTIVE")
	private int isActive;
	@Column(name = "CREATED_BY")
	private String createdBy;
	@Column(name = "CREATED_DATE")
	private Date createdDate;
	@Column(name = "MODIFIED_BY")
	private String modifiedBy;
	@Column(name = "MODIFIED_DATE")
	private Date modifiedDate;
	@Column(name = "PS_LISTEN_PORT")
	private int psListenPort;
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "PACK_STATION_ID", nullable = false)
	private Set<EdiPackStationPrinter> ediPackStationPrinter;
	public Long getPackStationId() {
		return packStationId;
	}
	public void setPackStationId(Long packStationId) {
		this.packStationId = packStationId;
	}
	public String getPackStationName() {
		return packStationName;
	}
	public void setPackStationName(String packStationName) {
		this.packStationName = packStationName;
	}
	public String getPackStationLocation() {
		return packStationLocation;
	}
	public void setPackStationLocation(String packStationLocation) {
		this.packStationLocation = packStationLocation;
	}
	public String getPackStationHostname() {
		return packStationHostname;
	}
	public void setPackStationHostname(String packStationHostname) {
		this.packStationHostname = packStationHostname;
	}
	public String getPackStationMachinename() {
		return packStationMachinename;
	}
	public void setPackStationMachinename(String packStationMachinename) {
		this.packStationMachinename = packStationMachinename;
	}
	public String getPackStationIpaddress() {
		return packStationIpaddress;
	}
	public void setPackStationIpaddress(String packStationIpaddress) {
		this.packStationIpaddress = packStationIpaddress;
	}
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public int getPsListenPort() {
		return psListenPort;
	}
	public void setPsListenPort(int psListenPort) {
		this.psListenPort = psListenPort;
	}
	public Set<EdiPackStationPrinter> getEdiPackStationPrinter() {
		return ediPackStationPrinter;
	}
	public void setEdiPackStationPrinter(Set<EdiPackStationPrinter> ediPackStationPrinter) {
		this.ediPackStationPrinter = ediPackStationPrinter;
	}
}
