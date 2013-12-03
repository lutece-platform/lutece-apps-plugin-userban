/*
 * Copyright (c) 2002-2013, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.userban.dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;

import fr.paris.lutece.plugins.userban.bean.AbstractFilter;
import fr.paris.lutece.plugins.userban.dao.commons.PagedQuery;
import fr.paris.lutece.plugins.userban.dao.commons.PaginationProperties;
import fr.paris.lutece.plugins.userban.dao.commons.ResultList;
import fr.paris.lutece.portal.service.jpa.JPALuteceDAO;


/**
 * The abstract class for all DAO
 * @param <K> the class for entity Key
 * @param <E> the entity class
 */
public abstract class AbstractDAO<K, E> extends JPALuteceDAO<K, E> implements IAbstractDAO<K, E>
{
    public static final Logger LOGGER = Logger.getLogger( AbstractDAO.class );
    
    public static final String LIKE_ENCLOSING = "%";
    
    /**
     * the method to implement to add the constraint
     * @param filter the bean filter
     * @param root the table root
     * @param query the criteria query
     * @param builder the criteria builder
     */
    protected void buildCriteriaQuery( AbstractFilter<K> filter, Root<E> root, CriteriaQuery<E> query,
        CriteriaBuilder builder )
    {
        LOGGER.debug( "use abstract" );
    }

    /**
     * Generate count query from criteria query and return a paged query.
     * 
     * @param <T> the generic type of criteria query
     * @param criteriaQuery criteria query
     * @param paginationProperties pagination data
     * @return query paged
     */
    protected <T> PagedQuery<E> createPagedQuery( CriteriaQuery<T> criteriaQuery,
            PaginationProperties paginationProperties )
    {

        // Generate count query
        EntityManager em = getEM( );
        CriteriaBuilder cb = em.getCriteriaBuilder( );
        CriteriaQuery<Long> countQuery = cb.createQuery( Long.class );
        countQuery.select( cb.count( countQuery.from( criteriaQuery.getResultType( ) ) ) );

        countQuery.getRoots( ).clear( );
        for ( Root<?> root : criteriaQuery.getRoots( ) )
        {
            countQuery.getRoots( ).add( root );
        }

        if ( criteriaQuery.getRestriction( ) != null )
        {
            countQuery.where( criteriaQuery.getRestriction( ) ).distinct( true );
        }

        // Create the paged query
        PagedQuery<E> pq = new PagedQuery<E>( em.createQuery( criteriaQuery ), em.createQuery( countQuery ),
                paginationProperties );

        return pq;
    }

    /**
     * Return all entities paged.
     * 
     * @param paginationProperties properties for pagination
     * @return the result list
     */
    @Override
    public ResultList<E> findAll( PaginationProperties paginationProperties )
    {

        Query query = getEM( ).createQuery( "SELECT e FROM " + getEntityClassName( ) + " e " );

        Query countQuery = getEM( ).createQuery( "SELECT count(e) FROM " + getEntityClassName( ) + " e " );

        PagedQuery<E> pq = new PagedQuery<E>( query, countQuery, paginationProperties );
        return pq.getResultList( );
    }

    /**
     * Add a predicate to an existing query
     * 
     * @param query existing query
     * @param exp restriction
     */
    protected void addRestriction( CriteriaQuery<?> query, Expression<Boolean> exp )
    {
        CriteriaBuilder builder = getEM( ).getCriteriaBuilder( );
        Predicate restriction = query.getRestriction( );
        if ( restriction == null )
        {
            query.where( exp );
        }
        else
        {
            query.where( builder.and( restriction, exp ) );
        }
    }

    /**
     * Add the order by parameter to the query.
     * 
     * @param filter the filter
     * @param root the offer root
     * @param query the criteria query
     * @param builder the criteria builder
     */
    protected void buildSortQuery( AbstractFilter<K> filter, Root<E> root, CriteriaQuery<E> query, CriteriaBuilder builder )
    {
        if ( filter.getOrders( ) != null && !filter.getOrders( ).isEmpty( ) )
        {
            List<Order> orderList = new LinkedList<Order>( );

            // get asc order
            for ( String order : filter.getOrders( ) )
            {
                if ( filter.getOrderAsc( ) )
                {
                    orderList.add( builder.asc( root.get( order ) ) );
                }
                else
                {
                    orderList.add( builder.desc( root.get( order ) ) );
                }
            }

            query.orderBy( orderList );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * fr.paris.lutece.plugins.generic.dao.IAbstractDAO#find(fr.paris.lutece
     * .plugins.generic.bean.AbstractFilter, java.lang.Class,
     * fr.paris.lutece.plugins.generic.dao.commons.PaginationProperties)
     */
    @Override
    public ResultList<E> find( AbstractFilter<K> filter, Class<E> type, PaginationProperties paginationProperties )
    {
        EntityManager em = getEM( );
        CriteriaBuilder cb = em.getCriteriaBuilder( );
        CriteriaQuery<E> cq = cb.createQuery( type );

        Root<E> root = cq.from( type );
        buildCriteriaQuery( filter, root, cq, cb );
        buildSortQuery( filter, root, cq, cb );
        cq.distinct( true );

        return createPagedQuery( cq, paginationProperties ).getResultList( );
    }
    
    /**
     * Encloses string with percent "%"
     * @param strToEnclose string to enclose
     * @return <code>"%" + strToEnclose + "%"</code>
     */
    public static String buildCriteriaLikeString( String strToEnclose )
    {
        return LIKE_ENCLOSING + strToEnclose + LIKE_ENCLOSING;
    }

}
