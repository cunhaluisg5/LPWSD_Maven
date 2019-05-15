/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.UsuarioDAO;
import br.cesjf.lpwsd.model.Usuario;
import br.cesjf.lpwsd.util.SessionUtil;
import static br.cesjf.lpwsd.util.SessionUtil.getRequest;
import java.io.IOException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author luisg
 */
@ManagedBean
@SessionScoped
public class loginBean {

    private String login;
    private String senha;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void logar(ActionEvent actionEvent) throws IOException {
        try {
            Usuario user = new UsuarioDAO().validar(login, senha);
            HttpServletRequest request = SessionUtil.getRequest();
            request.getSession().setAttribute("usuario", user);
            request.getSession().setAttribute("nome", user.getNome());
            request.getSession().setAttribute("tipo", user.getTipo());
            FacesContext context = FacesContext.getCurrentInstance();
            context.getExternalContext().redirect("index.xhtml");            
        } catch (Exception ex) {
            setLogin("");
            setSenha("");
            status();
        }        
    }

    public void logout() throws IOException {
        getRequest().getSession().invalidate();
        FacesContext.getCurrentInstance().getExternalContext().redirect("login.xhtml");
        setLogin("");
        setSenha("");
    }

    public void status() {
        String cabecalho = "Dados Incorretos!";
        String mensagem = "Por favor, preencha novamente!";
        FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, cabecalho, mensagem);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
    
    public String getTipo(){
        return SessionUtil.getUserTipo();
    }
    
    public String getPerfil(){
        String perfil = SessionUtil.getUserName().toUpperCase();
        return perfil;
    }
}
