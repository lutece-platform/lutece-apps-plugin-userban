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
package fr.paris.lutece.plugins.userban.web.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import fr.paris.lutece.plugins.userban.bean.user.User;
import fr.paris.lutece.plugins.userban.bean.user.UserFilter;
import fr.paris.lutece.plugins.userban.service.user.IUserService;
import fr.paris.lutece.plugins.userban.web.AbstractJspBean;
import fr.paris.lutece.portal.service.admin.AccessDeniedException;
import fr.paris.lutece.portal.service.message.AdminMessage;
import fr.paris.lutece.portal.service.message.AdminMessageService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.portal.web.constants.Messages;
import fr.paris.lutece.util.beanvalidation.ValidationError;
import fr.paris.lutece.util.datatable.DataTableManager;
import fr.paris.lutece.util.html.HtmlTemplate;


/**
 * The User Jsp Bean
 * @author jchaline
 */
public class UserJspBean extends AbstractJspBean
{

    public static final Logger LOGGER = Logger.getLogger( UserJspBean.class );

    public static final String RIGHT_MANAGE_USER = "USERBAN_USER_MANAGEMENT";

    private static final long serialVersionUID = 8855491560517517603L;

    private static final String JSP_MANAGE_USER = "jsp/admin/plugins/userban/user/ManageUser.jsp";
    private static final String JSP_DO_DELETE_USER = "jsp/admin/plugins/userban/user/DoDeleteUser.jsp";

    private static final String I18N_CONFIRMATION_DELETE_USER = "userban.user.delete.confirmation";
    private static final String I18N_TITLE_DELETE_USER = "userban.user.delete.title";

    private static final String MACRO_COLUMN_ACTIONS_USER = "columnActionsUser";

    private static final String MARK_USER_JPA_FIELD_ID = "_strId";
    private static final String MARK_DATA_TABLE = "dataTable";

    private static final String TEMPLATE_MANAGE_USER = "admin/plugins/userban/user/manage_user.html";
    private static final String TEMPLATE_SAVE_USER = "admin/plugins/userban/user/save_user.html";

    private static final String BEAN_USER_SERVICE = "userban.userService";

    /**
     * for moment, this plugin is only use with the sate "ban", but the goal is
     * to give the way to choose the state when other lutece plugin call the
     * methods of userban
     */
    private static final String PARAMETER_STATE_BAN = "ban";
    private static final String PARAMETER_PREFIX_BEAN_FIELD = "userban.user.field.";

    private static final String MARK_USER_ID = "user_id";
    private static final String MARK_DATA_TABLE_USER = "dataTableUser";

    private IUserService _serviceUser;
    private List<String> defaultOrder;

    @Override
    public void init( HttpServletRequest request, String strRight ) throws AccessDeniedException
    {
        super.init( request, strRight );
        _serviceUser = (IUserService) SpringContextService.getBean( BEAN_USER_SERVICE );
        defaultOrder = new ArrayList<String>();
        defaultOrder.add( MARK_USER_JPA_FIELD_ID );
    }

    /**
     * Get the manage users page
     * 
     * @param request http the request
     * @return the page with users list
     */
    public String getManageUser( HttpServletRequest request )
    {
        Map<String, Object> model = super.getManageBeansModel( request );

        @SuppressWarnings( "unchecked" )
        DataTableManager<User> dataTableFromSession = (DataTableManager<User>) request.getSession( ).getAttribute(
                MARK_DATA_TABLE );
        DataTableManager<User> dataTableUser = dataTableFromSession != null ? dataTableFromSession
                : new DataTableManager<User>( JSP_MANAGE_USER, "", 10, true );
        if ( dataTableFromSession == null )
        {
            dataTableUser.addColumn( "userban.user.field.id", "id", false );
            dataTableUser.addColumn( "userban.user.field.date", "date?date", false );
            dataTableUser.addColumn( "userban.user.field.commentaire", "commentaire", false );
            dataTableUser.addColumn( "userban.user.field.motif", "motif", false );
            dataTableUser.addFreeColumn( "userban.transverse.label.actions", MACRO_COLUMN_ACTIONS_USER );
        }

        UserFilter userFilter = new UserFilter( );
        populate( userFilter, request );
        userFilter.setOrderAsc( true );
        userFilter.setOrders( defaultOrder );

        List<User> listUsers = _serviceUser.find( userFilter, null );
        dataTableUser.filterSortAndPaginate( request, listUsers );
        model.put( MARK_DATA_TABLE_USER, dataTableUser );
        model.put( MARK_FILTER, userFilter );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_MANAGE_USER, getLocale( ), model );
        dataTableUser.clearItems( );
        request.getSession( ).setAttribute( MARK_DATA_TABLE, dataTableUser );

        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Get the create page for user
     * @param request the http request
     * @return the html page
     */
    public String getSaveUser( HttpServletRequest request )
    {
        Map<String, Object> model = super.getSaveBeansModel( request );
        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_SAVE_USER, getLocale( ), model );
        return getAdminPage( template.getHtml( ) );
    }

    /**
     * Save the user
     * @param request the http request
     * @return the url for the next page
     */
    public String doSaveUser( HttpServletRequest request )
    {
        if ( request.getParameter( MARK_BUTTON_CANCEL ) != null )
        {
            return doGoBack( request, JSP_MANAGE_USER );
        }

        User bean = new User( );
        populate( bean, request );

        List<ValidationError> errors = validate( bean, PARAMETER_PREFIX_BEAN_FIELD );
        String urlResult = null;
        if ( errors.isEmpty( ) )
        {
            //force state for moment
            bean.setState( PARAMETER_STATE_BAN );

            //date is alway the actual date
            bean.setDate( new Date( ) );

            _serviceUser.doSaveBean( bean );
            urlResult = doGoBack( request, JSP_MANAGE_USER );
        }
        else
        {
            urlResult = AdminMessageService.getMessageUrl( request, Messages.MESSAGE_INVALID_ENTRY, errors );
        }

        return urlResult;
    }

    /**
     * Returns the confirmation message to delete an user
     * 
     * @param request The Http request
     * @return the html code message
     */
    public String getDeleteUser( HttpServletRequest request )
    {
        String strUserId = request.getParameter( MARK_USER_ID );

        Map<String, Object> urlParam = new HashMap<String, Object>( );
        urlParam.put( MARK_USER_ID, strUserId );

        String strJspBack = JSP_MANAGE_USER;

        return AdminMessageService.getMessageUrl( request, I18N_CONFIRMATION_DELETE_USER, null, I18N_TITLE_DELETE_USER,
                JSP_DO_DELETE_USER, "_self", AdminMessage.TYPE_CONFIRMATION, urlParam, strJspBack );
    }

    /**
     * Delete an user
     * 
     * @param request The Http request
     * @return the html code message
     */
    public String doDeleteUser( HttpServletRequest request )
    {
        String strUserId = request.getParameter( MARK_USER_ID );

        _serviceUser.doDeleteBean( strUserId );

        return doGoBack( request, JSP_MANAGE_USER );
    }

}