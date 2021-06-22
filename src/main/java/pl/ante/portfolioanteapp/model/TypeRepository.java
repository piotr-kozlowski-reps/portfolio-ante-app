package pl.ante.portfolioanteapp.model;

import java.util.Optional;

public interface TypeRepository {

    //GET
    Optional<Type> findById(Integer id);
}
