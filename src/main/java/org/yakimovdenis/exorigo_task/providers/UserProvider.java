package org.yakimovdenis.exorigo_task.providers;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yakimovdenis.exorigo_task.model.RoleEntity;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.service.UserService;
import org.yakimovdenis.exorigo_task.support.UserEntityComparator;

import javax.annotation.PostConstruct;

@Component
public class UserProvider extends SortableDataProvider {
    
    @Autowired
    private UserService userService;
    private List<UserEntity> list = new ArrayList<UserEntity>();
    private UserEntityComparator comparator = new UserEntityComparator();

    public UserProvider() {
        setSort("name", SortOrder.ASCENDING);



//        RoleEntity role = new RoleEntity();
//        role.setRoleName("USER");
//        for (int i=0; i<10; i++){
//            UserEntity user = new UserEntity();
//            user.setName("name"+i);
//            user.setSurname("surname"+i);
//            user.setLogin("login"+i);
//            user.setPassword("pass"+i);
//            user.setEnabled(true);
//            user.setRole(role);
//            list.add(new UserEntity());
//        }

    }

    @PostConstruct
    private void init(){
        list.addAll(userService.getAll(null,null,null,false));
    }

    public Iterator<UserEntity> iterator(long first, long count) {
        List<UserEntity> newList = new ArrayList<UserEntity>(list);
        newList.sort(comparator);
        return newList.subList(Math.toIntExact(first), Math.toIntExact(first + count)).iterator();
    }

    public IModel<UserEntity> model(final Object object) {
        return new AbstractReadOnlyModel<UserEntity>() {
            @Override
            public UserEntity getObject() {
                return (UserEntity) object;
            }
        };
    }

    @Override
    public long size() {
        return list.size();
    }
}
