/*
 * Copyright (c) 2002-2014, Mairie de Paris
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
package fr.paris.lutece.plugins.userban.dao.commons;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TemporalType;


/**
 * Adaptater for paginated query.
 * 
 * @param <K> the bean class
 */
public class PagedQuery<K> implements Query
{

    /** The _query. */
    private Query _query;

    /** The _count query. */
    private final Query _countQuery;

    /** The _pagination properties. */
    private final PaginationProperties _paginationProperties;

    /**
     * Creates a new PaginatedQuery.java object.
     * 
     * @param query query for getting results
     * @param countQuery query for counting total result
     * @param paginationProperties the pagination properties
     */
    public PagedQuery( Query query, Query countQuery, PaginationProperties paginationProperties )
    {
        super( );
        this._query = query;
        this._countQuery = countQuery;
        this._paginationProperties = paginationProperties;

        if ( this._paginationProperties != null )
        {
            this._query.setMaxResults( this._paginationProperties.getPageSize( ) );
            this._query.setFirstResult( this._paginationProperties.getFirstResult( ) );
        }
    }

    @Override
    public ResultList<K> getResultList( )
    {
        ResultList<K> resultList = new ResultList<K>( );
        Long nbTotalResults = -1L;
        if ( this._countQuery != null )
        {
            nbTotalResults = (Long) this._countQuery.getSingleResult( );
        }
        resultList.addAll( _query.getResultList( ) );
        resultList.setTotalResult( nbTotalResults.intValue( ) );
        return resultList;
    }

    @Override
    public Object getSingleResult( )
    {
        return _query.getSingleResult( );
    }

    @Override
    public int executeUpdate( )
    {
        return _query.executeUpdate( );
    }

    @Override
    public Query setMaxResults( int maxResult )
    {
        return _query.setMaxResults( maxResult );
    }

    @Override
    public int getMaxResults( )
    {
        return _query.getMaxResults( );
    }

    @Override
    public Query setFirstResult( int startPosition )
    {
        return _query.setFirstResult( startPosition );
    }

    @Override
    public int getFirstResult( )
    {
        return _query.getFirstResult( );
    }

    @Override
    public Query setHint( String hintName, Object value )
    {
        return _query.setHint( hintName, value );
    }

    @Override
    public Map<String, Object> getHints( )
    {
        return _query.getHints( );
    }

    @Override
    public <T> Query setParameter( Parameter<T> param, T value )
    {
        return _query.setParameter( param, value );
    }

    @Override
    public Query setParameter( Parameter<Calendar> param, Calendar value, TemporalType temporalType )
    {
        return _query.setParameter( param, value, temporalType );
    }

    @Override
    public Query setParameter( Parameter<Date> param, Date value, TemporalType temporalType )
    {
        return _query.setParameter( param, value, temporalType );
    }

    @Override
    public Query setParameter( String name, Object value )
    {
        return _query.setParameter( name, value );
    }

    @Override
    public Query setParameter( String name, Calendar value, TemporalType temporalType )
    {
        return _query.setParameter( name, value, temporalType );
    }

    @Override
    public Query setParameter( String name, Date value, TemporalType temporalType )
    {
        return _query.setParameter( name, value, temporalType );
    }

    @Override
    public Query setParameter( int position, Object value )
    {
        return _query.setParameter( position, value );
    }

    @Override
    public Query setParameter( int position, Calendar value, TemporalType temporalType )
    {
        return _query.setParameter( position, value, temporalType );
    }

    @Override
    public Query setParameter( int position, Date value, TemporalType temporalType )
    {
        return _query.setParameter( position, value, temporalType );
    }

    @Override
    public Set<Parameter<?>> getParameters( )
    {
        return _query.getParameters( );
    }

    @Override
    public Parameter<?> getParameter( String name )
    {
        return _query.getParameter( name );
    }

    @Override
    public <T> Parameter<T> getParameter( String name, Class<T> type )
    {
        return _query.getParameter( name, type );
    }

    @Override
    public Parameter<?> getParameter( int position )
    {
        return _query.getParameter( position );
    }

    @Override
    public <T> Parameter<T> getParameter( int position, Class<T> type )
    {
        return _query.getParameter( position, type );
    }

    @Override
    public boolean isBound( Parameter<?> param )
    {
        return _query.isBound( param );
    }

    @Override
    public <T> T getParameterValue( Parameter<T> param )
    {
        return _query.getParameterValue( param );
    }

    @Override
    public Object getParameterValue( String name )
    {
        return _query.getParameterValue( name );
    }

    @Override
    public Object getParameterValue( int position )
    {
        return _query.getParameterValue( position );
    }

    @Override
    public Query setFlushMode( FlushModeType flushMode )
    {
        return _query.setFlushMode( flushMode );
    }

    @Override
    public FlushModeType getFlushMode( )
    {
        return _query.getFlushMode( );
    }

    @Override
    public Query setLockMode( LockModeType lockMode )
    {
        return _query.setLockMode( lockMode );
    }

    @Override
    public LockModeType getLockMode( )
    {
        return _query.getLockMode( );
    }

    @Override
    public <T> T unwrap( Class<T> cls )
    {
        return _query.unwrap( cls );
    }

    /**
     * Gets the query.
     * 
     * @return the query
     */
    public Query getQuery( )
    {
        return _query;
    }

    /**
     * Sets the query.
     * 
     * @param query the query to set
     */
    public void setQuery( Query query )
    {
        this._query = query;
    }

}
