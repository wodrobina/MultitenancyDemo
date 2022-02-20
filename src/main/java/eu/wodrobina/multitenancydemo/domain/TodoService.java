package eu.wodrobina.multitenancydemo.domain;

import java.util.Collection;

public interface TodoService {

    Collection<Todo> findAll();

    void save(Todo todo);


}
