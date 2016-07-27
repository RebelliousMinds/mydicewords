package token.controllers

import java.security.KeyStore
import java.security.cert.X509Certificate
import java.util.{Base64, ArrayList, List, Map}
import javax.net.ssl._

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.{HttpEntity, HttpMethod, HttpHeaders}
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam, RestController}
import org.springframework.web.client.RestTemplate
import token.domain.{Token, TokenRepository}
import token.util.{NullHostnameVerifier, TolerantHttpRequestFactory}

@RestController
class TokenServiceController  @Autowired()( tokenRepository: TokenRepository) {

  @RequestMapping(value = Array("/token"), method = Array(RequestMethod.POST))
  def create( ) : Token = {

    val newToken = new Token( id = null, token = generateToken( 4 ), used = false )
    tokenRepository.insert( newToken )
    newToken

  }

  @RequestMapping(value = Array("/token"), method = Array(RequestMethod.GET))
  def readAll(@RequestParam(value = "query", defaultValue = "") query: String) : java.util.List[Token] = {

    if( query.equals("next") ) {
      next()
    } else {
      tokenRepository.findAll()
    }
  }

  def next(): List[Token] = {

    val tokenList = new ArrayList[Token]()

    val tokensFound = tokenRepository.findByUsed( false )

    if( null != tokensFound && tokensFound.size() > 0 ) {
      val token = tokensFound.get(0)
      val savedToken = tokenRepository.save( new Token( id = token.id, token = token.token, used = true) )
      tokenList.add( savedToken )
    }

    tokenList

  }

  def generateToken( length: Int ): String = {

    var token = ""

    for( position <- 1 to length ) {
      val word = findNextWord()
      if( position == 1 ) {
        token = word
      } else {
        token = s"${token}-${word}"
      }
    }

    return token

  }


  def findNextWord(): String = {

    val wordNumber = generateWordNumber

    val plainCreds = "admin:Awesome123!"
    val base64Creds = new String( Base64.getEncoder.encode( plainCreds.getBytes() ))

    val headers = new HttpHeaders()
    headers.add("Authorization", "Basic " + base64Creds );

    new SslTruster().turnOffSslChecking()

    val restTemplate = new RestTemplate()

    restTemplate.setRequestFactory( new TolerantHttpRequestFactory( new NullHostnameVerifier() ) )

    val getWordResponse = restTemplate.exchange(s"https://localhost:8443/word?wordNumber=${wordNumber}", HttpMethod.GET, new HttpEntity[String](headers), classOf[List[Map[String, String]]])

    if( null == getWordResponse.getBody || 0 == getWordResponse.getBody.size() ) {
      return findNextWord()
    } else {
      return getWordResponse.getBody.get(0).get("word")
    }
  }

  private def generateWordNumber: String = {
    return s"${scala.util.Random.nextInt(5) + 1}${scala.util.Random.nextInt(5) + 1}${scala.util.Random.nextInt(5) + 1}${scala.util.Random.nextInt(5) + 1}${scala.util.Random.nextInt(5) + 1}"
  }


  class SslTruster() {

    val trustManagers = new Array[TrustManager](1)

    trustManagers(0) = new X509TrustManager {

      override def getAcceptedIssuers: Array[X509Certificate] = {
        null
      }

      override def checkClientTrusted(x509Certificates: Array[X509Certificate], s: String): Unit = {}
      override def checkServerTrusted(x509Certificates: Array[X509Certificate], s: String): Unit = {}

    }

    def turnOffSslChecking() = {
      val sc = SSLContext.getInstance("SSL")
      sc.init(null, trustManagers, null )
      HttpsURLConnection.setDefaultSSLSocketFactory( sc.getSocketFactory )
    }

  }

}
