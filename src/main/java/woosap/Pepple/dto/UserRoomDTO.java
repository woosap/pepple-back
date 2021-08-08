package woosap.Pepple.dto;

import java.io.Serializable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserRoomDTO implements Serializable {

    @NotNull(message = "유저 아이디를 입력해주세요")
    private String userId;

    @NotNull(message = "방의 고유 id를 입력해주세요")
    private long roomId;
}
