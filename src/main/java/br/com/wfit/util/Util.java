/**
 * Vendas de Kits chiques.
 * 
 * @author Jose Alves
 */

package br.com.wfit.util;

import br.com.wfit.modelo.Cliente;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jose Alves
 */
public class Util {
    
    /**
     * Capta a data no formato, especificado pelo Cliente
     * caso nao seja especificado um formato valido
     * ser utilizado o padrao: dd/MM/yyyy
     * 
     * @return 
     */
    public static String captarDataFormatada ( Date data, String padrao ) {
        StringBuilder sb = new StringBuilder();
        SimpleDateFormat formato = null;
        if ( isEmpty (padrao) ) {
            formato  = new SimpleDateFormat("dd/MM/yyyy");        
        } else {
            formato  = new SimpleDateFormat( padrao );        
        }
        sb.append( formato.format(data) );
        return sb.toString();
    }
   
    /*
     * Montando a mensagem estilo balao...
     */
    public static void montarMensagem(FacesMessage.Severity tipo, String mensagem) {
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            context.addMessage(null, new FacesMessage(tipo, mensagem, "") );
            context.getExternalContext().getFlash().setKeepMessages(true);
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(((HttpServletRequest) ec.getRequest()).getRequestURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /*
    * Montar mensagem no centro da tela
    */
    public static void montarMensagemModal(FacesMessage.Severity tipo, String titulo, String corpo) {
        FacesMessage mensagem = new FacesMessage(tipo, titulo, corpo);
        RequestContext.getCurrentInstance().showMessageInDialog(mensagem);
    }
    
    /*
     * Verificando existencia de valor vazio para montar o link
     */
    public static String linkTacoVazio(String arg) {
        if ( Util.isEmpty(arg) ) {
            arg = montarLink(arg, "5");
        } else {
            if ( "*".trim().equalsIgnoreCase( arg )) {
                arg = montarLink(arg, "4");
            }
        }
        return arg;
    }
    
    /**
     * Montando o link para especificacao de nota de rodape
     * @param descricao
     * @param codMensagem
     * @return 
     */
    public static String montarLink ( String descricao, String... codMensagem ) {
        StringBuilder retorno = new StringBuilder();
        String tmp = "";

        for (String cod : codMensagem) {
            tmp += cod;
            if(codMensagem.length > 1) 
                cod += ",";
        }
        if ( !Util.isEmpty(descricao) )
            retorno.append(descricao);
        else 
            retorno.append("[vazio]");
        retorno.append(" <nota>"+ tmp +"</nota>");
        return retorno.toString();
    }
    
    /**
     * Especificado para montar a nota de rodape...
     * @param codigoMensagem
     * @return 
     */
    public static String montarDescricaoLink( String codigoMensagem ) {
        StringBuilder retorno = new StringBuilder();
//        retorno.append(Constantes.notas.get(codigoMensagem.trim()));
        return retorno.toString();
    }
    
   /*
    * Verificando se o Cliente esta logado no sistema.
    */
    public static boolean isClienteLogado() {
        if ( Util.isEmpty( Util.captarClienteSessao() ) ) {
            FacesMessage mensagem = new FacesMessage(FacesMessage.SEVERITY_ERROR, "É porque não dá mesmo!", 
                    "Ops... Identifiquei que você não entrou no sistema!!<br /> "
                            + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"
                            + "Preciso que você faça isso, para liberar a consulta!!");
            RequestContext.getCurrentInstance().showMessageInDialog(mensagem);
            return false;
        } else {
            return true;
        }
    }
    
    /*
     * redirecionando...
     */
    public static void forward( String caminho ) {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect( ((HttpServletRequest) ec.getRequest()).getContextPath() + caminho );            
            ec.redirect( ((HttpServletRequest) ec.getRequest()).getRequestURI() );            
        } catch ( Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Trabalha na formatacao da data no formato yyyy-MM-dd
     * @param data
     * @return 
     */
    public static Date formatarDataBanco ( String pData ) {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date data = null;
        try {
            data = new Date( formato.parse(pData).getTime() );
        } catch (ParseException pe) {
            pe.printStackTrace();
        }
        return data;
    }
    
    public static String cifrar(String texto) {
        StringBuilder sb = new StringBuilder();
        
        if ( isEmpty( texto )) {
            return "";
        }
        
        try {
            MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
            byte message[] = algoritmo.digest(texto.getBytes("ISO-8859-1"));
            for (byte b : message) {
                sb.append( String.format("%02X",0xFF & b) );
            }
        } catch (NoSuchAlgorithmException ae) {
            ae.printStackTrace();
        } catch (UnsupportedEncodingException ue) {
            ue.printStackTrace();
        }
        return sb.toString();
    }
    
    /**
     * Dever-se-a ser utilizado para recuperacao de senha, quando o Cliente solicitar tal funcionalidade
     * @param texto
     * @return 
     */
    public static String cifrarRecuperacao (String texto) {
        StringBuilder sb = new StringBuilder();
        
        if ( isEmpty( texto )) {
            return "";
        }
        try {
            MessageDigest algoritmo = MessageDigest.getInstance("MD5");
            byte message[] = algoritmo.digest(texto.getBytes("ISO-8859-1"));
            
            sb.append(message);
        }  catch (NoSuchAlgorithmException ae) {
            ae.printStackTrace();
        } catch (UnsupportedEncodingException ue) {
            ue.printStackTrace();
        }
        
        return sb.toString();
    }
    
    /**
     * Tratando da valicao de e-mail
     * @param email
     * @return 
     */
    public static boolean validarEmail( String email ) {
        String regex = "[a-z._-].+@[a-z.]+";
        return email.matches(regex);
    }
    
    
    /**
     * Verificando objeto nulo
     * @param obj
     * @return 
     */
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;
        } else if (obj instanceof String && ((String) obj).trim().equals("") ) {
            return true;
        } else if (obj instanceof Collection && ((Collection) obj).size() == 0)  {
            return true;
        } else {
            return false;
        }
    }
    
   /*
    * Gravando o Cliente na sessao
    */
    public static void gravarClienteSessao(Cliente cliente) {
        FacesContext fc =   FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) fc.getExternalContext().getSession(false);
        sessao.setAttribute( "CODIGO_CLIENTE", cliente.getId() );
//        sessao.setAttribute( "NOME_Cliente", cliente.getNome() );
//        sessao.setAttribute( "NOME_TITULO", cliente.getNomeTitulo() );
//        sessao.setAttribute( "EMAIL_Cliente", cliente.getEmail() );
//        sessao.setAttribute( "TELEFONE_Cliente", cliente.getTelefone() );
//        sessao.setAttribute( "DT_NASCIMENTO_Cliente", cliente.getDtNascimento() );
    }
    
