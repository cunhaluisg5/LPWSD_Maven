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
public class exemplarBean extends crudBean<Exemplar, ExemplarDAO> {

    private ExemplarDAO exemplarDAO;
    
    public List<SelectItem> itens;
    private List<Livro> livros;

    public exemplarBean() {
        livros = new LivroDAO().buscarTodas();
    }

    public List<SelectItem> getItens() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        List<Exemplar> exemplares = exemplarDAO.buscarTodas();

        for (Exemplar exemplar : exemplares) {
            list.add(new SelectItem(exemplar, exemplar.getIdLivro().getTitulo())); //O que aparece no ComboBox
        }
        return list;
    }
    
     public Exemplar buscarId(int id) {
        return new ExemplarDAO().buscarId(id);
    }
    
    @Override
    public ExemplarDAO getDao() {
        if (exemplarDAO == null) {
            exemplarDAO = new ExemplarDAO();
        }
        return exemplarDAO;
    }

    @Override
    public Exemplar novo() {
        return new Exemplar();
    }

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
