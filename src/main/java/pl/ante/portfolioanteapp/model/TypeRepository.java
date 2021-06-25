package pl.ante.portfolioanteapp.model;

import java.util.List;
import java.util.Optional;

public interface TypeRepository {

    //GET
    Optional<Type> findById(Integer id);
    List<Type> findAll();

}
