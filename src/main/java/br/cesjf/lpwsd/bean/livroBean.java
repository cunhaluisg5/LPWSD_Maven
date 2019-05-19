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
public class livroBean extends crudBean<Livro, LivroDAO>{
    
    private LivroDAO livroDAO;
    public List<SelectItem> itens;
    private List<Assunto> assuntos;
    private List<Editora> editoras;
    private List<Autor> autores;

    public livroBean() {
        assuntos = new AssuntoDAO().buscarTodas();
        editoras = new EditoraDAO().buscarTodas();
        autores = new AutorDAO().buscarTodas();
    }
    
    public List<SelectItem> getItens() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        List<Livro> livros = livroDAO.buscarTodas();

        for (Livro livro : livros) {
            list.add(new SelectItem(livro, livro.getTitulo())); //O que aparece no ComboBox
        }
        return list;
    }

    public LivroDAO getLivroDAO() {
        return livroDAO;
    }

    public void setLivroDAO(LivroDAO livroDAO) {
        this.livroDAO = livroDAO;
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
}
