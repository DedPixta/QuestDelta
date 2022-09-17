package ua.com.javarush.quest.ryabov.questdelta.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long id;

    private String login;

    private String password;

    private Role role;

}
