/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.cesjf.lpwsd.dao;

import br.cesjf.lpwsd.model.Reserva;
import br.cesjf.lpwsd.util.PersistenceUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 *
 * @author luisg
 */
public class ReservaDAO implements Serializable, CrudDAO<Reserva>{
    
    public static ReservaDAO reservaDAO;
    
    public static ReservaDAO getInstance(){
        if(reservaDAO == null){
            reservaDAO = new ReservaDAO();
        }
        return reservaDAO;
    }

    @Override
    public Reserva buscarId(int id) {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select a from Reserva a where a.id =:id");
        query.setParameter("id", id);
        
        List<Reserva> reserva = query.getResultList();
        if(reserva != null && reserva.size() > 0){
            return reserva.get(0);
        }
        return null;
    }

    @Override
    public List<Reserva> buscarTodas() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("from Reserva As a");
        return query.getResultList();
    }

    @Override
    public List<Reserva> buscarInstancia() {
        EntityManager em = PersistenceUtil.getEntityManager();
        Query query = em.createQuery("select distinct a from Reserva a group by a.reserva");
        return query.getResultList();
    }

    @Override
    public void remover(Reserva reserva) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        if (!em.contains(reserva)) {
            reserva = em.merge(reserva);
        }
        em.remove(reserva);
        em.getTransaction().commit();
    }

    @Override
    public Reserva persistir(Reserva reserva) {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        try {
            reserva = em.merge(reserva);
            em.getTransaction().commit();
            System.out.println("Registro Reserva gravado com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return reserva;
    }

    @Override
    public void removeAll() {
        EntityManager em = PersistenceUtil.getEntityManager();
        em.getTransaction().begin();
        Query query = em.createQuery(" delete from Reserva ");
        query.executeUpdate();
        em.getTransaction().commit();
    }
    
}
