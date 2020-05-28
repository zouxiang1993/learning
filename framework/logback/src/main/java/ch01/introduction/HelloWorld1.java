package ch01.introduction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/27
 */
public class HelloWorld1 {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(HelloWorld1.class);
        logger.debug("hello world");
    }
}
