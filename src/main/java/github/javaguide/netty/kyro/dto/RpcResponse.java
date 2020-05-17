package github.javaguide.netty.kyro.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author shuang.kou
 * @createTime 2020年05月13日 18:03:00
 */
@AllArgsConstructor
@Getter
@NoArgsConstructor
@Builder
@ToString
public class RpcResponse {
   private String message;
}
