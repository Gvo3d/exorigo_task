package org.yakimovdenis.exorigo_task.providers;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.yakimovdenis.exorigo_task.model.TelephoneEntity;
import org.yakimovdenis.exorigo_task.service.TelephoneService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class TelephoneProvider extends SortableDataProvider {
    private TelephoneService telephoneService;
    private List<TelephoneEntity> list = new ArrayList<TelephoneEntity>();

    public TelephoneProvider(TelephoneService telephoneService) {
        this.telephoneService = telephoneService;
        setSort("id", SortOrder.ASCENDING);
        list.addAll(telephoneService.getAll(null, null, null, false));
    }

    public void populateTelephoneList(List<TelephoneEntity> telephones){
        list = telephones;
    }

    public Iterator<TelephoneEntity> iterator(long first, long count) {
        List<TelephoneEntity> newList = new ArrayList<TelephoneEntity>(list);
        newList.sort((o1, o2) -> {
            int dir = getSort().isAscending() ? 1 : -1;
            return dir * (o1.getPhoneNumber().compareTo(o2.getPhoneNumber()));
        });
        return newList.subList(Math.toIntExact(first), Math.toIntExact(first + count)).iterator();
    }

    public IModel<TelephoneEntity> model(final Object object) {
        return new AbstractReadOnlyModel<TelephoneEntity>() {
            @Override
            public TelephoneEntity getObject() {
                return (TelephoneEntity) object;
            }
        };
    }

    @Override
    public long size() {
        return list.size();
    }
}
