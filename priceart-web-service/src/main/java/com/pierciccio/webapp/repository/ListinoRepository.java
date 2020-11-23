package com.pierciccio.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pierciccio.webapp.entity.Listini;

public interface ListinoRepository  extends JpaRepository<Listini, String>
{
}
