package org.yakimovdenis.exorigo_task.support;

import org.yakimovdenis.exorigo_task.model.UserEntity;

import java.util.Comparator;

public class UserEntityComparator implements Comparator<UserEntity> {
    @Override
    public int compare(UserEntity o1, UserEntity o2) {
        return o1.getId()<o2.getId()?-1:o1.getId()>o2.getId()?1:0;
    }
}
