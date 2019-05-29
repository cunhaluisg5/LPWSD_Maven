/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.ExemplarDAO;
import br.cesjf.lpwsd.dao.LivroDAO;
import br.cesjf.lpwsd.model.Exemplar;
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
public class exemplarBean extends crudBean<Exemplar, ExemplarDAO> {

    //DAO
    private ExemplarDAO exemplarDAO;
    
    //Itens
    public List<SelectItem> itens;
    
    //Lista de livros
    private List<Livro> livros;

    //Construtor
    public exemplarBean() {
        livros = new LivroDAO().buscarTodas();
    }
    
    //Busca exemplar por id
     public Exemplar buscarId(int id) {
        return new ExemplarDAO().buscarId(id);
    }
    
     //Retorna o DAO
    @Override
    public ExemplarDAO getDao() {
        if (exemplarDAO == null)
            exemplarDAO = new ExemplarDAO();
        return exemplarDAO;
    }

    //Instancia um exemplar
    @Override
    public Exemplar novo() {
        return new Exemplar();
    }

    //GETTERs and SETTERs
    
    public ExemplarDAO getExemplarDAO() {
        return exemplarDAO;
    }

    public void setExemplarDAO(ExemplarDAO exemplarDAO) {
        this.exemplarDAO = exemplarDAO;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }
}