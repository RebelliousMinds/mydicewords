package token.controllers

import org.junit.Test
import token.controllers.TokenServiceController

class TokenServiceControllerTest {

//  @Test
  def findNextWord : Unit = {

    val controller = new TokenServiceController( null )

    val word = controller.findNextWord

    println( word )

  }

//  @Test
  def generateToken : Unit = {

    val controller = new TokenServiceController( null )

    val token = controller.generateToken( 4 )

    println( token )

  }
}
