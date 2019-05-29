/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.dao;

import br.cesjf.lpwsd.model.Exemplar;
import br.cesjf.lpwsd.util.PersistenceUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author luisg
 */
public class ExemplarDAO implements CrudDAO<Exemplar> {

    //DAO
    public static ExemplarDAO exemplarDAO;

    public static ExemplarDAO getInstance() {
        if (exemplarDAO == null)
            exemplarDAO = new ExemplarDAO();
        return exemplarDAO;
    }

    //Busca um exemplar por id
    @Override
    public Exemplar buscarId(int id) { 
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT a FROM Exemplar a WHERE a.id =:id ");
        query.setParameter("id", id);

        List<Exemplar> exemplar = query.getResultList();
        if (exemplar != null && exemplar.size() > 0)
            return exemplar.get(0);
        return null;
    }

    //Busca todos os exemplares
    @Override
    public List<Exemplar> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("FROM Exemplar As a");
        return query.getResultList();
    }

    //Busca a instância de exemplares
    @Override
    public List<Exemplar> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT DISTINCT a FROM Exemplar a GROUP BY a.exemplar");
        return query.getResultList();
    }

    //Remove um exemplar
    @Override
    public void remover(Exemplar exemplar) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(exemplar))
            exemplar = em.merge(exemplar);
        em.remove(exemplar);
        em.getTransaction().commit();
    }

    //Persiste os dados do exemplar
    @Override
    public Exemplar persistir(Exemplar exemplar) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            exemplar = em.merge(exemplar);
            em.getTransaction().commit();
            System.out.println("Registro Exemplar gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exemplar;
    }

    //Remove todos os exemplares
    @Override
    public void removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery("DELETE FROM Exemplar");
        query.executeUpdate();
        em.getTransaction().commit();
    }
}