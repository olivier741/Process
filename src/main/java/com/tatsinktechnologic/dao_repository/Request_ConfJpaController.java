/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.dao_repository;

import com.tatsinktechnologic.beans_entity.Request_Conf;
import com.tatsinktechnologic.dao_repository.exceptions.NonexistentEntityException;
import com.tatsinktechnologic.dao_repository.exceptions.PreexistingEntityException;
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
public class Request_ConfJpaController implements Serializable {

    public Request_ConfJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Request_Conf request_Conf) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(request_Conf);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRequest_Conf(request_Conf.getCommand_id()) != null) {
                throw new PreexistingEntityException("Request_Conf " + request_Conf + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Request_Conf request_Conf) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            request_Conf = em.merge(request_Conf);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = request_Conf.getCommand_id();
                if (findRequest_Conf(id) == null) {
                    throw new NonexistentEntityException("The request_Conf with id " + id + " no longer exists.");
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
            Request_Conf request_Conf;
            try {
                request_Conf = em.getReference(Request_Conf.class, id);
                request_Conf.getCommand_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The request_Conf with id " + id + " no longer exists.", enfe);
            }
            em.remove(request_Conf);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Request_Conf> findRequest_ConfEntities() {
        return findRequest_ConfEntities(true, -1, -1);
    }

    public List<Request_Conf> findRequest_ConfEntities(int maxResults, int firstResult) {
        return findRequest_ConfEntities(false, maxResults, firstResult);
    }

    private List<Request_Conf> findRequest_ConfEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Request_Conf.class));
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

    public Request_Conf findRequest_Conf(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Request_Conf.class, id);
        } finally {
            em.close();
        }
    }

    public int getRequest_ConfCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Request_Conf> rt = cq.from(Request_Conf.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
