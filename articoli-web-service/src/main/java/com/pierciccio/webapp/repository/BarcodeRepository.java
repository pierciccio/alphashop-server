package com.pierciccio.webapp.repository;

import com.pierciccio.webapp.entity.Barcode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarcodeRepository extends JpaRepository<Barcode, String>
{
	Barcode findByBarcode(String Barcode);
}
