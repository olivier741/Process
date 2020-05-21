/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.dao_repository;

import com.tatsinktechnologic.beans_entity.Alias;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.tatsinktechnologic.beans_entity.Service;
import com.tatsinktechnologic.dao_repository.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author olivier.tatsinkou
 */
public class AliasJpaController implements Serializable {

    public AliasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Alias alias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Service service = alias.getService();
            if (service != null) {
                service = em.getReference(service.getClass(), service.getService_id());
                alias.setService(service);
            }
            em.persist(alias);
            if (service != null) {
                service.getListAlias().add(alias);
                service = em.merge(service);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Alias alias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Alias persistentAlias = em.find(Alias.class, alias.getAlias_id());
            Service serviceOld = persistentAlias.getService();
            Service serviceNew = alias.getService();
            if (serviceNew != null) {
                serviceNew = em.getReference(serviceNew.getClass(), serviceNew.getService_id());
                alias.setService(serviceNew);
            }
            alias = em.merge(alias);
            if (serviceOld != null && !serviceOld.equals(serviceNew)) {
                serviceOld.getListAlias().remove(alias);
                serviceOld = em.merge(serviceOld);
            }
            if (serviceNew != null && !serviceNew.equals(serviceOld)) {
                serviceNew.getListAlias().add(alias);
                serviceNew = em.merge(serviceNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = alias.getAlias_id();
                if (findAlias(id) == null) {
                    throw new NonexistentEntityException("The alias with id " + id + " no longer exists.");
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
            Alias alias;
            try {
                alias = em.getReference(Alias.class, id);
                alias.getAlias_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The alias with id " + id + " no longer exists.", enfe);
            }
            Service service = alias.getService();
            if (service != null) {
                service.getListAlias().remove(alias);
                service = em.merge(service);
            }
            em.remove(alias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Alias> findAliasEntities() {
        return findAliasEntities(true, -1, -1);
    }

    public List<Alias> findAliasEntities(int maxResults, int firstResult) {
        return findAliasEntities(false, maxResults, firstResult);
    }

    private List<Alias> findAliasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Alias.class));
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

    public Alias findAlias(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Alias.class, id);
        } finally {
            em.close();
        }
    }

    public int getAliasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Alias> rt = cq.from(Alias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
