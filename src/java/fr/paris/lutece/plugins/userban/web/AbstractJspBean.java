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
package fr.paris.lutece.plugins.userban.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.util.datatable.DataTableManager;


/**
 * 
 * @author jchaline
 * 
 */
public class AbstractJspBean extends PluginAdminPageJspBean
{

    public static final int N_DEFAULT_ITEMS_PER_PAGE = 50;
    
    protected int _nItemsPerPage;
    protected final static String MARK_BUTTON_CANCEL = "cancel";
    protected static final String MARK_JSP_BACK = "jsp_back";
    protected static final String MARK_FILTER = "filter";

    private String _strCurrentPageIndex = StringUtils.EMPTY;
    private static final long serialVersionUID = 8195930894349438376L;

    /**
     * Get model for manage beans
     * @param request the http request
     * @return the abstract model
     */
    public Map<String, Object> getManageBeansModel( HttpServletRequest request )
    {
        Map<String, Object> model = new HashMap<String, Object>( );

        return model;
    }

    /**
     * Get model for save beans
     * @param request the http request
     * @return the abstract model
     */
    public Map<String, Object> getSaveBeansModel( HttpServletRequest request )
    {
        Map<String, Object> model = new HashMap<String, Object>( );

        return model;
    }

    /**
     * Return the url of the JSP which called the last action
     * @param request The Http request
     * @return The url of the last JSP
     */
    protected String doGoBack( HttpServletRequest request, String JSP_MANAGE_BEAN )
    {
        String strJspBack = request.getParameter( MARK_JSP_BACK );

        return StringUtils.isNotBlank( strJspBack ) ? ( AppPathService.getBaseUrl( request ) + strJspBack )
                : AppPathService.getBaseUrl( request ) + JSP_MANAGE_BEAN;
    }
    
    /**
     * Get the correct filter to use with data table manager
     * @param request the http request
     * @param filter the bean filter get with request
     * @param markFilter the key of the filter
     * @param dataTable the datatable to use
     * @param <T> the bean filter type
     * @return the filter to use
     */
    protected <T> T getFilterToUse( HttpServletRequest request, T filter, String markFilter,
            DataTableManager<?> dataTable )
    {

        @SuppressWarnings( "unchecked" )
        T filterFromSession = (T) request.getSession( ).getAttribute( markFilter );
        //1) est-ce qu'une recherche vient d'être faite ? 2) est-ce qu'un filtre existe en session ? 3) est-ce que le filtre en session est d'un type héritant du fitre fournit en parametre ?
        T filterToUse = request.getParameter( MARK_FILTER ) != null || filterFromSession == null
                || !filterFromSession.getClass( ).isAssignableFrom( filter.getClass( ) ) ? dataTable
                .getAndUpdateFilter( request, filter ) : filterFromSession;
        return filterToUse;
    }
}
