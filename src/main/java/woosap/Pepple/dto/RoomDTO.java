package woosap.Pepple.dto;

import java.io.Serializable;
import java.security.Provider.Service;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import woosap.Pepple.entity.type.Category;

@Getter
@Setter
@AllArgsConstructor
public class RoomDTO implements Serializable {

    private String title; // 방제목

    private String sub_title; // 소제목

    private Date date; // 생성 시간

    private List<Category> category; // 카테고리

    private int num_of_people; // 방 인원수

    private String boss; // 방 개설자

    Vector<Service> userV; // 같은 방에 있는 Client 정보

    public RoomDTO() {
        userV = new Vector<>();
    }
}
