package com.pierciccio.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pierciccio.webapp.entity.Barcode;

public interface BarcodeRepository extends JpaRepository<Barcode, String>
{
	Barcode findByBarcode(String Barcode);
}
