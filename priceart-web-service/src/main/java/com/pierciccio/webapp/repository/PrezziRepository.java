package com.pierciccio.webapp.repository;


import com.pierciccio.webapp.entity.DettListini;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PrezziRepository extends JpaRepository<DettListini, Long>
{
	@Modifying
	@Query(value = "DELETE FROM dettlistini WHERE CodArt = :codart AND IdList = :idlist", nativeQuery = true)
	void DelRowDettList(@Param("codart") String CodArt, @Param("idlist") String IdList);
	
	//Query JPQL
	@Query(value = "SELECT b FROM Listini a JOIN a.dettListini b WHERE b.codArt = :codart AND a.id = :idlist")
	DettListini SelByCodArtAndList(@Param("codart") String CodArt, @Param("idlist") String Listino);
}
