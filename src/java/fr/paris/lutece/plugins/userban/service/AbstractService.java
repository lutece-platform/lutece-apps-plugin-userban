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
package fr.paris.lutece.plugins.userban.service;

import java.util.List;

import fr.paris.lutece.plugins.userban.bean.AbstractBean;
import fr.paris.lutece.plugins.userban.bean.AbstractFilter;
import fr.paris.lutece.plugins.userban.dao.IAbstractDAO;
import fr.paris.lutece.plugins.userban.dao.commons.PaginationProperties;




/**
 * The abstract class service
 * @param <K> the bean primary key
 * @param <E> the bean class
 */
public abstract class AbstractService<K, E extends AbstractBean<K>> implements IAbstractService<K,E>
{
    /**
     * Get the bean dao
     * @return the dao
     */
    protected abstract IAbstractDAO<K,E> getDAO( );

    /**
     * Get the class type of the bean
     * @return the class type
     */
    protected abstract Class<E> getBeanClass();
    
    @Override
    public E findByPrimaryKey( K id )
    {
        E bean = getDAO().findById( id );
        return bean;
    }

    @Override
    public List<E> findAll( PaginationProperties paginationProperties )
    {
        return getDAO().findAll( paginationProperties );
    }

    @Override
    public List<E> find( AbstractFilter<K> filter, PaginationProperties paginationProperties )
    {
        return getDAO( ).find( filter, getBeanClass(), paginationProperties );
    }

    @Override
    public void doSaveBean( E bean )
    {
        E existingBean = getDAO().findById( bean.getId( ) );
        if ( existingBean != null )
        {
            getDAO( ).update( bean );
        }
        else
        {
            getDAO( ).create( bean );
        }
    }

    @Override
    public void doDeleteBean( K id )
    {
        E existingBean = getDAO( ).findById( id );
        if ( existingBean != null )
        {
            getDAO( ).remove( id );
        }
    }

}