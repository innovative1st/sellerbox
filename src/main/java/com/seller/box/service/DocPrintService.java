package com.seller.box.service;

import java.util.List;
import java.util.Properties;

public interface DocPrintService {
	public Properties printPackingSlip(String requestId, String ipAddress, String shipmentId, String filepath);
	public Properties printShipLabel(String requestId, String ipAddress, String shipmentId, String filepath);
	public Properties printGiftNoteCard(String requestId, String ipAddress, String shipmentId, List<String> giftNoteCards);
}
