package org.yakimovdenis.exorigo_task.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class UserEntity implements Serializable{
    public static final String TABLE_NAME = "users";
    public static final String NAME_REGEX = "\\w{3,50}";
    public static final String SURNAME_REGEX = "\\w{3,50}";
    public static final String LOGIN_REGEX = "\\w{6,20}";
    public static final String PASSWORD_REGEX = ".{6,20}";

    private Integer id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private Set<TelephoneEntity> phones;
    private RoleEntity role;
    private boolean enabled;

}
