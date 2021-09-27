package woosap.Pepple.dto.agora;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AgoraTokenDTO implements Serializable {
    String channelName;
    String userAccount;
}
