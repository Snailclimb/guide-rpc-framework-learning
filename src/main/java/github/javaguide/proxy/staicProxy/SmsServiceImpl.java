package github.javaguide.proxy.staicProxy;


/**
 * @author shuang.kou
 * @createTime 2020年08月9日 11:21 :00
 */
public class SmsServiceImpl implements SmsService {
    public String send(String message) {
        System.out.println("send message:" + message);
        return message;
    }
}
