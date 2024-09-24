package egovframework.example.cmmn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import egovframework.rte.fdl.cryptography.EgovEnvCryptoService;
import egovframework.rte.fdl.cryptography.impl.EgovEnvCryptoServiceImpl;

public class EgovEnvCryptoUserTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EgovEnvCryptoUserTest.class);
	
    @SuppressWarnings("resource")
    public static void main(String[] args) {
		
        //해당부분 작성해야한다.
        String[] arrCryptoString = {
            "c##shin", // 데이터베이스 접속 계정 설정
            "java", // 데이터베이스 접속 패드워드 설정
            "jdbc:oracle:thin:@127.0.0.1:1521:oracle", // 데이터베이스 접속 주소 설정
            "oracle.jdbc.driver.OracleDriver" // 데이터베이스 드라이버
        };

        LOGGER.info("------------------------------------------------------");
        ApplicationContext context = new ClassPathXmlApplicationContext(
                new String[] { "classpath:/egovframework/spring/context-crypto.xml" });
        EgovEnvCryptoService cryptoService = context.getBean(EgovEnvCryptoServiceImpl.class);
        LOGGER.info("------------------------------------------------------");

        String label = "";
        try {
            for (int i = 0; i < arrCryptoString.length; i++) {
                if (i == 0)
                    label = "c##shin";
                if (i == 1)
                    label = "java";
                if (i == 2)
                    label = "jdbc:oracle:thin:@127.0.0.1:1521:oracle";
                if (i == 3)
                    label = "oracle.jdbc.driver.OracleDriver";
                LOGGER.info(label + " 원본(orignal):" + arrCryptoString[i]);
                LOGGER.info(label + " 인코딩(encrypted):" + cryptoService.encrypt(arrCryptoString[i]));
                LOGGER.info(label + " 디코딩(encrypted):" + cryptoService.decrypt(arrCryptoString[i]));
                LOGGER.info("------------------------------------------------------");
            }
        } catch (IllegalArgumentException e) {
            LOGGER.error("[" + e.getClass() + "] IllegalArgumentException : " + e.getMessage());
        } catch (Exception e) {
            LOGGER.error("[" + e.getClass() + "] Exception : " + e.getMessage());
        }
    }

}
