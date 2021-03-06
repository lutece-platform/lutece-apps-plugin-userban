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
package fr.paris.lutece.plugins.userban.bean.user;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import fr.paris.lutece.plugins.userban.bean.AbstractBean;


/**
 * The User Bean
 * @author jchaline
 */
@Entity
@Table( name = "userban_user" )
public class User extends AbstractBean<String>
{
    private static final long serialVersionUID = 1L;

    @Column( name = "state" )
    private String _strState;

    @Id
    @Column( name = "id" )
    @Email
    @NotEmpty(message = "#i18n{portal.validation.message.notEmpty}")
    private String _strId;

    @Column( name = "date" )
    private Date _date;

    @Column( name = "commentaire" )
    private String _strCommentaire;
    
    @Column( name = "motif" )
    @NotEmpty(message = "#i18n{portal.validation.message.notEmpty}")
    private String _strMotif;

    /**
     * the state setter
     * @param state the state to set
     */
    public void setState( String state )
    {
        this._strState = state;
    }

    /**
     * the state getter
     * @return state
     */
    public String getState( )
    {
        return this._strState;
    }

    /**
     * the date setter
     * @param date the date to set
     */
    public void setDate( Date date )
    {
        this._date = date;
    }

    /**
     * the date getter
     * @return date
     */
    public Date getDate( )
    {
        return this._date;
    }

    /**
     * the commentaire setter
     * @param commentaire the commentaire to set
     */
    public void setCommentaire( String commentaire )
    {
        this._strCommentaire = commentaire;
    }

    /**
     * the commentaire getter
     * @return commentaire
     */
    public String getCommentaire( )
    {
        return this._strCommentaire;
    }

    @Override
    public String getId( )
    {
        return _strId;
    }

    @Override
    public void setId( String id )
    {
        this._strId = id;
    }

    /**
     * @return the motif
     */
    public String getMotif( )
    {
        return _strMotif;
    }


    /**
     * @param motif the motif to set
     */
    public void setMotif( String motif )
    {
        this._strMotif = motif;
    }
}