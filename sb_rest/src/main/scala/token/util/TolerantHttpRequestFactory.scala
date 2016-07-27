package token.util

import java.net.HttpURLConnection
import javax.net.ssl.{HttpsURLConnection, HostnameVerifier}

import org.springframework.http.client.SimpleClientHttpRequestFactory

class TolerantHttpRequestFactory ( verifier: HostnameVerifier ) extends SimpleClientHttpRequestFactory {
  override def prepareConnection(connection: HttpURLConnection, httpMethod: String): Unit = {
    if( connection.isInstanceOf[HttpsURLConnection]) {
      connection.asInstanceOf[HttpsURLConnection].setHostnameVerifier( verifier )
    }

    super.prepareConnection(connection, httpMethod)

  }
}
