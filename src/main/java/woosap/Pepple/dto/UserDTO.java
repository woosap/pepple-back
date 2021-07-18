package woosap.Pepple.dto;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woosap.Pepple.entity.type.Job;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements Serializable {
    @NotBlank(message = "아이디를 반드시 입력해야 합니다")
    private String userId;

    @NotBlank(message = "닉네임은 필수 항목입니다")
    private String nickname;

    private String imageUrl;

    private Job job;

    private String profile;

    private List<String> snsList;

}
