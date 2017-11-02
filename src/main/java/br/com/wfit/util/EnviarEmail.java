/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.wfit.util;

import java.util.ArrayList;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *
 * @author Jose Alves
 */
public class EnviarEmail {
    
    /**
     * Enviar o email para a lista de usarios especificados
     * @param emails
     * @param assunto
     * @param conteudo 
     */
    public static void tratarEnvio(ArrayList<String> emails, String assunto, String conteudo, String... dadosRemetente) {
        HtmlEmail email = new HtmlEmail();
        
        try {
            email.setHostName(Constantes.HOST_NAME_GMAIL);
            email.addTo(Constantes.ADMINISTRADOR_1);
            email.setFrom(Constantes.EMAIL_REMETENTE_GMAIL, "WFitness - Administrador");
            
            for (String tmp : emails) {
                email.addBcc(tmp);
            }
            
            String tmp = "<br /><br /><b>Nome: "+dadosRemetente[0]+"<br />Email: "+dadosRemetente[1]+"</b><br /><br />";
            tmp += "<b>Conteúdo digitado pelo usuário: </b><br /><br />";
            email.setSubject(assunto);
            
            // Trabalhando com imagem...
//            URL url = new URL ("http://<ENDERECO DA IMAGEM AQUI...>");
//            String idImg = email.embed(url, "logo");
            
            email.setHtmlMsg(tmp+conteudo);
            
            // Tratando mensagem alternativa
            email.setTextMsg("Seu servidor de e-mail não suporta mensagem HTML... :-(");

            email.setSmtpPort(Constantes.PORTA_SMTP_GMAIL);
            email.setAuthenticator(new DefaultAuthenticator(Constantes.EMAIL_REMETENTE_GMAIL, Constantes.SENHA_REMETENTE_GMAIL));
            email.setSSLOnConnect(true);
            
            // Enviando email
            email.send();
            
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Tratando a situacao de enviar o email
     * para recuperacao de senha perdida.
     */
    public static void recuperarSenha(ArrayList<String> emails, String adicionalConteudo) {
        
        String assunto = "[wfit] - Recuperação de Senha.";
        String conteudo = "<html><head><title>Recuperação de senha - WFIT.</title></head>"
                + "<body><br /><br />Olá! Recebemos uma solicitação de alteração de senha.<br /><br />"
                + "Assim acreditamos que sendo uma petição realizada por você, geramos uma nova senha! <br />"
                + "No entanto, caso essa solicitação não tenha sido gerada por favor,<br />" 
                + "solicitamos o quanto antes que altere-a, prezando pela segurança dos seus dados. <br /><br /><br />"
                + "Tome nota da sua nova senha: <h1>"
                + "<strong><span style='background-color: #F0FFF0'>" +adicionalConteudo+ "</span></strongs></h1><br />"
                + "<b>[ - POR FAVOR, NÃO RESPONDA ESSE E-MAIL. - ]</b><br />"
                + "</body></html>";
        
        tratarEnvio(emails, assunto, conteudo);
    }
}

    