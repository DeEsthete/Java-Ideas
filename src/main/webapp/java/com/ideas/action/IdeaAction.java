package com.ideas.action;

import com.ideas.dao.IdeaDao;
import com.ideas.dao.RateDao;
import com.ideas.dao.UserDao;
import com.ideas.entity.Idea;
import com.ideas.entity.Rate;
import com.ideas.entity.User;
import com.ideas.model.IdeaViewModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.ideas.util.AppConstants.URL_IDEAS_PAGE;
import static com.ideas.util.AppConstants.URL_IDEA_PAGE;

public class IdeaAction implements Action {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IdeaDao articleDao = new IdeaDao();
        UserDao userDao = new UserDao();
        RateDao rateDao = new RateDao();

        int ideaId = Integer.parseInt(request.getParameter("id"));
        Idea idea = articleDao.findById(ideaId);

        IdeaViewModel model = new IdeaViewModel();
        model.setId(idea.getId());
        model.setUserId(idea.getUserId());
        model.setUserNickname(userDao.findById(idea.getUserId()).getNickname());
        model.setContent(idea.getContent());
        model.setDateCreate(idea.getDateCreate());

        List<Rate> likes = rateDao.getIdeaRates(idea.getId()).stream().filter(r -> r.getIsLike()).collect(Collectors.toList());
        List<Rate> dislikes = rateDao.getIdeaRates(idea.getId()).stream().filter(r -> !r.getIsLike()).collect(Collectors.toList());
        model.setLikesCount(likes.size());
        model.setDislikesCount(dislikes.size());
        model.setUsersLike(likes.stream().map(r -> userDao.findById(r.getUserId())).collect(Collectors.toList()));
        model.setUsersDislike(dislikes.stream().map(r -> userDao.findById(r.getUserId())).collect(Collectors.toList()));

        request.setAttribute("entity", model);
        request.getRequestDispatcher(URL_IDEA_PAGE).forward(request, response);
    }
}
