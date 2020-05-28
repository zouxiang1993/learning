package ch01.introduction;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: No Description
 *
 * @author zouxiang
 * @date 2020/5/27
 */
public class HelloWorld2 {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(HelloWorld2.class);
        logger.debug("Hello world");

        /*
        打印logback自身的内部状态，LoggerContext对于查找logback相关的问题非常有用。
         */
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        StatusPrinter.print(lc);
    }
}
