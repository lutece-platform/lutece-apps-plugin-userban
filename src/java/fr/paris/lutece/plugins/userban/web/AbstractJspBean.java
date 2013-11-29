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

import fr.paris.lutece.plugins.userban.dao.commons.PaginationProperties;
import fr.paris.lutece.plugins.userban.dao.commons.PaginationPropertiesImpl;
import fr.paris.lutece.plugins.userban.dao.commons.ResultList;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.web.admin.PluginAdminPageJspBean;
import fr.paris.lutece.portal.web.util.LocalizedDelegatePaginator;
import fr.paris.lutece.util.html.DelegatePaginator;
import fr.paris.lutece.util.html.Paginator;


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

    private String _strCurrentPageIndex = StringUtils.EMPTY;
    private static final long serialVersionUID = 8195930894349438376L;

    private static final String MARK_JSP_BACK = "jsp_back";

    /**
     * Return a paginator for the view using parameter in http request.
     * 
     * @param <T> the generic type
     * @param request http request
     * @param list bean list to paginate
     * @return paginator
     */
    protected <T> DelegatePaginator<T> getPaginator( HttpServletRequest request, ResultList<T> list )
    {

        _strCurrentPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        _nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                N_DEFAULT_ITEMS_PER_PAGE );
        String strBaseUrl = request.getRequestURL( ).toString( );
        LocalizedDelegatePaginator<T> paginator = new LocalizedDelegatePaginator<T>( list, _nItemsPerPage, strBaseUrl,
                Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex, list.getTotalResult( ), getLocale( ) );

        return paginator;
    }

    /**
     * Return a bean for pagination in service/dao using parameter in http
     * request
     * @param request http request
     * @return paginator
     */
    protected PaginationProperties getPaginationProperties( HttpServletRequest request )
    {
        String strPageIndex = Paginator.getPageIndex( request, Paginator.PARAMETER_PAGE_INDEX, _strCurrentPageIndex );
        int nCurrentPageIndex = 1;
        if ( StringUtils.isNotEmpty( strPageIndex ) )
        {
            nCurrentPageIndex = Integer.valueOf( strPageIndex );
        }
        int nItemsPerPage = Paginator.getItemsPerPage( request, Paginator.PARAMETER_ITEMS_PER_PAGE, _nItemsPerPage,
                N_DEFAULT_ITEMS_PER_PAGE );

        return new PaginationPropertiesImpl( ( nCurrentPageIndex - 1 ) * nItemsPerPage, nItemsPerPage );
    }

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
}
