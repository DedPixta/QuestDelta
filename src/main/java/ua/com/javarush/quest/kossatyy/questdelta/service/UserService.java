package ua.com.javarush.quest.kossatyy.questdelta.service;

import ua.com.javarush.quest.kossatyy.questdelta.dto.UserDto;
import ua.com.javarush.quest.kossatyy.questdelta.entity.Role;
import ua.com.javarush.quest.kossatyy.questdelta.entity.User;
import ua.com.javarush.quest.kossatyy.questdelta.mapper.Mapper;
import ua.com.javarush.quest.kossatyy.questdelta.mapper.UserMapper;
import ua.com.javarush.quest.kossatyy.questdelta.repository.Repository;
import ua.com.javarush.quest.kossatyy.questdelta.repository.UserRepository;

import java.util.Collection;
import java.util.Comparator;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.nonNull;

public enum UserService {
    INSTANCE;

    private final Repository<User> userRepository = UserRepository.getInstance();
    private final Mapper<UserDto, User> userMapper = new UserMapper();

    public Collection<UserDto> getAll(int pageNumber, int pageSize) {
        return userRepository.getAll().stream()
                .sorted(Comparator.comparing(User::getLogin))
                .skip((long) pageNumber * pageSize)
                .limit(pageSize)
                .map(userMapper::toDto)
                .toList();
    }

    public Integer getAllCount() {
        return userRepository.getAll().size();
    }

    public Optional<UserDto> findByCredentials(String login, String password) {
        return userRepository.find(User.builder()
                        .login(login)
                        .password(password)
                        .build())
                .findFirst()
                .map(userMapper::toDto);
    }

    public Optional<UserDto> findByLogin(String login) {
        return userRepository.find(User.builder()
                        .login(login)
                        .build())
                .findFirst()
                .map(userMapper::toDto);
    }

    public Optional<UserDto> findById(long id) {
        return Optional.of(userRepository.getById(id))
                .map(userMapper::toDto);
    }

    public boolean validateLogin(String login) {
        String regex = "^[A-Za-z\\d]{1,20}$";
        return check(regex, login);
    }

    public boolean validatePassword(String password) {
        String regex = "^[A-Za-z\\d]{1,8}$";
        return check(regex, password);
    }

    public void createUser(String login, String password, Role role) {
        userRepository.create(User.builder()
                .login(login)
                .password(password)
                .role(role)
                .build());
    }

    public void update(Long id, String login, String password, Role role) {
        User user = userRepository.getById(id);

        if(nonNull(login) && !login.isBlank()) {
            user.setLogin(login);
        }

        if(nonNull(password) && !password.isBlank()) {
            user.setPassword(password);
        }

        user.setRole(role);

        userRepository.update(user);
    }

    public void deleteById(long id) {
        userRepository.deleteById(id);
    }

    private boolean check(String regex, String input) {
        if (input == null) {
            return false;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.find();
    }

    public Role getRole(String parameter) {
        Role userRole;
        try {
            userRole = Role.valueOf(parameter);
        } catch (IllegalArgumentException e) {
            userRole = Role.GUEST;
        }
        return userRole;
    }
}