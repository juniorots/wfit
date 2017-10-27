/**
 * Vendas de Kits chiques.
 * 
 * @author Jose Alves
 */

package br.com.wfit.util;

/**
 *
 * @author Jose Alves
 */
public class Constantes {
    /*
     * Util - navegacao
     */
    public static String INICIO_SISTEMA = "/index.xhtml";
    public static String HOST_NAME_GMAIL = "smtp.gmail.com";
    public static String HOST_NAME_HOTMAIL = "smtp.live.com";
    public static String ADMINISTRADOR_1 = "juniorots@gmail.com";
    public static Integer PORTA_SMTP_GMAIL = 465;
//    public static Integer PORTA_SMTP_GMAIL = 587;

   /*
    * Nota: tal usuario somente sera utilizado para envio automatico
    * de senhas, assim nao deve ser utilizado para responder qualquer questionamento
    * ou item relacionado a correspondencia que nao seja destinado ao sistema wfit.
    *
    * Para configurar o servico de envio automatico de email, faca por meio do link abaixo:
    *
    * https://www.google.com/settings/security/lesssecureapps -> permite aplicativos de terceiros logar na conta...
    *
    * https://accounts.google.com/DisplayUnlockCaptcha -> permite login em outros computadores...
    *
    */
//    public static String EMAIL_REMETENTE_GMAIL = "dedoduro.default.user@gmail.com";
//    public static String SENHA_REMETENTE_GMAIL = "d3d0Dur0123";
    public static String EMAIL_REMETENTE_GMAIL = "wfit.default@gmail.com";
    public static String SENHA_REMETENTE_GMAIL = "12wfit@de34";
    
    /**
     * Uteis em threads
     */
    public static int UM_SEGUNDO = 1000;
    public static int UM_MINUTO = 60 * UM_SEGUNDO;
    public static int UMA_HORA = 60 * UM_MINUTO;
    
}
