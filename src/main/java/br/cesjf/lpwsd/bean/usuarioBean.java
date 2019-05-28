/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.UsuarioDAO;
import br.cesjf.lpwsd.model.Usuario;
import br.cesjf.lpwsd.report.Relatorio;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    private UsuarioDAO usuarioDAO;
    public List<SelectItem> itens;

    public List<SelectItem> getItens() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        List<Usuario> editoras = usuarioDAO.buscarTodas();

        for (Usuario usuario : editoras) {
            list.add(new SelectItem(usuario, usuario.getNome())); //nome Usuario ira aparecer no combo
        }
        return list;
    }
    
    public List<SelectItem> tiposUsuarios() {
        List<SelectItem> list = new ArrayList<SelectItem>();

        list.add(new SelectItem("Aluno"));
        list.add(new SelectItem("Professor"));
        list.add(new SelectItem("Funcionário"));
        list.add(new SelectItem("Bibliotecário"));
        list.add(new SelectItem("Administrador"));
        return list;
    }

    public Usuario buscarId(int id) {
        return new UsuarioDAO().buscarId(id);
    }
    
    @Override
    public UsuarioDAO getDao() {
        if (usuarioDAO == null) {
            usuarioDAO = new UsuarioDAO();
        }
        return usuarioDAO;
    }

    @Override
    public Usuario novo() {
        return new Usuario();
    }
    
    public void gerarRelatorioAction() {
        Relatorio relatorio = new Relatorio();
        relatorio.getRelatorio();
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
}
