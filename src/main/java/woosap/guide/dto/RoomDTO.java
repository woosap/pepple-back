package woosap.guide.dto;

import io.swagger.annotations.ApiParam;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woosap.guide.type.Category;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO {
    @ApiParam(value = "primary key로 사용할 고유 아이디 -> 백엔드에서 처리 예정")
    private Long id;
    @ApiParam(value = "제목")
    private String title;
    @ApiParam(value = "소제목")
    private String subheading;
    @ApiParam(value = "수용인원")
    private int capacity;
    // 1: n
    @ApiParam(value = "카테고리 -> 목록중 선택하는 것으로, 대문자 형식으로 맞추어 주셔야 함, 불편할시 개선할 수 있음")
    private Category[] category;
    // 현재 수용인원은 entity에 추가하는거로?
    private LocalDateTime createdAt;

}
