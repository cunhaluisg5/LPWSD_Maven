/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.AssuntoDAO;
import br.cesjf.lpwsd.dao.AutorDAO;
import br.cesjf.lpwsd.dao.EditoraDAO;
import br.cesjf.lpwsd.dao.LivroDAO;
import br.cesjf.lpwsd.model.Assunto;
import br.cesjf.lpwsd.model.Autor;
import br.cesjf.lpwsd.model.Editora;
import br.cesjf.lpwsd.model.Livro;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.event.FlowEvent;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class livroBean extends crudBean<Livro, LivroDAO>{
    
    //DAO
    private LivroDAO livroDAO;
    
    //Itens
    public List<SelectItem> itens;
    
    //Listas
    private List<Assunto> assuntos;
    private List<Editora> editoras;
    private List<Autor> autores;
    
    private int index;

    //Construtor
    public livroBean() {
        assuntos = new AssuntoDAO().buscarTodas();
        editoras = new EditoraDAO().buscarTodas();
        autores = new AutorDAO().buscarTodas();
    }
    
    //Busca um livro por id
    public Livro buscarId(int id) {
        return new LivroDAO().buscarId(id);
    } 
    
    public String nextTab(FlowEvent event){
        arquivoBean.livro = getEntidade();
        return event.getNewStep();
    }
    
    //Retorna o DAO
    @Override
    public LivroDAO getDao() {
        if (livroDAO == null)
            livroDAO = new LivroDAO();
        return livroDAO;
    }

    //Instancia um livro
    @Override
    public Livro novo() {
        return new Livro();
    }  

    //Getters and Setters
    public LivroDAO getLivroDAO() {
        return livroDAO;
    }

    public void setLivroDAO(LivroDAO livroDAO) {
        this.livroDAO = livroDAO;
    }

    public void setItens(List<SelectItem> itens) {
        this.itens = itens;
    }

    public List<Assunto> getAssuntos() {
        return assuntos;
    }

    public void setAssuntos(List<Assunto> assuntos) {
        this.assuntos = assuntos;
    }

    public List<Editora> getEditoras() {
        return editoras;
    }

    public void setEditoras(List<Editora> editoras) {
        this.editoras = editoras;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}