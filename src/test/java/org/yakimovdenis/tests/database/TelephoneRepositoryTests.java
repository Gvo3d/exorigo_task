package org.yakimovdenis.tests.database;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.yakimovdenis.exorigo_task.model.TelephoneEntity;
import org.yakimovdenis.exorigo_task.service.TelephoneService;

import java.util.List;
import java.util.function.Consumer;

@ContextConfiguration(classes = {TestDAOConfig.class})
public class TelephoneRepositoryTests extends AbstractDatabaseTest {
    private static final Integer FIRST_ENTITY_ID = 1;

    @Autowired
    private TelephoneService telephoneService;

    @Test
    public void test(){
        TelephoneEntity telephoneEntity = new TelephoneEntity();
        telephoneEntity.setPhoneNumber("123456");
        System.out.println(SEPARATOR);
        System.out.println("CREATING PHONE");
        telephoneService.create(telephoneEntity);
        System.out.println(SEPARATOR);
        TelephoneEntity fromDb = telephoneService.getOne(FIRST_ENTITY_ID);
        System.out.println(fromDb);
        fromDb.setPhoneNumber("654321");
        telephoneService.update(fromDb);
        getAllRoles();
        System.out.println(SEPARATOR);
        System.out.println("PHONE WAS DELETED:");
        telephoneService.delete(FIRST_ENTITY_ID);
        getAllRoles();
    }
    
    public void getAllRoles(){
        System.out.println(SEPARATOR);
        List<TelephoneEntity> list = telephoneService.getAll(null,null,null,false);
        list.stream().forEach(new Consumer<TelephoneEntity>() {
            @Override
            public void accept(TelephoneEntity telephoneEntity) {
                System.out.println(telephoneEntity);
            }
        });
    }
}