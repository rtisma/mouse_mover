package com.roberttisma.tools.mouse_mover.repository;

import java.util.List;

public interface Repository<ID, T> {

  List<ID> listIds();

  List<T> findAll();

  T findOne(ID id);

  void save(T object);

  T delete(ID id);

}
