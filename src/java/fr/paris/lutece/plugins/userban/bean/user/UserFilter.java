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
package fr.paris.lutece.plugins.userban.bean.user;

import java.util.Date;

import fr.paris.lutece.plugins.userban.bean.AbstractFilter;


/**
 * The User class for filter
 * @author jchaline
 */
public class UserFilter extends AbstractFilter
{

    private static final long serialVersionUID = -3554777257603912111L;
    private String _state;
    private String _guid;
    private Date _date;
    private String _commentaire;
    private String _motif;

    /**
     * @return the commentaire
     */
    public String getCommentaire( )
    {
        return _commentaire;
    }

    /**
     * @param commentaire the commentaire to set
     */
    public void setCommentaire( String commentaire )
    {
        this._commentaire = commentaire;
    }

    /**
     * @return the state
     */
    public String getState( )
    {
        return _state;
    }

    /**
     * @param state the state to set
     */
    public void setState( String state )
    {
        this._state = state;
    }

    /**
     * @return the date
     */
    public Date getDate( )
    {
        return _date;
    }

    /**
     * @param date the date to set
     */
    public void setDate( Date date )
    {
        this._date = date;
    }

    /**
     * @return the motif
     */
    public String getMotif( )
    {
        return _motif;
    }

    /**
     * @param motif the motif to set
     */
    public void setMotif( String motif )
    {
        this._motif = motif;
    }

    /**
     * @return the guid
     */
    public String getGuid( )
    {
        return _guid;
    }

    /**
     * @param guid the guid to set
     */
    public void setGuid( String guid )
    {
        this._guid = guid;
    }

}