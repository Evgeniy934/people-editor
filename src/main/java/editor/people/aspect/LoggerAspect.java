package editor.people.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggerAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Pointcut("within(editor.people.controller..*)" +
            " || within(editor.people.service..*)" +
            " || within(editor.people.repository..*)")
    public void appPointcut() {
    }

    @Before("appPointcut()")
    public void logBefore(JoinPoint point) {
        log.info("{} {} {}",
                point.getSignature().getDeclaringTypeName(),
                point.getSignature().getName(),
                point.getArgs());
    }
}
