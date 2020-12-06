package com.pierciccio.webapp.repository;

import com.pierciccio.webapp.entities.Promo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PromoRepository extends JpaRepository<Promo, String>
{
	Promo findByIdPromo(String IdPromo);
	
	Promo findByAnnoAndCodice(int Anno, String Codice);
	
	
	@Query("SELECT DISTINCT a FROM Promo a JOIN a.dettPromo b WHERE CURRENT_DATE BETWEEN b.inizio AND b.fine")
	List<Promo> SelPromoActive();
}
