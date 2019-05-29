/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.dao;

import br.cesjf.lpwsd.model.Emprestimo;
import br.cesjf.lpwsd.util.PersistenceUtil;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author luisg
 */
public class EmprestimoDAO implements CrudDAO<Emprestimo>{

    //Busca um empréstimo por id
    @Override
    public Emprestimo buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Emprestimo> query = em.createQuery("SELECT e FROM Emprestimo e WHERE e.id =:id ", Emprestimo.class);
        query.setParameter("id", id);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    //Busca todos os empréstimos
    @Override
    public List<Emprestimo> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("FROM Emprestimo As a");
        return query.getResultList();
    }

    //Busca a instância de empréstimos
    @Override
    public List<Emprestimo> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("SELECT DISTINCT a FROM Emprestimo a GROUP BY a.emprestimo");
        return query.getResultList();
    }

    //Remove um empréstimo
    @Override
    public void remover(Emprestimo emprestimo) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(emprestimo))
            emprestimo = em.merge(emprestimo);
        em.remove(emprestimo);
        em.getTransaction().commit();
    }

    //Persiste os dados do empréstimo
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

    //Remove todos os empréstimos
    @Override
    public void removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
       em.getTransaction().begin();
       Query query = em.createQuery("DELETE FROM Emprestimo");
       query.executeUpdate();
       em.getTransaction().commit();
    }
    
    //Verifica se existe débito
    public boolean checkDebit(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(e) FROM Emprestimo e WHERE e.idUsuario.id =:id AND e.dataDevolucao IS NULL AND e.dataPrevista <= :dataAtual", Long.class);
        query.setParameter("id", id);
        query.setParameter("dataAtual", new Date());

        return query.getSingleResult() > 0;
    }
    
    //Verifica se existe empréstimo em aberto
    public Long checkOpened(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(e) FROM Emprestimo e WHERE e.idUsuario.id =:id AND e.dataPrevista >= :dataAtual ", Long.class);
        query.setParameter("id", id);
        query.setParameter("dataAtual", new Date());

        return query.getSingleResult();
    }
    
    //Verifica se existe exemplar disponível
    public boolean available(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        TypedQuery<Long> query = em.createQuery("SELECT COUNT(e) FROM Emprestimo e WHERE e.dataDevolucao IS NULL AND e.idExemplar.id =:id ", Long.class);
        query.setParameter("id", id);

        return query.getSingleResult() == 0;
    }   
}
