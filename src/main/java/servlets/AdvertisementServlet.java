package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DAOAdvertisement;
import utils.ServletUtil;
import vo.Advertisement;
import vo.AppConstants;
import vo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdvertisementServlet", value = "/advertisement")
public class AdvertisementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            if (AppConstants.GET_ALL_ADVERTISEMENT.equals(req.getParameter("actionName"))) {
                getAllAdvertisement(req, resp);
            } else if (AppConstants.GET_FILTERED_ADVERTISEMENT.equals(req.getParameter("actionName"))) {
                getFilteredAdvertisement(req, resp);
            } else if (AppConstants.ADD_ADVERTISEMENT_TO_FAVORITES.equals(req.getParameter("actionName"))) {
                addAdvertisementToFavorite(req, resp);
            } else if (AppConstants.GET_FAVORITES_FOR_CURRENT_USER.equals(req.getParameter("actionName"))) {
                getFavoritesForCurrentUser(req, resp);
            } else if (AppConstants.REMOVE_FROM_FAVORITES_FOR_CURRENT_USER.equals(req.getParameter("actionName"))) {
                removeFromFavoritesForCurrentUser(req, resp);
            }
        } catch (Exception ex){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unknown Business Error");
        }

    }

    private void removeFromFavoritesForCurrentUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ServletUtil<Integer, Integer> servletUtil = new ServletUtil<>(req, resp);
        ObjectMapper mapper = new ObjectMapper();
        String json = servletUtil.parseJSONString();

        DAOAdvertisement daoAdvertisement = new DAOAdvertisement();
        Integer advertisementId = null;
        try {
            User user = (User) req.getSession().getAttribute("currentUser");
            Integer currentUserId = user.getUserId();
            advertisementId = mapper.readValue(json, Integer.class);
            advertisementId = daoAdvertisement.removeFromFavoritesForCurrentUser(currentUserId, advertisementId);
        } catch (Exception ex) {
            System.out.println("Exception in LoginServlet - doPost.");
            throw ex;
        }
        servletUtil.sendObject(advertisementId);
    }

    private void addAdvertisementToFavorite(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ServletUtil<Integer, Integer> servletUtil = new ServletUtil<>(req, resp);
        ObjectMapper mapper = new ObjectMapper();
        String json = servletUtil.parseJSONString();

        DAOAdvertisement daoAdvertisement = new DAOAdvertisement();
        Integer advertisementId = null;
        try {
            User user = (User) req.getSession().getAttribute("currentUser");
            Integer currentUserId = user.getUserId();
            advertisementId = mapper.readValue(json, Integer.class);
            advertisementId = daoAdvertisement.addAdvertisementToFavorite(currentUserId, advertisementId);
        } catch (Exception ex) {
            System.out.println("Exception in LoginServlet - doPost.");
            throw ex;
        }
        servletUtil.sendObject(advertisementId);
    }

    private void getFavoritesForCurrentUser(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ServletUtil<Integer, List<Advertisement>> servletUtil = new ServletUtil<>(req, resp);
        ObjectMapper mapper = new ObjectMapper();
        String json = servletUtil.parseJSONString();

        User user = (User) req.getSession().getAttribute("currentUser");
        Integer currentUserId = user.getUserId();

        DAOAdvertisement daoAdvertisement = new DAOAdvertisement();
        List<Advertisement> advertisementList = null;
        try {
            advertisementList = daoAdvertisement.getFavoritesForCurrentUser(currentUserId);
        } catch (Exception ex) {
            System.out.println("Exception in LoginServlet - doPost.");
            throw ex;
        }
        servletUtil.sendObject(advertisementList);
    }

    public void getAllAdvertisement(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ServletUtil<Integer, List<Advertisement>> servletUtil = new ServletUtil<>(req, resp);
        ObjectMapper mapper = new ObjectMapper();

        DAOAdvertisement daoAdvertisement = new DAOAdvertisement();
        List<Advertisement> advertisementList = null;
        try {
            advertisementList = daoAdvertisement.getAllAdvertisement();
        } catch (Exception ex) {
            System.out.println("Exception in LoginServlet - doPost.");
            throw ex;
        }
        servletUtil.sendObject(advertisementList);
    }

    public void getFilteredAdvertisement(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        ServletUtil<Integer, List<Advertisement>> servletUtil = new ServletUtil<>(req, resp);
        ObjectMapper mapper = new ObjectMapper();
        String json = servletUtil.parseJSONString();

        Advertisement filterAdvertisement =  mapper.readValue(json, Advertisement.class);

        DAOAdvertisement daoAdvertisement = new DAOAdvertisement();
        List<Advertisement> advertisementList = null;
        try {
            advertisementList = daoAdvertisement.getFilteredAdvertisement(filterAdvertisement);
        } catch (Exception ex) {
            System.out.println("Exception in LoginServlet - doPost.");
            throw ex;
        }
        servletUtil.sendObject(advertisementList);
    }

}