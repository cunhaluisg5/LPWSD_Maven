/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.dao;

import br.cesjf.lpwsd.model.Livro;
import br.cesjf.lpwsd.util.PersistenceUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author luisg
 */
public class LivroDAO implements Serializable, CrudDAO<Livro> {

    //DAO
    public static LivroDAO livroDAO;

    //Retorna o DAO
    public static LivroDAO getInstance() {
        if (livroDAO == null)
            livroDAO = new LivroDAO();
        return livroDAO;
    }
    
    //Busca um livro por título
    public Livro buscar(String titulo) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT a FROM Livro a WHERE a.titulo =:titulo ");
        query.setParameter("titulo", titulo);

        List<Livro> livro = query.getResultList();
        if (livro != null && livro.size() > 0)
            return livro.get(0);
        return null;
    }
    
    //Busca um livro por id
    @Override
    public Livro buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT a FROM Livro a WHERE a.id =:id ");
        query.setParameter("id", id);

        List<Livro> livro = query.getResultList();
        if (livro != null && livro.size() > 0)
            return livro.get(0);
        return null;
    }

    //Busca todos os livros
    @Override
    public List<Livro> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("FROM Livro As a");
        return query.getResultList();
    }

    //Busca a instância de livros
    @Override
    public List<Livro> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT DISTINCT a FROM Livro a GROUP BY a.livro");
        return query.getResultList();
    }
    
    //Remove um livro
    @Override
    public void remover(Livro livro) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(livro))
            livro = em.merge(livro);
        em.remove(livro);
        em.getTransaction().commit();
    }

    //Persiste os dados do livro
    @Override
    public Livro persistir(Livro livro) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            livro = em.merge(livro);
            em.getTransaction().commit();
            System.out.println("Registro Livro gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return livro;
    }

    //Remove todos os livros
    @Override
    public void removeAll() {
       EntityManager em = PersistenceUtil.getEntityManager();
       em.getTransaction().begin();
       Query query = em.createQuery("DELETE FROM Livro");
       query.executeUpdate();
       em.getTransaction().commit();
    }   
}
