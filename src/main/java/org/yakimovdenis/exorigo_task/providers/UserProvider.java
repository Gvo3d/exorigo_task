package org.yakimovdenis.exorigo_task.providers;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import lombok.Data;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortParam;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.yakimovdenis.exorigo_task.model.RoleEntity;
import org.yakimovdenis.exorigo_task.model.UserEntity;
import org.yakimovdenis.exorigo_task.service.UserService;
import org.yakimovdenis.exorigo_task.support.BooleanComparator;

public class UserProvider extends SortableDataProvider {
    private UserService userService;
    private List<UserEntity> list = new ArrayList<UserEntity>();

    public UserProvider(UserService userService) {
        this.userService = userService;
        setSort("name", SortOrder.ASCENDING);
        list.addAll(userService.getAll(null, null, null, false));
    }

    public Iterator<UserEntity> iterator(long first, long count) {
        List<UserEntity> newList = new ArrayList<UserEntity>(list);
        newList.sort(new Comparator<UserEntity>() {
            public int compare(UserEntity o1, UserEntity o2) {
                int dir = getSort().isAscending() ? 1 : -1;
                if ("name".equals(getSort().getProperty())) {
                    return dir * (o1.getName().compareTo(o2.getName()));
                } else if ("surname".equals(getSort().getProperty())) {
                    return dir * (o1.getSurname().compareTo(o2.getSurname()));
                } else if ("login".equals(getSort().getProperty())) {
                    return dir * (o1.getLogin().compareTo(o2.getLogin()));
                } else if ("role.roleName".equals(getSort().getProperty())) {
                    return dir * (o1.getRole().getRoleName().compareTo(o2.getRole().getRoleName()));
                } else if ("password".equals(getSort().getProperty())) {
                    return 1;
                } else {
                    return dir * (new BooleanComparator(dir).compare(o1.isEnabled(), o2.isEnabled()));
                }
            }
        });
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
