package token.domain

import java.util.{ArrayList, List}

import org.springframework.data.mongodb.repository.MongoRepository

trait TokenRepository extends MongoRepository[Token, String] {

  def findByUsed( used: Boolean ) : List[Token]

}