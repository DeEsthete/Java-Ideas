package com.ideas.action;

import com.ideas.dao.UserDao;
import com.ideas.entity.User;
import com.ideas.helper.CookieHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

import static com.ideas.util.AppConstants.ATTR_USER_TOKEN;

public class ActionFactory {
    private static final Map<String, Action> PAGES = new HashMap<>();

    public ActionFactory() {
        init();
    }

    private void init() {
    }

    public Action getAction(HttpServletRequest request, HttpServletResponse response) {
        String userToken = CookieHelper.getCookie(request, ATTR_USER_TOKEN);
        if (userToken != null) {
            UserDao userDao = new UserDao();
            User user = userDao.findByToken(userToken);
            if (user != null) {
                request.setAttribute("user", user);
            }
        }
        return PAGES.get(request.getMethod() + request.getPathInfo());
    }
}
