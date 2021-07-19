package woosap.Pepple.dto;

import java.io.Serializable;
import java.security.Provider.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import woosap.Pepple.entity.User;
import woosap.Pepple.entity.type.Category;

@Getter
@Setter
@AllArgsConstructor
public class RoomDTO implements Serializable {

    @NotBlank(message = "페플러의 관심을 끌 수 있도록 제목을 설정해주세요!")
    private String title; // 방제목

    @NotBlank(message = "스터디에 대해 잘 알 수 있게 적어주세요!")
    private String sub_title; // 소제목

    private LocalDateTime date; // 생성 시간

    private List<Category> category; // 카테고리

    private int capacity; // 방 입장 제한인원수

    private int peoples; // 현재 인원수

    private String maker; // 방 개설자

    private List<User> userV; // 같은 방에 있는 Client 정보

}
