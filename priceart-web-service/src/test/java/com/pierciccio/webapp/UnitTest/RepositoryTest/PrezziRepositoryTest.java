
package com.pierciccio.webapp.UnitTest.RepositoryTest;

import com.pierciccio.webapp.entity.DettListini;
import com.pierciccio.webapp.entity.Listini;
import com.pierciccio.webapp.repository.ListinoRepository;
import com.pierciccio.webapp.repository.PrezziRepository;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
 
@TestPropertySource(locations="classpath:application-list1.properties")
//@ContextConfiguration(classes = Application.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class PrezziRepositoryTest
{
    @Autowired
    private PrezziRepository prezziRepository;
    
    @Autowired
    private ListinoRepository listinoRepository;
    
    String IdList = "100";
    String CodArt = "002000301";
    Double Prezzo = 1.00;
    
    @Test
    @Order(1)
    public void A_testInsListino()
    {
    	Listini listinoTest = new Listini(IdList,"Listino Test 100","No");
    	
    	Set<DettListini> dettListini = new HashSet<>();
    	DettListini dettListTest = new DettListini(CodArt,Prezzo, listinoTest);
    	dettListini.add(dettListTest);
    	
    	listinoTest.setDettListini(dettListini);
    	
    	listinoRepository.save(listinoTest);
    	
    	assertThat(listinoRepository
        		.findById(IdList))
    			.isNotEmpty();
    	
    }
    
    @Test
    @Order(2)
	public void B_TestfindByCodArtAndIdList1()
	{
        
        assertThat(prezziRepository
        		.SelByCodArtAndList(CodArt, IdList))
        		.extracting(DettListini::getPrezzo)
				.isEqualTo(Prezzo);
    }
    
    @Test
    @Transactional
    @Order(3)
	public void C_TestDeletePrezzo()
	{
        
    	prezziRepository.DelRowDettList(CodArt, IdList);
    	
        assertThat(prezziRepository
        		.SelByCodArtAndList(CodArt, IdList))
        		.isNull();
    }
    
    @Test
    @Order(4)
	public void D_TestDeleteListino()
	{
    	Optional<Listini> listinoTest = listinoRepository.findById(IdList);
    	
    	listinoRepository.delete(listinoTest.get());
    	
        assertThat(prezziRepository
        		.SelByCodArtAndList(CodArt, IdList))
        		.isNull();
    }
    
    
}




















