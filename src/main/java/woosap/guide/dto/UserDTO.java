package woosap.guide.dto;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import woosap.guide.type.Job;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    @ApiParam(value = "별명? 닉네임? 기획부분에서 사용한다고해서 넣었습니다")
    private String nickName;
    @ApiParam(value = "기본적으로 인증을 email + password로 구현을 했습니다")
    private String email;
    @ApiParam(value = "직업 - 설계에나와있는데로 대문자에 맞춰서 보내주셔야 합니다.")
    private Job job;
    private String password;
    @ApiParam(value="이미지 경로")
    private String imagePath;
    // database -> 설계 고려  1 : N
    @ApiParam(value ="SNS 리스트")
    public List<String> snsList;
    @ApiParam(value ="소셜로그인 여부")
    private boolean socialJoined;
}
