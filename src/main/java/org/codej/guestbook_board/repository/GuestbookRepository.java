package org.codej.guestbook_board.repository;

import org.codej.guestbook_board.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GuestbookRepository extends JpaRepository<Guestbook,Long> , QuerydslPredicateExecutor<Guestbook> {


}
