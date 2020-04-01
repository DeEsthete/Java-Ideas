package com.ideas.action;

import com.ideas.dao.IdeaDao;
import com.ideas.dao.RateDao;
import com.ideas.dao.UserDao;
import com.ideas.entity.Idea;
import com.ideas.entity.Rate;
import com.ideas.model.IdeaViewModel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static com.ideas.util.AppConstants.URL_IDEAS_PAGE;

public class IdeasAction implements Action {
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        IdeaDao articleDao = new IdeaDao();
        UserDao userDao = new UserDao();
        RateDao rateDao = new RateDao();

        String sortBy = request.getParameter("sortBy");

        List<Idea> ideas = articleDao.findAll();
        List<IdeaViewModel> ideaViewModelList = new ArrayList<>();

        ideas.forEach(i -> {
            IdeaViewModel model = new IdeaViewModel();
            model.setId(i.getId());
            model.setUserId(i.getUserId());
            model.setUserNickname(userDao.findById(i.getUserId()).getNickname());
            model.setContent(i.getContent());
            model.setDateCreate(i.getDateCreate());
            model.setLikesCount((int) rateDao.getIdeaRates(i.getId()).stream().filter(r -> r.getIsLike()).count());
            model.setDislikesCount((int) rateDao.getIdeaRates(i.getId()).stream().filter(r -> !r.getIsLike()).count());
            ideaViewModelList.add(model);
        });

        ideaViewModelList.sort((o1, o2) -> {
            if (sortBy.equals("likes")) {
                return o1.getLikesCount() - o2.getLikesCount();
            } else if (sortBy.equals("dislikes")) {
                return o1.getDislikesCount() - o2.getDislikesCount();
            } else {
                return o1.getDateCreate().compareTo(o2.getDateCreate());
            }
        });

        request.setAttribute("list", ideaViewModelList);
        request.getRequestDispatcher(URL_IDEAS_PAGE).forward(request, response);
    }
}
