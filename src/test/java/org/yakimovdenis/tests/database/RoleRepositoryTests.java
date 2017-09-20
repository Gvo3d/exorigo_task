package org.yakimovdenis.tests.database;

import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.yakimovdenis.exorigo_task.model.RoleEntity;
import org.yakimovdenis.exorigo_task.service.RoleService;

import java.util.List;
import java.util.function.Consumer;

@ContextConfiguration(classes = {TestDAOConfig.class})
public class RoleRepositoryTests extends AbstractDatabaseTest {
    private static final Integer FIRST_ENTITY_ID = 1;

    @Autowired
    private RoleService roleService;

    @Test
    public void test(){
        RoleEntity roleEntity = new RoleEntity();
        roleEntity.setRoleName("USER");
        System.out.println(SEPARATOR);
        System.out.println("CREATING ROLE");
        roleService.create(roleEntity);
        System.out.println(SEPARATOR);
        RoleEntity fromDb = roleService.getOne(FIRST_ENTITY_ID);
        System.out.println(fromDb);
        fromDb.setRoleName("ROLE_USER");
        roleService.update(fromDb);
        getAllRoles();
        System.out.println(SEPARATOR);
        System.out.println("ROLE WAS DELETED:");
        roleService.delete(FIRST_ENTITY_ID);
        getAllRoles();
    }

    public void getAllRoles(){
        System.out.println(SEPARATOR);
        List<RoleEntity> list = roleService.getAll(null,null,null,false);
        list.stream().forEach(new Consumer<RoleEntity>() {
            @Override
            public void accept(RoleEntity roleEntity) {
                System.out.println(roleEntity);
            }
        });
    }
}