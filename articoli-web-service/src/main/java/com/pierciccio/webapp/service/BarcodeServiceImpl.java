package com.pierciccio.webapp.service;

import com.pierciccio.webapp.entity.Barcode;
import com.pierciccio.webapp.repository.BarcodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BarcodeServiceImpl implements BarcodeService
{
	@Autowired
	BarcodeRepository barcodeRepository;
	
	@Override
	public Barcode SelByBarcode(String Barcode)
	{
		return barcodeRepository.findByBarcode(Barcode);
	}
}
