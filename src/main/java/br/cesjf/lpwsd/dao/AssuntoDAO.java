/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.dao;

import br.cesjf.lpwsd.model.Assunto;
import br.cesjf.lpwsd.util.PersistenceUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author luisg
 */
public class AssuntoDAO implements Serializable, CrudDAO<Assunto>{
    
    //DAO
    public static AssuntoDAO assuntoDAO;
    
    //Retorna o DAO
    public static AssuntoDAO getInstance(){
        if(assuntoDAO == null)
            assuntoDAO = new AssuntoDAO();
        return assuntoDAO;
    }
    
    //Busca um assunto por nome
    public Assunto buscar(String nome){
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT a FROM Assunto a WHERE a.nome =:nome");
        query.setParameter("nome", nome);
        
        List<Assunto> assunto = query.getResultList();
        if(assunto != null && assunto.size() > 0)
            return assunto.get(0);
        return null;
    }

    //Busca um assunto por id
    @Override
    public Assunto buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT a FROM Assunto a WHERE a.id =:id");
        query.setParameter("id", id);
        
        List<Assunto> assunto = query.getResultList();
        if(assunto != null && assunto.size() > 0)
            return assunto.get(0);
        return null;
    }

    //Busca todos os assuntos
    @Override
    public List<Assunto> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("FROM Assunto As a");
        return query.getResultList();
    }

    //Busca a instância de assunto
    @Override
    public List<Assunto> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT DISTINCT a FROM Assunto a GROUP BY a.assunto");
        return query.getResultList();
    }

    //Remove um assunto
    @Override
    public void remover(Assunto assunto) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(assunto)) 
            assunto = em.merge(assunto);
        em.remove(assunto);
        em.getTransaction().commit();
    }

    //Persiste os dados do assunto
    @Override
    public Assunto persistir(Assunto assunto) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            assunto = em.merge(assunto);
            em.getTransaction().commit();
            System.out.println("Registro Assunto gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return assunto;
    }

    //Remove todos os assuntos
    @Override
    public void removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM Assunto ");
        query.executeUpdate();
        em.getTransaction().commit();
    }
}