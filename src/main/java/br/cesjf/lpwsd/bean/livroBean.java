/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.AutorDAO;
import br.cesjf.lpwsd.dao.LivroDAO;
import br.cesjf.lpwsd.model.Autor;
import br.cesjf.lpwsd.model.Livro;
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
public class livroBean extends crudBean<Livro, LivroDAO>{
    
    private LivroDAO livroDAO;
    public List<SelectItem> itens;

    public LivroDAO getLivroDAO() {
        return livroDAO;
    }

    public void setLivroDAO(LivroDAO livroDAO) {
        this.livroDAO = livroDAO;
    }

    public List<SelectItem> getItens() {
        return itens;
    }

    public void setItens(List<SelectItem> itens) {
        this.itens = itens;
    }
    
    public Livro buscarId(int id) {
        return new LivroDAO().buscarId(id);
    }

    @Override
    public LivroDAO getDao() {
        if (livroDAO == null) {
            livroDAO = new LivroDAO();
        }
        return livroDAO;
    }

    @Override
    public Livro novo() {
        return new Livro();
    }    
    
    public List<Autor> getAutores(){
        return new AutorDAO().buscarTodas();
    }
}
