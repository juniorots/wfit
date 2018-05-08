package br.com.wfit.negocio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.wfit.base.ProdutoDAO;
import br.com.wfit.modelo.EnumTipoProduto;
import br.com.wfit.modelo.Produto;
import lombok.Cleanup;

public class ProdutoDAOTest {
	
	@BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

  @Test
  public void example() {
  }
    
//  @Test
  public void mainTest() {
	  
	  @Cleanup
      final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");

      @Cleanup
      final EntityManager entityManager = entityManagerFactory.createEntityManager();
      entityManager.getTransaction().begin();
//
      Produto prod = new Produto();    
      ProdutoDAO dao = new ProdutoDAO(entityManager);
//      
      prod.setSrcImagem("tmp/img-286x215-1.jpg");
      prod.setSrcImagemModal("tmp/img-640x480-1.jpg");
      prod.setTitulo("Produto 01");
      prod.setDescritivo("Descritivo 01");
      prod.setTipo(EnumTipoProduto.PRODUTO_DESTAQUE.getTipo());
      dao.insert(prod);      
      
      Produto prod2 = new Produto();   
      
      prod2.setSrcImagem("tmp/img-286x215-2.jpg");
      prod2.setSrcImagemModal("tmp/img-640x480-2.jpg");
      prod2.setTitulo("Produto 02");
      prod2.setDescritivo("Descritivo 02");
      prod2.setTipo(EnumTipoProduto.PRODUTO_DESTAQUE.getTipo());
      dao.insert(prod2);      
//      
      Produto prod3 = new Produto();   
      
      prod3.setSrcImagem("tmp/img-286x215-3.jpg");
      prod3.setSrcImagemModal("tmp/img-640x480-3.jpg");
      prod3.setTitulo("Produto 03");
      prod3.setDescritivo("Descritivo 03");
      prod3.setTipo(EnumTipoProduto.PRODUTO_DESTAQUE.getTipo());
      dao.insert(prod3);
      
      Produto prod4 = new Produto();   
      
      prod4.setSrcImagem("tmp/img-286x215-4.jpg");
      prod4.setSrcImagemModal("tmp/img-640x480-4.jpg");
      prod4.setTitulo("Produto 04");
      prod4.setDescritivo("Descritivo 04");
      prod4.setTipo(EnumTipoProduto.PRODUTO_DESTAQUE.getTipo());
      dao.insert(prod4);
      
      Produto prod5 = new Produto();   
      
      prod5.setSrcImagem("tmp/img-286x215-5.jpg");
      prod5.setSrcImagemModal("tmp/img-640x480-5.jpg");
      prod5.setTitulo("Produto 05");
      prod5.setDescritivo("Descritivo 05");
      prod5.setTipo(EnumTipoProduto.PRODUTO_DESTAQUE.getTipo());
      dao.insert(prod5);
      
      Produto prod6 = new Produto();   
      
      prod6.setSrcImagem("tmp/img-286x215-6.jpg");
      prod6.setSrcImagemModal("tmp/img-640x480-6.jpg");
      prod6.setTitulo("Produto 06");
      prod6.setDescritivo("Descritivo 06");
      prod6.setTipo(EnumTipoProduto.PRODUTO_DESTAQUE.getTipo());
      dao.insert(prod6);
      
      Produto prod7 = new Produto();   
      
      prod7.setSrcImagem("tmp/img-286x215-7.jpg");
      prod7.setTitulo("Produto 07");
      prod7.setDescritivo("Descritivo 07");
      prod7.setTipo(EnumTipoProduto.PRODUTO_LANCAMENTO.getTipo());
      dao.insert(prod7);
      
      Produto prod8 = new Produto();   
      
      prod8.setSrcImagem("tmp/img-286x215-8.jpg");
      prod8.setTitulo("Produto 08");
      prod8.setDescritivo("Descritivo 08");
      prod8.setTipo(EnumTipoProduto.PRODUTO_LANCAMENTO.getTipo());
      dao.insert(prod8);
      
      
      Produto prod9 = new Produto();   
      
      prod9.setSrcImagem("tmp/img-286x215-9.jpg");
      prod9.setTitulo("Produto 09");
      prod9.setDescritivo("Descritivo 09");
      prod9.setTipo(EnumTipoProduto.PRODUTO_LANCAMENTO.getTipo());
      dao.insert(prod9);
      
      
      entityManager.getTransaction().commit();
  }
  
}
