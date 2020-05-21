/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.dao_repository;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.tatsinktechnologic.beans_entity.Command;
import com.tatsinktechnologic.beans_entity.Notification_Conf;
import com.tatsinktechnologic.dao_repository.exceptions.NonexistentEntityException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author olivier.tatsinkou
 */
public class Notification_ConfJpaController implements Serializable {

    public Notification_ConfJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notification_Conf notification_Conf) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Command command = notification_Conf.getCommand();
            if (command != null) {
                command = em.getReference(command.getClass(), command.getCommand_id());
                notification_Conf.setCommand(command);
            }
            em.persist(notification_Conf);
            if (command != null) {
                command.getListConfig().add(notification_Conf);
                command = em.merge(command);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notification_Conf notification_Conf) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notification_Conf persistentNotification_Conf = em.find(Notification_Conf.class, notification_Conf.getNotification_id());
            Command commandOld = persistentNotification_Conf.getCommand();
            Command commandNew = notification_Conf.getCommand();
            if (commandNew != null) {
                commandNew = em.getReference(commandNew.getClass(), commandNew.getCommand_id());
                notification_Conf.setCommand(commandNew);
            }
            notification_Conf = em.merge(notification_Conf);
            if (commandOld != null && !commandOld.equals(commandNew)) {
                commandOld.getListConfig().remove(notification_Conf);
                commandOld = em.merge(commandOld);
            }
            if (commandNew != null && !commandNew.equals(commandOld)) {
                commandNew.getListConfig().add(notification_Conf);
                commandNew = em.merge(commandNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = notification_Conf.getNotification_id();
                if (findNotification_Conf(id) == null) {
                    throw new NonexistentEntityException("The notification_Conf with id " + id + " no longer exists.");
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
            Notification_Conf notification_Conf;
            try {
                notification_Conf = em.getReference(Notification_Conf.class, id);
                notification_Conf.getNotification_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notification_Conf with id " + id + " no longer exists.", enfe);
            }
            Command command = notification_Conf.getCommand();
            if (command != null) {
                command.getListConfig().remove(notification_Conf);
                command = em.merge(command);
            }
            em.remove(notification_Conf);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Notification_Conf> findNotification_ConfEntities() {
        return findNotification_ConfEntities(true, -1, -1);
    }

    public List<Notification_Conf> findNotification_ConfEntities(int maxResults, int firstResult) {
        return findNotification_ConfEntities(false, maxResults, firstResult);
    }

    private List<Notification_Conf> findNotification_ConfEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notification_Conf.class));
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

    public Notification_Conf findNotification_Conf(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notification_Conf.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotification_ConfCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notification_Conf> rt = cq.from(Notification_Conf.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
