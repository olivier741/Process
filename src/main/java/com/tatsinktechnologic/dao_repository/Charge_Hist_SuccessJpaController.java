/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.dao_repository;

import com.tatsinktechnologic.beans_entity.Charge_Hist_Success;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.tatsinktechnologic.beans_entity.Product;
import com.tatsinktechnologic.dao_repository.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author olivier.tatsinkou
 */
public class Charge_Hist_SuccessJpaController implements Serializable {

    public Charge_Hist_SuccessJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Charge_Hist_Success charge_Hist_Success) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Product product = charge_Hist_Success.getProduct();
            if (product != null) {
                product = em.getReference(product.getClass(), product.getProduct_id());
                charge_Hist_Success.setProduct(product);
            }
            em.persist(charge_Hist_Success);
            if (product != null) {
                product.getListCharge_Hist().add(charge_Hist_Success);
                product = em.merge(product);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Charge_Hist_Success charge_Hist_Success) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Charge_Hist_Success persistentCharge_Hist_Success = em.find(Charge_Hist_Success.class, charge_Hist_Success.getCharge_his_id());
            Product productOld = persistentCharge_Hist_Success.getProduct();
            Product productNew = charge_Hist_Success.getProduct();
            if (productNew != null) {
                productNew = em.getReference(productNew.getClass(), productNew.getProduct_id());
                charge_Hist_Success.setProduct(productNew);
            }
            charge_Hist_Success = em.merge(charge_Hist_Success);
            if (productOld != null && !productOld.equals(productNew)) {
                productOld.getListCharge_Hist().remove(charge_Hist_Success);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld)) {
                productNew.getListCharge_Hist().add(charge_Hist_Success);
                productNew = em.merge(productNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = charge_Hist_Success.getCharge_his_id();
                if (findCharge_Hist_Success(id) == null) {
                    throw new NonexistentEntityException("The charge_Hist_Success with id " + id + " no longer exists.");
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
            Charge_Hist_Success charge_Hist_Success;
            try {
                charge_Hist_Success = em.getReference(Charge_Hist_Success.class, id);
                charge_Hist_Success.getCharge_his_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The charge_Hist_Success with id " + id + " no longer exists.", enfe);
            }
            Product product = charge_Hist_Success.getProduct();
            if (product != null) {
                product.getListCharge_Hist().remove(charge_Hist_Success);
                product = em.merge(product);
            }
            em.remove(charge_Hist_Success);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Charge_Hist_Success> findCharge_Hist_SuccessEntities() {
        return findCharge_Hist_SuccessEntities(true, -1, -1);
    }

    public List<Charge_Hist_Success> findCharge_Hist_SuccessEntities(int maxResults, int firstResult) {
        return findCharge_Hist_SuccessEntities(false, maxResults, firstResult);
    }

    private List<Charge_Hist_Success> findCharge_Hist_SuccessEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Charge_Hist_Success.class));
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

    public Charge_Hist_Success findCharge_Hist_Success(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Charge_Hist_Success.class, id);
        } finally {
            em.close();
        }
    }

    public int getCharge_Hist_SuccessCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Charge_Hist_Success> rt = cq.from(Charge_Hist_Success.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
