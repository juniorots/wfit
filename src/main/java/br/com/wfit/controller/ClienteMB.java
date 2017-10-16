package br.com.wfit.controller;

import br.com.wfit.base.ClienteDAO;
import br.com.wfit.modelo.Cliente;
import br.com.wfit.util.Constantes;
import br.com.wfit.util.EnviarEmail;
import br.com.wfit.util.Util;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import lombok.Cleanup;

/**
 *
 * @author Jose Alves
 */
@ManagedBean
@RequestScoped
public class ClienteMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private Cliente cliente= new Cliente();
    private Collection<Cliente> listaCliente = new ArrayList();
    
    /**
     * Creates a new instance of ClienteMB
     */
    public ClienteMB() {
    	cliente = Util.captarClienteSessao();
        if ( Util.isEmpty( getCliente() ) ) {
        	cliente = new Cliente();
        }
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Collection<Cliente> getListaCliente() {
        if (listaCliente == null) {
            return new ArrayList<Cliente>();
        }
    
        return listaCliente;
    }

    public void setListaCliente(Collection<Cliente> listaCliente) {
        this.listaCliente = listaCliente;
    }

    /**
     * Responsavel por alterar as informacoes do Cliente logado
     */
    public void alterarCliente() {
        FacesMessage mensagem = null;
        
        if ( !validarDados() ) return;
        
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        ClienteDAO dao = new ClienteDAO(entityManager);
        getCliente().setSenha( Util.cifrar( getCliente().getSenha() ) );
        Cliente usAlterado = dao.update( getCliente() );
        entityManager.getTransaction().commit();
        
        Util.montarMensagem(FacesMessage.SEVERITY_INFO, "Dados alterados com sucesso.");
        Util.gravarClienteSessao( usAlterado );
        setCliente ( Util.captarClienteSessao() );
        
//        mensagem = new FacesMessage(FacesMessage.SEVERITY_INFO, "Hum...", "descritivo aqui...");
//        RequestContext.getCurrentInstance().showMessageInDialog(mensagem);
    }
    
    /**
     * Responsavel por persistir as informacoes digitadas na base
     */
    public void salvarCliente() {
        
        if ( !validarDados() ) return;
        
        if ( !continuarRegistro( getCliente() ) ) {
            Util.montarMensagem(FacesMessage.SEVERITY_ERROR, "Falha no cadastro. E-mail já registrado no sistema.");
            return;
        }
        
        @Cleanup
        final EntityManagerFactory entityFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entManager = entityFactory.createEntityManager();
        entManager.getTransaction().begin();
        ClienteDAO dao = new ClienteDAO(entManager);
        
//        getCliente().setSenha( Util.cifrar( getCliente().getSenha() ) );
        Cliente usInserido = dao.insert( getCliente() );
        entManager.getTransaction().commit();
        
        if ( !Util.isEmpty( usInserido.getId() ) ) {
            Util.montarMensagem(FacesMessage.SEVERITY_INFO, "Usuário cadastrado com sucesso.");
            Util.gravarClienteSessao( usInserido );
            setCliente( Util.captarClienteSessao() );
        } else {
            Util.montarMensagem(FacesMessage.SEVERITY_ERROR, "Falha no cadastro. Operação cancelada.");
        }
    }
    
    /**
     * Responsavel por enviar o e-mail com a mensagem do cliente e registra-lo na base de dados.
     */
    public void enviarNotificacao() {
    	
    	if (bloquearEnvioNotificacao( getCliente() )) {
    		Util.montarMensagem(FacesMessage.SEVERITY_INFO, "É necessário preencher todos os campos.");
    		return;
    	}
    	
    	@Cleanup
        final EntityManagerFactory entityFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entManager = entityFactory.createEntityManager();
        entManager.getTransaction().begin();
        ClienteDAO dao = new ClienteDAO(entManager);
        Cliente usInserido = dao.insert( getCliente() );
        
        entManager.getTransaction().commit();
        
        //TO-DO: CONTINUAR IMPLEMENTACAO DE ENVIO DE EMAIL AQUI... CLASSE: EnviarEmail
        
    }
    
    /**
     * Verifica a necessidade de preenchimento dos campos
     * @return
     */
    public boolean bloquearEnvioNotificacao(Cliente cliente) {
    	return Util.isEmpty(cliente.getEmail()) || Util.isEmpty(cliente.getNome()) || Util.isEmpty(cliente.getMensagem()); 
    }
    
    /*
     * Validando dados inseridos
     */
    public boolean validarDados() {
        if ( !getCliente().getSenha().equalsIgnoreCase( getCliente().getConfirmaSenha() )) {
            Util.montarMensagem(FacesMessage.SEVERITY_ERROR, "Falha no processo. Senhas diferentes");
            return false; // fail! :-(
        }
        
        if ( !validarEmail() ) return false; // fail! :-(
        
        return true; // passou! :-)
    }
    
    /**
     * Tratando da validacao de emails...
     * @return 
     */
    public boolean validarEmail() {
        if ( !Util.validarEmail( getCliente().getEmail() ) ) {
            Util.montarMensagem(FacesMessage.SEVERITY_ERROR, "Falha no processo. E-mail invalido");
            return false; // fail! :-(
        }
        return true; // acerto! :-)
    }
    
    /**
     * Responsavel por verificar se o e-mail ja nao esta inserido na base de dados
     * @param Cliente
     * @return 
     */
    private boolean continuarRegistro(Cliente cliente) {
        
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        ClienteDAO dao = new ClienteDAO(entityManager);
        return Util.isEmpty( dao.findByStringField("email", cliente.getEmail(), true, 0, 1) );
    }
    
    /**
     * Credenciando Cliente
     */
    public void validarCliente() {

        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        ClienteDAO dao = new ClienteDAO(entityManager);
        
        HashMap<String, String> campos = new HashMap();
        campos.put("email", getCliente().getEmail() );
        campos.put("senha", Util.cifrar( getCliente().getSenha() ) );

        ArrayList<Cliente> retorno = (ArrayList<Cliente>) dao.findByStringFields(campos, true, 0, 1);
        
        if (!Util.isEmpty( retorno ) ) {
            Cliente retornoCliente = retorno.get(0);
            Util.gravarClienteSessao( retornoCliente );
            setCliente( retornoCliente );
        } else {
            getCliente().setEmail("");
            Util.montarMensagemModal(FacesMessage.SEVERITY_ERROR, "Vixi...", "E-mail ou Senha inválidos.");
        }
    }
    
    /**
     * Tratando da solicitacao de recuperacao de conta
     */
    public void recuperarConta() {
        
        FacesMessage mensagem = null;
        
        if ( !validarEmail() ) return;
        
        @Cleanup
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("databaseDefault");
        
        @Cleanup
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        
        ClienteDAO dao = new ClienteDAO(entityManager);
        
        HashMap<String, String> campos = new HashMap();
        HashMap<String, Date> campoData = new HashMap();
        campos.put("email", getCliente().getEmail() );
        campoData.put("dtNascimento", getCliente().getDtNascimento() );
        
        Cliente retorno = (Cliente) dao.findByStringDateOperatorEqual(campos, campoData, true, 0, 1);
        
        if ( !Util.isEmpty( retorno ) ) {
    
            Random random = new Random();
            String novaSenha = Util.cifrarRecuperacao( String.valueOf( random.nextInt( 1000000 ) ) );
            retorno.setSenha( Util.cifrar( novaSenha ) );
            
            dao.update( retorno );
            entityManager.getTransaction().commit();
            
            ArrayList emails = new ArrayList();
            emails.add( retorno.getEmail() );
            
            EnviarEmail.recuperarSenha(emails, novaSenha);
            
            Util.montarMensagem(FacesMessage.SEVERITY_INFO, "Uma senha automática fora enviado para o e-mail informado, <br />"
                    + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;após a sua validação procure alterá-la.");
        } else {
            Util.montarMensagem(FacesMessage.SEVERITY_ERROR, "Informações inexistentes em nossa base de dados, favor tentar novamente.");
        }
        cliente = new Cliente();
    }
    
    /**
     * Tratando do fechamento da sessao aberta pelo Cliente
     * @return 
     */
    public void sairSistema() {
        setCliente( new Cliente() );
        Util.gravarClienteSessao( getCliente() );
        Util.forward( Constantes.INICIO_SISTEMA );
    }
}
