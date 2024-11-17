package com.parting.dippin.entity.term.repository;

import com.parting.dippin.entity.term.TermEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<TermEntity, Integer>, QTermRepository {

}
