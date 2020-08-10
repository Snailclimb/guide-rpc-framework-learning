package github.javaguide.proxy.dynamicProxy.jdkDynamicProxy;

/**
 * @author shuang.kou
 * @createTime 2020年05月11日 11:26:00
 */
public class Main {
    public static void main(String[] args) {
        SmsService smsService = new SmsServiceImpl();
        SmsService smsServiceImplProxy = (SmsService) JdkProxyFactory.getProxy(smsService);
        smsServiceImplProxy.send("java");
    }
}
