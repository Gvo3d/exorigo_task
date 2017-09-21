package org.yakimovdenis.exorigo_task.support;

import org.yakimovdenis.exorigo_task.model.UserEntity;

import java.io.Serializable;
import java.util.Comparator;

public class UserEntityComparator implements Comparator<UserEntity>, Serializable {
    @Override
    public int compare(UserEntity o1, UserEntity o2) {
        if (null==o1 || null==o2) {
            return 0;
        }
        return o1.getId()<o2.getId()?-1:o1.getId()>o2.getId()?1:0;
    }
}
