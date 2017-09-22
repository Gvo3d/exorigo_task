package org.yakimovdenis.exorigo_task.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleEntity implements Serializable{
    public static final String TABLE_NAME = "roles";

    private Integer id;
    private String roleName;
}
