/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.UsuarioDAO;
import br.cesjf.lpwsd.model.Usuario;
import br.cesjf.lpwsd.report.Relatorio;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class usuarioBean extends crudBean<Usuario, UsuarioDAO> {

    //DAO
    private UsuarioDAO usuarioDAO;
    
    //Itens
    public List<SelectItem> itens;
    
    //Retorna os tipos de usuários
    public List<SelectItem> tiposUsuarios() {
        List<SelectItem> list = new ArrayList<SelectItem>();

        list.add(new SelectItem("Aluno"));
        list.add(new SelectItem("Professor"));
        list.add(new SelectItem("Funcionário"));
        list.add(new SelectItem("Bibliotecário"));
        list.add(new SelectItem("Administrador"));
        return list;
    }

    //Busca um usuário por id
    public Usuario buscarId(int id) {
        return new UsuarioDAO().buscarId(id);
    }
    
    //Retorna o DAO
    @Override
    public UsuarioDAO getDao() {
        if (usuarioDAO == null)
            usuarioDAO = new UsuarioDAO();
        return usuarioDAO;
    }

    //Instancia um usuário
    @Override
    public Usuario novo() {
        return new Usuario();
    }
    
    //Gera um relatório
    public void gerarRelatorioAction() {
        Relatorio relatorio = new Relatorio();
        relatorio.setReport("usuarios");
        relatorio.getRelatorio();
    }

    //Getters and Setters
    
    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
}
