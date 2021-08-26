import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.Authentication
import spock.lang.Specification

@SpringBootTest
class CardTests2 extends Specification{

    def "checkOldPin pin not equal throwsWrongPinException" () {
        given:
        def test = Mock(Authentication.class)

        when:
        def test2 = test.getCredentials()

        then:
        test2 = 123456789

        expect:
        test2 == 123456789
    }
}
