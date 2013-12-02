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

import org.springframework.transaction.annotation.Transactional;

import fr.paris.lutece.plugins.userban.bean.AbstractFilter;
import fr.paris.lutece.plugins.userban.dao.commons.PaginationProperties;
import fr.paris.lutece.plugins.userban.dao.commons.ResultList;


/**
 * The User interface for service
 * @param <K> the primary key of the bean
 * @param <E> the bean class
 * @author jchaline
 */
@Transactional
public interface IAbstractService<K, E>
{

    /**
     * Find an entity by its Id
     * @param key The entity key
     * @return The entity object
     */
    E findByPrimaryKey( K key );
    
    /**
     * Create or update user in database
     * @param bean the bean to save
     */
    void doSaveBean( E bean );

    /**
     * Delete user in database
     * @param bean the bean to delete
     */
    void doDeleteBean( K bean );

    /**
     * Get all the bean from dao
     * @param paginationProperties the pagination properties
     * @return the bean list
     */
    abstract ResultList<E> findAll( PaginationProperties paginationProperties );

    /**
     * Get all the bean matching the filter from dao
     * @param filter the filter
     * @param paginationProperties the pagination properties
     * @return the bean list
     */
    abstract ResultList<E> find( AbstractFilter filter, PaginationProperties paginationProperties );
}