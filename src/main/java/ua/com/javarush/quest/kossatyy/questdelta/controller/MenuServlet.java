package ua.com.javarush.quest.kossatyy.questdelta.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ua.com.javarush.quest.kossatyy.questdelta.entity.Role;

import java.io.IOException;

@WebServlet(name = "MenuServlet", value = "/menu")
public class MenuServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.setAttribute("user.role", Role.GUEST);
        RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/jsp/menu.jsp");
        rd.forward(req, resp);
    }
}