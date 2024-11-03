package org.salmane.matchservice.dao;

import org.salmane.matchservice.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchDAO extends MongoRepository<Match, String> {

}
