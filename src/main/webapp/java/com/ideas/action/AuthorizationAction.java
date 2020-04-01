package com.ideas.action;

import com.ideas.dao.UserDao;
import com.ideas.entity.User;
import com.ideas.helper.CookieHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

import static com.ideas.util.AppConstants.ATTR_USER_TOKEN;
import static com.ideas.util.AppConstants.URL_AUTHORIZATION_PAGE;

public class AuthorizationAction implements Action {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getMethod().equals("GET")) {
            request.getRequestDispatcher(URL_AUTHORIZATION_PAGE).forward(request, response);
        } else if (request.getMethod().equals("POST")) {
            UserDao userDao = new UserDao();
            User user = userDao.findByLoginPassword(request.getParameter("login"), request.getParameter("password"));
            if (user == null) {
                request.setAttribute("loginError", true);
                request.getRequestDispatcher(URL_AUTHORIZATION_PAGE).forward(request, response);
            } else {
                //Cookie user token
                UUID uuid = UUID.randomUUID();
                String randomUUIDString = uuid.toString();
                user.setToken(randomUUIDString);
                userDao.update(user);
                CookieHelper.setCookie(response, ATTR_USER_TOKEN, user.getToken());
                response.sendRedirect("/fs/ideas");
            }
        }
    }
}
