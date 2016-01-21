package com.fantasyworks.fangraphsparser.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.fantasyworks.fangraphsparser.entity.Player;

public interface PlayerRepo extends CrudRepository<Player, Long> {
	
	public Optional<Player> findPlayerByUid(String uid);
	
}
