package org.yakimovdenis.exorigo_task.model;

import lombok.Data;

@Data
public class RoleEntity {
    public static final String TABLE_NAME = "roles";

    private Integer id;
    private String roleName;
}
