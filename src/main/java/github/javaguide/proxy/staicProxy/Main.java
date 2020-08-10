package github.javaguide.proxy.staicProxy;

/**
 * @author shuang.kou
 * @createTime 2020年08月10日 10:25:00
 **/
public class Main {
    public static void main(String[] args) {
        SmsService smsService = new SmsServiceImpl();
        SmsProxy smsProxy = new SmsProxy(smsService);
        smsProxy.send("java");
    }
}
