package github.javaguide.socket;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author shuang.kou
 * @createTime 2020年05月11日 17:02:00
 */
@Data
@AllArgsConstructor
public class Message implements Serializable {

    private String content;
}
