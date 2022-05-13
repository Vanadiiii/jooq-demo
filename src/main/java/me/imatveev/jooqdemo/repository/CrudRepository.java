package me.imatveev.jooqdemo.repository;

import org.jooq.Condition;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, I> {
    Integer SUCCESS_CODE = 1;

    T insert(T t);

    T update(T t);

    Optional<T> find(I id);

    List<T> findAll(Condition condition);

    Boolean delete(I id);
}
