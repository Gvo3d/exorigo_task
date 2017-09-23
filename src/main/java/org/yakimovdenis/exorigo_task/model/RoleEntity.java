package org.yakimovdenis.exorigo_task.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleEntity implements Serializable, IdentifiableEntity{
    public static final String TABLE_NAME = "roles";
    public static final String ROLE_REGEX = "\\w{3,15}";

    private Integer id;
    private String roleName;
}
