package org.yakimovdenis.exorigo_task.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

@Data
public class RoleEntity implements Serializable, IdentifiableEntity, GrantedAuthority{
    public static final String TABLE_NAME = "roles";
    public static final String ROLE_REGEX = "\\w{3,15}";

    private Integer id;
    private String roleName;

    @Override
    public String getAuthority() {
        return roleName;
    }
}
