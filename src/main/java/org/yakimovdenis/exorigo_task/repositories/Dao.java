package org.yakimovdenis.exorigo_task.repositories;

import java.io.Serializable;
import java.util.List;

public interface Dao<T, ID extends Serializable> {
    T getEntity(ID id, String tableName);
    boolean exists(ID id, String tableName);
    List<T> getAllEntities(String searcheableParameter, String searcheableValue, String orderingParameter, boolean isAscend, String tableName);
    boolean delete(ID id, String tableName);
    boolean update(T object);
}
