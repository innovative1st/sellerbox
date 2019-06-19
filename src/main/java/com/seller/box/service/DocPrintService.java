package com.seller.box.service;

import java.util.List;
import java.util.Properties;

import com.seller.box.core.ManifestResponse;

public interface DocPrintService {
	public Properties printPackingSlip(ManifestResponse response, String filepath);
	public Properties printShipLabel(ManifestResponse response, String filepath);
	public Properties printGiftNoteCard(ManifestResponse response, List<String> giftNoteCards);
}
