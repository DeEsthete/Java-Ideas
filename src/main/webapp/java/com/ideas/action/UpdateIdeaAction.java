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
import java.util.Calendar;
import java.util.Date;

import static com.ideas.util.AppConstants.ATTR_USER_TOKEN;

public class UpdateIdeaAction implements Action {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IdeaDao ideaDao = new IdeaDao();
        UserDao userDao = new UserDao();
        User user = userDao.findByToken(CookieHelper.getCookie(request, ATTR_USER_TOKEN));
        int ideaId = Integer.parseInt(request.getParameter("ideaId"));
        Idea idea = ideaDao.findById(ideaId);

        if (idea.getUserId() != user.getId()) {
            return;
        }

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        if (cal.getTime().getTime() <= idea.getDateCreate().getTime()) {
            return;
        }

        idea.setContent(request.getParameter("content"));
        ideaDao.update(idea);
        response.sendRedirect("/fs/idea?ideaId=" + ideaId);
    }
}
