package org.yakimovdenis.exorigo_task.providers;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.yakimovdenis.exorigo_task.components.BooleanComparator;
import org.yakimovdenis.exorigo_task.model.RoleEntity;
import org.yakimovdenis.exorigo_task.service.RoleService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class RoleProvider extends SortableDataProvider {
    private RoleService roleService;
    private List<RoleEntity> list = new ArrayList<RoleEntity>();

    public RoleProvider(RoleService roleService) {
        this.roleService = roleService;
        setSort("id", SortOrder.ASCENDING);
        list.addAll(roleService.getAll(null, null, null, false));
    }

    public Iterator<RoleEntity> iterator(long first, long count) {
        List<RoleEntity> newList = new ArrayList<RoleEntity>(list);
        newList.sort((o1, o2) -> {
            int dir = getSort().isAscending() ? 1 : -1;
            return dir * (o1.getRoleName().compareTo(o2.getRoleName()));
        });
        return newList.subList(Math.toIntExact(first), Math.toIntExact(first + count)).iterator();
    }

    public IModel<RoleEntity> model(final Object object) {
        return new AbstractReadOnlyModel<RoleEntity>() {
            @Override
            public RoleEntity getObject() {
                return (RoleEntity) object;
            }
        };
    }

    @Override
    public long size() {
        return list.size();
    }
}
