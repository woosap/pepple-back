package woosap.Pepple.dto;

import java.io.Serializable;
import java.security.Provider.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woosap.Pepple.entity.User;
import woosap.Pepple.entity.type.Category;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoomDTO implements Serializable {

    private long roomId;

    @NotBlank(message = "페플러의 관심을 끌 수 있도록 제목을 설정해주세요!")
    private String title; // 방제목

    @NotBlank(message = "스터디에 대해 잘 알 수 있게 적어주세요!")
    private String sub_title; // 소제목

    private LocalDateTime date; // 생성 시간

    @NotNull(message = "카테고리를 입력해주세요")
    private List<Category> category; // 카테고리

    @Size(min = 1, max = 6, message = "인원수는 1명에서 6명 사이입니다")
    private int capacity; // 방 입장 제한인원수

    @NotBlank(message = "방 개설자를 입력 해주세요")
    private String creator; // 방 개설자
}
