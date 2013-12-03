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
package fr.paris.lutece.plugins.userban.service.user;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import fr.paris.lutece.plugins.userban.bean.AbstractFilter;
import fr.paris.lutece.plugins.userban.bean.user.User;
import fr.paris.lutece.plugins.userban.dao.commons.PaginationProperties;
import fr.paris.lutece.plugins.userban.dao.commons.ResultList;
import fr.paris.lutece.plugins.userban.dao.user.UserDAO;
import fr.paris.lutece.plugins.userban.service.AbstractService;


/**
 * The User class service
 * @author jchaline
 */
public class UserService extends AbstractService<String, User> implements IUserService
{
    public static final Logger LOGGER = Logger.getLogger( UserService.class );

    @Inject
    @Named( "userban.userDAO" )
    private UserDAO _daoUser;

    @Override
    public User findByPrimaryKey( String id )
    {
        User bean = _daoUser.findById( id );
        return bean;
    }

    @Override
    public ResultList<User> findAll( PaginationProperties paginationProperties )
    {
        return _daoUser.findAll( paginationProperties );
    }

    @Override
    public ResultList<User> find( AbstractFilter filter, PaginationProperties paginationProperties )
    {
        return _daoUser.find( filter, User.class, paginationProperties );
    }

    @Override
    public void doSaveBean( User bean )
    {
        User existingBean = _daoUser.findById( bean.getId( ) );
        if ( existingBean != null )
        {
            _daoUser.update( bean );
        }
        else
        {
            _daoUser.create( bean );
        }
    }

    @Override
    public void doDeleteBean( String id )
    {
        User existingBean = _daoUser.findById( id );
        if ( existingBean != null )
        {
            _daoUser.remove( id );
        }
    }

    @Override
    public boolean userMatchState( String id, String state )
    {
        boolean match = false;

        if ( StringUtils.isNotBlank( state ) )
        {
            User existingBean = _daoUser.findById( id );

            if ( existingBean != null )
            {
                match = state.equals( existingBean.getState( ) );
            }
        }

        return match;
    }

}