package cache;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringContextHolder implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        assertApplicationContext();
        return applicationContext;
    }

    public static <T>T getBean(String beanName){
        assertApplicationContext();
        return (T)applicationContext.getBean(beanName);
    }

    public static <T>T getBean(Class<T> requiredType){
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    private static void assertApplicationContext(){
        if(null == SpringContextHolder.applicationContext){
            throw new RuntimeException("applicationContext属性为null，请检查是否注入成功！");
        }
    }
}
