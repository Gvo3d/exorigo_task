package org.yakimovdenis.tests.database;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.yakimovdenis.exorigo_task.model.RoleEntity;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.service.RoleService;
import org.yakimovdenis.exorigo_task.service.UserService;

import java.util.List;
import java.util.function.Consumer;

@ContextConfiguration(classes = {TestDAOConfig.class})
public class UserRepositoryTests extends AbstractDatabaseTest {
    private static final Integer FIRST_ENTITY_ID = 1;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Test
    public void test(){
        UserEntity userEntity = new UserEntity();
        userEntity.setName("name");
        userEntity.setSurname("surname");
        userEntity.setLogin("login");
        userEntity.setPassword("password");
        userEntity.setEnabled(true);
        RoleEntity role = new RoleEntity();
        role.setRoleName("TEMP_ROLE");
        roleService.create(role);
        role = roleService.getAll("rolename","TEMP_ROLE",null,false).get(0);
        userEntity.setRole(role);
        System.out.println(SEPARATOR);
        System.out.println("CREATING ROLE");
        userService.create(userEntity);
        System.out.println(SEPARATOR);
        UserEntity fromDb = userService.getOne(FIRST_ENTITY_ID);
        System.out.println(fromDb);
        fromDb.setName("NEW NAME");
        userService.update(fromDb);
        getAllRoles();
        System.out.println(SEPARATOR);
        System.out.println("ROLE WAS DELETED:");
        userService.delete(FIRST_ENTITY_ID);
        getAllRoles();
    }

    public void getAllRoles(){
        System.out.println(SEPARATOR);
        List<UserEntity> list = userService.getAll(null,null,null,false);
        list.stream().forEach(new Consumer<UserEntity>() {
            @Override
            public void accept(UserEntity userEntity) {
                System.out.println(userEntity);
            }
        });
    }
}