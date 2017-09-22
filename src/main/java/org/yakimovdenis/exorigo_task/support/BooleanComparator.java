package org.yakimovdenis.exorigo_task.support;

import java.util.Comparator;

public class BooleanComparator implements Comparator<Boolean> {
    private int dir;

    public BooleanComparator(int dir) {
        this.dir = dir;
    }

    @Override
    public int compare(Boolean o1, Boolean o2) {
        int result = 0;
            if (o1.equals(Boolean.TRUE) && o2.equals(Boolean.FALSE)){
                result = 1;
            } else if (o1.equals(Boolean.FALSE) && o2.equals(Boolean.TRUE)){
                result = -1;
            }
        return dir==1?result:result*-1;
    }
}
