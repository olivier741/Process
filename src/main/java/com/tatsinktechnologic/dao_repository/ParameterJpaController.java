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
import com.tatsinktechnologic.beans_entity.Parameter;
import com.tatsinktechnologic.dao_repository.exceptions.NonexistentEntityException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author olivier.tatsinkou
 */
public class ParameterJpaController implements Serializable {

    public ParameterJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Parameter parameter) {
        if (parameter.getListCommand() == null) {
            parameter.setListCommand(new HashSet<Command>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Set<Command> attachedListCommand = new HashSet<Command>();
            for (Command listCommandCommandToAttach : parameter.getListCommand()) {
                listCommandCommandToAttach = em.getReference(listCommandCommandToAttach.getClass(), listCommandCommandToAttach.getCommand_id());
                attachedListCommand.add(listCommandCommandToAttach);
            }
            parameter.setListCommand(attachedListCommand);
            em.persist(parameter);
            for (Command listCommandCommand : parameter.getListCommand()) {
                Parameter oldParameterOfListCommandCommand = listCommandCommand.getParameter();
                listCommandCommand.setParameter(parameter);
                listCommandCommand = em.merge(listCommandCommand);
                if (oldParameterOfListCommandCommand != null) {
                    oldParameterOfListCommandCommand.getListCommand().remove(listCommandCommand);
                    oldParameterOfListCommandCommand = em.merge(oldParameterOfListCommandCommand);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Parameter parameter) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Parameter persistentParameter = em.find(Parameter.class, parameter.getParameter_id());
            Set<Command> listCommandOld = persistentParameter.getListCommand();
            Set<Command> listCommandNew = parameter.getListCommand();
            Set<Command> attachedListCommandNew = new HashSet<Command>();
            for (Command listCommandNewCommandToAttach : listCommandNew) {
                listCommandNewCommandToAttach = em.getReference(listCommandNewCommandToAttach.getClass(), listCommandNewCommandToAttach.getCommand_id());
                attachedListCommandNew.add(listCommandNewCommandToAttach);
            }
            listCommandNew = attachedListCommandNew;
            parameter.setListCommand(listCommandNew);
            parameter = em.merge(parameter);
            for (Command listCommandOldCommand : listCommandOld) {
                if (!listCommandNew.contains(listCommandOldCommand)) {
                    listCommandOldCommand.setParameter(null);
                    listCommandOldCommand = em.merge(listCommandOldCommand);
                }
            }
            for (Command listCommandNewCommand : listCommandNew) {
                if (!listCommandOld.contains(listCommandNewCommand)) {
                    Parameter oldParameterOfListCommandNewCommand = listCommandNewCommand.getParameter();
                    listCommandNewCommand.setParameter(parameter);
                    listCommandNewCommand = em.merge(listCommandNewCommand);
                    if (oldParameterOfListCommandNewCommand != null && !oldParameterOfListCommandNewCommand.equals(parameter)) {
                        oldParameterOfListCommandNewCommand.getListCommand().remove(listCommandNewCommand);
                        oldParameterOfListCommandNewCommand = em.merge(oldParameterOfListCommandNewCommand);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                int id = parameter.getParameter_id();
                if (findParameter(id) == null) {
                    throw new NonexistentEntityException("The parameter with id " + id + " no longer exists.");
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
            Parameter parameter;
            try {
                parameter = em.getReference(Parameter.class, id);
                parameter.getParameter_id();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parameter with id " + id + " no longer exists.", enfe);
            }
            Set<Command> listCommand = parameter.getListCommand();
            for (Command listCommandCommand : listCommand) {
                listCommandCommand.setParameter(null);
                listCommandCommand = em.merge(listCommandCommand);
            }
            em.remove(parameter);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Parameter> findParameterEntities() {
        return findParameterEntities(true, -1, -1);
    }

    public List<Parameter> findParameterEntities(int maxResults, int firstResult) {
        return findParameterEntities(false, maxResults, firstResult);
    }

    private List<Parameter> findParameterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Parameter.class));
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

    public Parameter findParameter(int id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Parameter.class, id);
        } finally {
            em.close();
        }
    }

    public int getParameterCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Parameter> rt = cq.from(Parameter.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
