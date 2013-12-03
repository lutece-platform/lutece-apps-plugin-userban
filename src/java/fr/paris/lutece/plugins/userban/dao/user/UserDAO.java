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
package fr.paris.lutece.plugins.userban.dao.user;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.plugins.userban.bean.AbstractFilter;
import fr.paris.lutece.plugins.userban.bean.user.User;
import fr.paris.lutece.plugins.userban.bean.user.UserFilter;
import fr.paris.lutece.plugins.userban.bean.user.User_;
import fr.paris.lutece.plugins.userban.dao.AbstractDAO;


/**
 * The User DAO class
 * @author jchaline
 */
public class UserDAO extends AbstractDAO<String, User> implements IUserDAO<String, User>
{

    public static final String PLUGIN_NAME = "userban";

    @Override
    protected void buildCriteriaQuery( AbstractFilter<String> abstractFilter, Root<User> root, CriteriaQuery<User> query,
            CriteriaBuilder builder )
    {
        super.buildCriteriaQuery( abstractFilter, root, query, builder );
        UserFilter trueFilter = (UserFilter) abstractFilter;
        List<Predicate> listPredicates = new ArrayList<Predicate>( );

        if ( StringUtils.isNotBlank( trueFilter.getId( ) ) )
        {
            listPredicates
                    .add( builder.like( root.get( User_._id ), buildCriteriaLikeString( trueFilter.getId( ) ) ) );
        }

        if ( !listPredicates.isEmpty( ) )
        {
            query.where( listPredicates.toArray( new Predicate[listPredicates.size( )] ) );
        }
    }

    @Override
    public String getPluginName( )
    {
        return PLUGIN_NAME;
    }
}