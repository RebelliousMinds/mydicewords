package token.domain

import org.springframework.data.mongodb.repository.MongoRepository
import java.util.List

trait WordRepository extends MongoRepository[Word, String] {
  def findByWordNumber( wordNumber: String ) : List[Word]
}