package github.javaguide.jdkDynamicProxy;

/**
 * @author shuang.kou
 * @createTime 2020年05月11日 11:21:00
 */
public class SmsServiceImpl implements SmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
