package pl.ante.portfolioanteapp.model.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.ante.portfolioanteapp.model.Type;
import pl.ante.portfolioanteapp.model.TypeRepository;

import java.util.List;

@Repository
interface SqlTypeRepository extends TypeRepository, JpaRepository<Type, Integer> {
}
