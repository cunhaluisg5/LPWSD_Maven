/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author luisg
 */
@Entity
@Table(name = "assunto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Assunto.findAll", query = "SELECT a FROM Assunto a")
    , @NamedQuery(name = "Assunto.findById", query = "SELECT a FROM Assunto a WHERE a.id = :id")
    , @NamedQuery(name = "Assunto.findByNome", query = "SELECT a FROM Assunto a WHERE a.nome = :nome")
    , @NamedQuery(name = "Assunto.findByDescricao", query = "SELECT a FROM Assunto a WHERE a.descricao = :descricao")
    , @NamedQuery(name = "Assunto.findByAssunto", query = "SELECT a FROM Assunto a WHERE a.assunto = :assunto")})
public class Assunto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "nome")
    private String nome;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descricao")
    private String descricao;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "assunto")
    private String assunto;

    public Assunto() {
    }

    public Assunto(Integer id) {
        this.id = id;
    }

    public Assunto(Integer id, String nome, String descricao, String assunto) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.assunto = assunto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Assunto)) {
            return false;
        }
        Assunto other = (Assunto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.cesjf.lpwsd.model.Assunto[ id=" + id + " ]";
    }
    
}
