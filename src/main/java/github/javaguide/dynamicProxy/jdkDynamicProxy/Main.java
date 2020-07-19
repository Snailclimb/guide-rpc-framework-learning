package github.javaguide.dynamicProxy.jdkDynamicProxy;

/**
 * @author shuang.kou
 * @createTime 2020年05月11日 11:26:00
 */
public class Main {
    public static void main(String[] args) {
        DebugProxy debugProxy = new DebugProxy(new SmsServiceImpl());
        SmsService smsService = debugProxy.getProxy(SmsService.class);
        smsService.send("java");
    }
}
