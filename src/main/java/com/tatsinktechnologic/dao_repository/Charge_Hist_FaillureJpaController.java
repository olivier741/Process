/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.dao_repository;

import com.tatsinktechnologic.beans_entity.Charge_Hist_Faillure;
import com.tatsinktechnologic.dao_repository.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author olivier.tatsinkou
 */
public class Charge_Hist_FaillureJpaController implements Serializable {

    public Charge_Hist_FaillureJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Charge_Hist_Faillure charge_Hist_Faillure) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(charge_Hist_Faillure);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Charge_Hist_Faillure charge_Hist_Faillure) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            charge_Hist_Faillure = em.merge(charge_Hist_Faillure);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = charge_Hist_Faillure.getCharge_his_id();
                if (findCharge_Hist_Faillure(id) == null) {
                    throw new NonexistentEntityException("The charge_Hist_Faillure with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(int id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Charge_Hist_Faillure charge_Hist_Faillure;
            try {
                charge_Hist_Faillure = em.getReference(Charge_Hist_Faillure.class, id);
                charge_Hist_Faillure.getCharge_his_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The charge_Hist_Faillure with id " + id + " no longer exists.", enfe);
            }
            em.remove(charge_Hist_Faillure);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Charge_Hist_Faillure> findCharge_Hist_FaillureEntities() {
        return findCharge_Hist_FaillureEntities(true, -1, -1);
    }

    public List<Charge_Hist_Faillure> findCharge_Hist_FaillureEntities(int maxResults, int firstResult) {
        return findCharge_Hist_FaillureEntities(false, maxResults, firstResult);
    }

    private List<Charge_Hist_Faillure> findCharge_Hist_FaillureEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Charge_Hist_Faillure.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Charge_Hist_Faillure findCharge_Hist_Faillure(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Charge_Hist_Faillure.class, id);
        } finally {
            em.close();
        }
    }

    public int getCharge_Hist_FaillureCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Charge_Hist_Faillure> rt = cq.from(Charge_Hist_Faillure.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
