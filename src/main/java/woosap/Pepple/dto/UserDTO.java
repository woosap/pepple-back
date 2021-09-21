package woosap.Pepple.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woosap.Pepple.entity.User;
import woosap.Pepple.entity.type.Job;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    @NotNull(message = "아이디를 입력해주세요")
    private String userId;

    @NotBlank(message = "닉네임을 입력 해주세요")
    private String nickname;

    private String imageUrl;

    private Job job;

    private String profile;

    private List<String> snsList;

}
