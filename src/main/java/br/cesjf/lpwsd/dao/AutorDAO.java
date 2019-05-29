/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.dao;

import br.cesjf.lpwsd.model.Autor;
import br.cesjf.lpwsd.util.PersistenceUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author luisg
 */
public class AutorDAO implements Serializable, CrudDAO<Autor>{
    
    //DAO
    public static AutorDAO autorDAO;

    //Retorna o DAO
    public static AutorDAO getInstance() {
        if (autorDAO == null)
            autorDAO = new AutorDAO();
        return autorDAO;
    }
    
    //Busca um autor por nome
    public Autor buscar(String nome) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT a FROM Autor a WHERE a.nome =:nome ");
        query.setParameter("nome", nome);

        List<Autor> autores = query.getResultList();
        if (autores != null && autores.size() > 0)
            return autores.get(0);
        return null;
    }
    
    //Busca um autor por id
    @Override
    public Autor buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT a FROM Autor a WHERE a.id =:id ");
        query.setParameter("id", id);

        List<Autor> autores = query.getResultList();
        if (autores != null && autores.size() > 0)
            return autores.get(0);
        return null;
    }

    //Busca todos os autores
    @Override
    public List<Autor> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("FROM Autor As a");
        return query.getResultList();
    }

    //Busca a instância de autores
    @Override
    public List<Autor> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT DISTINCT a FROM Autor a GROUP BY a.autores");
        return query.getResultList();
    }
    
    //Remove um autor
    @Override
    public void remover(Autor autores) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(autores))
            autores = em.merge(autores);
        em.remove(autores);
        em.getTransaction().commit();
    }

    //Persiste os dados do autor
    @Override
    public Autor persistir(Autor autores) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            autores = em.merge(autores);
            em.getTransaction().commit();
            System.out.println("Registro Autor gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return autores;
    }

    //Remove todos os autores
    @Override
    public void removeAll() {
       EntityManager em = PersistenceUtil.getEntityManager();
       em.getTransaction().begin();
       Query query = em.createQuery("DELETE FROM Autor ");
       query.executeUpdate();
       em.getTransaction().commit();
    }
}