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
import java.io.File;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.FlowEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class livroBean extends crudBean<Livro, LivroDAO>{
    //Bean 
    @ManagedProperty(value = "#{arquivoBean}")
    private arquivoBean bean = new arquivoBean();
    
    //DAO
    private LivroDAO livroDAO;
    
    //Itens
    public List<SelectItem> itens;
    
    //Listas
    private List<Assunto> assuntos;
    private List<Editora> editoras;
    private List<Autor> autores;
    
    private UploadedFile file;
    private UploadedFile image;

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
    
    //Grava os dados do livro e do arquivo
    @Override
    public void record(ActionEvent actionEvent) {
        LivroDAO livroDao = new LivroDAO();
        int id = livroDao.persistir(getEntidade()).getId();
        setEntidades(livroDao.buscarTodas());
        addMessage("Salvo(a) com sucesso!", FacesMessage.SEVERITY_INFO);
        bean.setIdBook(id);
        
        if (file != null) {
            bean.setUploadedFile(file);
            bean.upload(id + ".pdf", "files");
        }

        if (image != null) {
            bean.setUploadedFile(image);
            bean.upload(id + ".png", "bookImages");
        }
    }
    
    //Caminho da imagem
    public String dir(String name){
        return "/resources/bookImages/" + name + ".png";
    }
    
    //Atualiza próxima aba
    public String nextTab(FlowEvent event){
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

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public arquivoBean getBean() {
        return bean;
    }

    public void setBean(arquivoBean bean) {
        this.bean = bean;
    }

    public UploadedFile getImage() {
        return image;
    }

    public void setImage(UploadedFile image) {
        this.image = image;
    }
}