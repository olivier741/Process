/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tatsinktechnologic.dao_commun_repository;

import com.tatsinktechnologic.beans_entity.Alias;
import com.tatsinktechnologic.beans_entity.Product;
import com.tatsinktechnologic.beans_entity.Product_;
import com.tatsinktechnologic.beans_entity.Promo_Filter;
import com.tatsinktechnologic.beans_entity.Promotion;
import com.tatsinktechnologic.beans_entity.Promotion_Table;
import com.tatsinktechnologic.beans_entity.Register;
import com.tatsinktechnologic.beans_entity.Register_;
import com.tatsinktechnologic.beans_entity.Service;
import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Pattern;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.log4j.Logger;

/**
 *
 * @author olivier
 */
public class CommunRepository {
    
    private EntityManagerFactory emf;
    
    private static Logger logger = Logger.getLogger(CommunRepository.class);

    public CommunRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public List<Register> getExtendRegister(int maxrow,int index_id,Timestamp receive_time){
        EntityManager em = emf.createEntityManager();
        List<Register> result = null;
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();

            CriteriaQuery<Register> criteria = builder.createQuery(Register.class);
            Root<Register> regRoot = criteria.from(Register.class );
            criteria.select(regRoot);

            
            Expression<Integer> regExpression_id = regRoot.get(Register_.register_id);
            Expression<Integer> regExpression_mod = builder.mod(regExpression_id, index_id);
            Expression<Timestamp> regExpression_expiretime = regRoot.get(Register_.expire_time);

            criteria.where( builder.and(builder.equal( regRoot.get("autoextend"),true),
                                        builder.equal( regRoot.get("status"),1),
                                        builder.equal( regExpression_mod,0),
                                        builder.lessThanOrEqualTo(regExpression_expiretime,receive_time)
                                      )
                          );
            Query query = em.createQuery( criteria );
            query.setMaxResults(maxrow);
            
            logger.info("SQL query : "+ query.unwrap(org.hibernate.Query.class).getQueryString());
            logger.info("SQL param autoextend = "+true);
            logger.info("SQL param status = "+1);
            logger.info("SQL param id = "+index_id);
            logger.info("SQL param expire_time = "+receive_time);
            
            result = query.getResultList();
         } finally {
            em.close();
        }
        
