package com.parting.dippin.entity.term.repository;

import com.parting.dippin.entity.term.Term;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Integer>, QTermRepository {

}
