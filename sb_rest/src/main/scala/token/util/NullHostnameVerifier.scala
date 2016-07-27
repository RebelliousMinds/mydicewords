package token.util

import javax.net.ssl.{SSLSession, HostnameVerifier}

class NullHostnameVerifier extends HostnameVerifier {
  override def verify(s: String, sslSession: SSLSession): Boolean = {
    true
  }
}