        return result;
    }
       
       
    public Register getRegister(String msisdn, Product prod){
        EntityManager em = emf.createEntityManager();
        Register result = null;
         try {
            CriteriaBuilder builder = em.getCriteriaBuilder();

            CriteriaQuery<Register> criteria = builder.createQuery(Register.class);
            Root<Register> regRoot = criteria.from(Register.class );
            criteria.select(regRoot);
            criteria.where( builder.and(builder.equal( regRoot.get("msisdn"),msisdn),
                                        builder.equal( regRoot.get("product"),prod)
                                      )
                          );
            Query query = em.createQuery( criteria );
            logger.info("SQL query : "+ query.unwrap(org.hibernate.Query.class).getQueryString());
            logger.info("SQL param msisdn = "+msisdn);
            logger.info("SQL param product = "+prod);
            List<Register> listRegister = query.getResultList();
            if (listRegister!=null && listRegister.size() != 0) result = listRegister.get(0);
         } finally {
            em.close();
        }

        return result;
    }
    
     //  authorisation to get promotion 
    public boolean authorizationPromo(Promotion promotion,String msisdn){
        EntityManager em = emf.createEntityManager();
        boolean result = false;
        
        if (promotion.getPromotion_filter()!=null){
            if (promotion.getPromotion_filter()==Promo_Filter.REGEX){
                Pattern pattern_promo = Pattern.compile(promotion.getMsisdn_regex());
                result = pattern_promo.matcher(msisdn).find();   
            }else if (promotion.getPromotion_filter()==Promo_Filter.TABLE){
        
                try {
                    CriteriaBuilder builder = em.getCriteriaBuilder();

                    CriteriaQuery<Promotion_Table> criteria = builder.createQuery(Promotion_Table.class);
                    Root<Promotion_Table> promotionRoot = criteria.from(Promotion_Table.class );
                    criteria.select(promotionRoot);
                    criteria.where( builder.equal( promotionRoot.get("msisdn"),msisdn));
                    Query query = em.createQuery( criteria );
                    
                    logger.info("SQL query : "+ query.unwrap(org.hibernate.Query.class).getQueryString());
                    logger.info("SQL param msisdn = "+msisdn);

                    List<Promotion_Table> listPromtable = query.getResultList();
                    if (listPromtable!=null && listPromtable.size() != 0) result = true;
                 } finally {
                    em.close();
                }
          }
        }else{
            result = true;
        }

       return result;
    }
    
    
    // get list of product base on its name
    public List<Product> getListProduct(List<String> listProduct_code){
        EntityManager em = emf.createEntityManager();
        List<Product> result = null;

        try {
            
            CriteriaBuilder builder = em.getCriteriaBuilder();

            CriteriaQuery<Product> criteria = builder.createQuery(Product.class);
            Root<Product> productRoot = criteria.from(Product.class );
            criteria.select(productRoot);
            
            Expression<String> prodExpression = productRoot.get(Product_.product_code);
            Predicate prodPredicate = prodExpression.in(listProduct_code);
            
            criteria.where( prodPredicate);
            Query query = em.createQuery( criteria );

            logger.info("SQL query : "+ query.unwrap(org.hibernate.Query.class).getQueryString());
            logger.info("SQL param list Product = "+listProduct_code);
            result= query.getResultList();

         } finally {
            em.close();
        }
        return result;
    }
    
    
    public boolean checkRestrictProduct(String msisdn,List<String> listProduct_code){
        EntityManager em = emf.createEntityManager();
        boolean result = false;
        
        List<Product> listProduct = getListProduct(listProduct_code);
        
        if (listProduct!=null && listProduct.size() != 0){
              try {

                CriteriaBuilder builder = em.getCriteriaBuilder();

                CriteriaQuery<Register> criteria = builder.createQuery(Register.class);
                Root<Register> registerRoot = criteria.from(Register.class );
                criteria.select(registerRoot);

                Expression<Product> regExpression = registerRoot.get(Register_.product);
                Predicate regPredicate = regExpression.in(listProduct);

                criteria.where( builder.and(regPredicate, 
                                            builder.equal( registerRoot.get("status"),1),
                                            builder.equal( registerRoot.get("msisdn"),msisdn)
                                            )
                              );
                Query query = em.createQuery( criteria );

                logger.info("SQL query : "+ query.unwrap(org.hibernate.Query.class).getQueryString());
                logger.info("SQL param list Product = "+listProduct_code);
                logger.info("SQL param staus = "+1);
                logger.info("SQL param msisdn = "+msisdn);
                
                List<Register> listRegister = query.getResultList();
                if (listRegister!=null && listRegister.size() != 0) result = true;

             } finally {
                em.close();
            }
        }

        return result;
    }
    
    
     /**
     * select * from Alias where msisdn = ? and Alias.service = ?
     */
    public Alias getAlias(String msisdn) {
        EntityManager em = emf.createEntityManager();
        Alias result = null;
        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();

            CriteriaQuery<Alias> criteria = builder.createQuery(Alias.class);
            Root<Alias> aliasRoot = criteria.from(Alias.class);
            criteria.select(aliasRoot);
            criteria.where(builder.equal(aliasRoot.get("msisdn"), msisdn));

            Query query = em.createQuery(criteria);
            logger.info("SQL query : " + query.unwrap(org.hibernate.Query.class).getQueryString());
            logger.info("SQL param msisdn = " + msisdn);

            List<Alias> listEntity = query.getResultList();
            if (listEntity != null && !listEntity.isEmpty()) {
                result = listEntity.get(0);
            }
        } finally {
            em.close();
        }

        return result;
    }
    
    
     /**
     *  update Alias set alias = ? where msisdn = ? 
     */
    public boolean updateAlias(String msisdn, String alias) {
        boolean result = false;

        EntityManager em = emf.createEntityManager();

        try {
            CriteriaBuilder builder = em.getCriteriaBuilder();

            // create delete
            CriteriaUpdate<Alias> updateCriteria = builder.createCriteriaUpdate(Alias.class);
           
            // set the root class
            Root entityRoot = updateCriteria.from(Alias.class);
            
            // set update and where clause

            updateCriteria.set("alias", alias);
            // set where clause

            updateCriteria.where(builder.equal(entityRoot.get("msisdn"), msisdn));
            // perform update
            em.createQuery(updateCriteria).executeUpdate();
            result = true;
        } finally {
            em.close();
        }

        return result;
    }
}
