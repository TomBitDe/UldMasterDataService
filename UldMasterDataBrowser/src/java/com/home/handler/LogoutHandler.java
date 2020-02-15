package com.home.handler;

import javax.faces.context.FacesContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Do the application logout
 */
public class LogoutHandler {
    private static final Logger LOG = LogManager.getLogger(LogoutHandler.class.getName());

    public LogoutHandler() {
        super();
        LOG.debug("-->CTOR");
    }

    /**
     * Invalidate the current session for logout
     *
     * @return the page to go to
     */
    public String logout() {
        LOG.info("-->LOGOUT");

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        LOG.debug("<--LOGOUT");

        return "/login.html?faces-redirect=true";
    }
}
