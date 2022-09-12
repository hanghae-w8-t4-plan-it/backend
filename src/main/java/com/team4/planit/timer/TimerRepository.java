package com.team4.planit.timer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimerRepository extends JpaRepository<Timer, Long> {
//    Timer findAll(Timer timer);
}
