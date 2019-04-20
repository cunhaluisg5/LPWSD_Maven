/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.dao;

import br.cesjf.lpwsd.model.Emprestimo;
import br.cesjf.lpwsd.util.PersistenceUtil;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author luisg
 */
public class EmprestimoDAO implements CrudDAO<Emprestimo>{

    @Override
    public Emprestimo buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select a from Emprestimo a where a.id =:id ");
        query.setParameter("id", id);

        List<Emprestimo> emprestimo = query.getResultList();
        if (emprestimo != null && emprestimo.size() > 0) {
            return emprestimo.get(0);
        }
        return null;
    }

    @Override
    public List<Emprestimo> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("from Emprestimo As a");
        return query.getResultList();
    }

    @Override
    public List<Emprestimo> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select distinct a from Emprestimo a group by a.emprestimo");
        return query.getResultList();
    }

    @Override
    public void remover(Emprestimo emprestimo) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(emprestimo)) {
            emprestimo = em.merge(emprestimo);
        }
        em.remove(emprestimo);
        em.getTransaction().commit();
    }

    @Override
    public Emprestimo persistir(Emprestimo emprestimo) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            emprestimo = em.merge(emprestimo);
            em.getTransaction().commit();
            System.out.println("Registro Emprestimo gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emprestimo;
    }

    @Override
    public void removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
       em.getTransaction().begin();
       Query query = em.createQuery(" delete from Emprestimo");
       query.executeUpdate();
       em.getTransaction().commit();
    }
    
}