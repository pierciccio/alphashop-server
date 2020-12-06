package com.pierciccio.webapp.service;

import com.pierciccio.webapp.entity.DettListini;

public interface PrezziService
{
	public DettListini SelPrezzo(String CodArt, String Listino);
	
	public void DelPrezzo(String CodArt, String IdList);

}
