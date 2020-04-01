package com.ideas.action;

import com.ideas.dao.IdeaDao;
import com.ideas.dao.UserDao;
import com.ideas.entity.Idea;
import com.ideas.entity.User;
import com.ideas.helper.CookieHelper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.ideas.util.AppConstants.ATTR_USER_TOKEN;

public class CreateIdeaAction implements Action {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IdeaDao ideaDao = new IdeaDao();
        UserDao userDao = new UserDao();

        String token = CookieHelper.getCookie(request, ATTR_USER_TOKEN);
        if (token == null) {
            response.sendRedirect("/fs/authorization");
            return;
        }

        User user = userDao.findByToken(token);
        Idea idea = new Idea();
        idea.setUserId(user.getId());
        idea.setDateCreate(new Date());
        idea.setContent(request.getParameter("content"));

        ideaDao.insert(idea);
        response.sendRedirect("/fs/ideas");
    }
}
