package ua.com.javarush.quest.kossatyy.questdelta.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import ua.com.javarush.quest.kossatyy.questdelta.dto.UserDto;
import ua.com.javarush.quest.kossatyy.questdelta.entity.User;
import ua.com.javarush.quest.kossatyy.questdelta.mapper.Mapper;
import ua.com.javarush.quest.kossatyy.questdelta.mapper.UserMapper;
import ua.com.javarush.quest.kossatyy.questdelta.service.UserService;
import ua.com.javarush.quest.kossatyy.questdelta.utils.Attribute;
import ua.com.javarush.quest.kossatyy.questdelta.utils.Jsp;

import java.io.IOException;
import java.util.Optional;

import static ua.com.javarush.quest.kossatyy.questdelta.utils.ErrorMessage.*;

@WebServlet(name = "SignupServlet", value = "/signup")
public class SignupServlet extends HttpServlet {

    private final UserService userService = UserService.INSTANCE;
    private final Mapper<UserDto, User> userMapper = new UserMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Jsp.forward(req, resp, Jsp.SIGNUP);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login"); //TODO Duplicate code with LoginServlet
        boolean validLogin = userService.validateLogin(login);
        if (!validLogin) {
            req.setAttribute(Attribute.ERROR.getName(), LOGIN_NOT_VALID);
            Jsp.forward(req, resp, Jsp.SIGNUP);
        }

        String password = req.getParameter("password"); //TODO Validate in filter log/pass with AuthService?
        boolean validPass = userService.validatePassword(password);
        if (!validPass) {
            req.setAttribute(Attribute.ERROR.getName(), PASSWORD_NOT_VALID);
            Jsp.forward(req, resp, Jsp.SIGNUP);
        }

        Optional<User> user = userService.findByLogin(login);
        if (user.isPresent()) {
            req.setAttribute(Attribute.ERROR.getName(), LOGIN_ALREADY_USED);
            Jsp.forward(req, resp, Jsp.SIGNUP);
        }

        userService.createUser(login, password);
        Optional<User> userFromDB = userService.findByCredentials(login, password);
        if(userFromDB.isPresent()){
            UserDto userDto = userMapper.toDto(userFromDB.get());
            HttpSession session = req.getSession();
            session.setAttribute(Attribute.USER.getName(), userDto);
            Jsp.forward(req, resp, Jsp.MENU);
        } else {
            req.setAttribute(Attribute.ERROR.getName(), USER_NOT_CREATED);
            Jsp.forward(req, resp, Jsp.SIGNUP);
        }
    }
}