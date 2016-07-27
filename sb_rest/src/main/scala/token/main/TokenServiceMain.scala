import org.springframework.boot.SpringApplication
import token.TokenServiceMapperConfiguration

object TokenServiceMain extends App {
  SpringApplication.run(classOf[TokenServiceMapperConfiguration])
}
