/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.bean;

import br.cesjf.lpwsd.dao.LivroDAO;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author luisg
 */
@ManagedBean
@ViewScoped
public class acervoBean {

    //Variáveis de controle
    private String option = "Título";
    private String text;

    //Retorna o livro de acordo com o filtro
    public List<Object[]> getExemplares() {
        LivroDAO livroDAO = new LivroDAO();
        switch (option) {
            case "Título":
                return livroDAO.livrosPorTitulo(text);
            case "Autor":
                return livroDAO.livrosPorAutor(text);
            case "Assunto":
                return livroDAO.livrosPorAssunto(text);
            default:
                break;
        }
        return null;
    }
    
    //Getters and Setters

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