   /*
    * Captando o usuário da sessao
    */
    public static Cliente captarClienteSessao() {
        Cliente cliente = null;
        ExternalContext external =  FacesContext.getCurrentInstance().getExternalContext();
        HttpSession sessao = (HttpSession) external.getSession(true);
        
        if ( !isEmpty(sessao.getAttribute("CODIGO_CLIENTE") ) ) {
            cliente = new Cliente();
            cliente.setId( (UUID) sessao.getAttribute("CODIGO_CLIENTE") );
//            cliente.setNome( (String) sessao.getAttribute("NOME_Cliente") );
//            cliente.setNomeTitulo( (String) sessao.getAttribute("NOME_TITULO") );
//            cliente.setEmail( (String) sessao.getAttribute("EMAIL_Cliente") );
//            cliente.setTelefone( (String) sessao.getAttribute("TELEFONE_Cliente") );
//            cliente.setDtNascimento( (Date) sessao.getAttribute("DT_NASCIMENTO_Cliente") );
        }
        
        return cliente;
    }
    
    /*
    * Gravando o Cliente na sessao
    
    public static void gravarConcursoSessao(Concurso concurso) {
        FacesContext fc =   FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) fc.getExternalContext().getSession(false);
        sessao.setAttribute( "CODIGO_CONCURSO", concurso.getId() );
        sessao.setAttribute( "NOME_CONCURSO", concurso.getNomeConcurso());
        sessao.setAttribute( "URL_IMAGEM", concurso.getUrlImagem());
        sessao.setAttribute( "URL", concurso.getUrl() );
    }
    */
    
   /*
    * Captando o usuário da sessao
    
    public static Concurso captarConcursoSessao() {
        Concurso concurso = null;
        ExternalContext external =  FacesContext.getCurrentInstance().getExternalContext();
        HttpSession sessao = (HttpSession) external.getSession(true);
        
        if ( !isEmpty(sessao.getAttribute("CODIGO_CONCURSO") ) ) {
            concurso = new Concurso();
            concurso.setId( (UUID) sessao.getAttribute("CODIGO_CONCURSO") );
            concurso.setNomeConcurso( (String) sessao.getAttribute("NOME_CONCURSO") );
            concurso.setUrlImagem( (String) sessao.getAttribute("URL_IMAGEM") );
            concurso.setUrl( (String) sessao.getAttribute("URL") );
        }
        
        return concurso;
    }
    */
    
    /*
     * Util para limpar dados da sessao
     */
    public static void limparClienteSessao() {
       FacesContext fc =   FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) fc.getExternalContext().getSession(false);
        sessao.setAttribute("CODIGO_CLIENTE", "");
    }
    
    /*
     * Util para limpar dados da sessao
     */
    public static void limparConcursoSessao() {
       FacesContext fc =   FacesContext.getCurrentInstance();
        HttpSession sessao = (HttpSession) fc.getExternalContext().getSession(false);
        sessao.setAttribute("CODIGO_CONCURSO", "");
    }
}
