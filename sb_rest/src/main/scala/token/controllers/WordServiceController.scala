package token.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DuplicateKeyException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._
import token.domain.{CreateWordRequest, Word, WordRepository}

@RestController
class WordServiceController @Autowired()( wordRepository: WordRepository) {

  @RequestMapping(value = Array("/word"), method = Array(RequestMethod.POST))
  def create(  @RequestBody request: CreateWordRequest ) : Word = {
    val newWord = new Word( id = null, wordNumber = request.wordNumber, word = request.word )

    wordRepository.insert( newWord )
    newWord

  }

  @RequestMapping(value = Array("/word"), method = Array(RequestMethod.GET))
  def readAll(@RequestParam(value = "wordNumber", defaultValue = "") wordNumber: String) : java.util.List[Word] = {
    if( wordNumber.equals("") ) {
      wordRepository.findAll()
    } else {
      wordRepository.findByWordNumber( wordNumber )
    }
  }

  @RequestMapping(value = Array("/word/{id}"), method = Array(RequestMethod.GET))
  def read( @PathVariable("id") id: String ) : Word = {
    wordRepository.findOne( id )
  }

  @RequestMapping(value = Array("/word/{id}"), method = Array(RequestMethod.DELETE))
  def delete( @PathVariable("id") id: String ) = {
    wordRepository.delete( id )
  }

  @ResponseStatus(value = HttpStatus.CONFLICT, reason = "duplicate field" )
  @ExceptionHandler(Array(classOf[DuplicateKeyException]))
  def duplicateField() {}

}
