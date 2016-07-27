package token

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Primary, Bean}
import token.domain.ScalaObjectMapper


@SpringBootApplication
class TokenServiceMapperConfiguration {

  @Bean
  @Primary
  def scalaObjectMapper() = new ScalaObjectMapper

}
