package org.yakimovdenis.exorigo_task.service;

import java.io.Serializable;
import java.util.List;

public interface EntityCRUDService<T, ID extends Serializable> {
    T getOne(ID id);
    List<T> getAll(String searcheableParameter, String searcheableValue, String orderingParameter, boolean isAscend);
    boolean exists(ID id);
    boolean delete(ID id);
    boolean update(T object);
}
