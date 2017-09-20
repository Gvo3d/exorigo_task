package org.yakimovdenis.exorigo_task.model;

import lombok.Data;

import java.util.Set;

@Data
public class UserEntity{
    public static final String TABLE_NAME = "users";

    private Integer id;
    private String login;
    private String password;
    private String name;
    private String surname;
    private Set<TelephoneEntity> phones;
    private RoleEntity role;
    private boolean enabled;

}
