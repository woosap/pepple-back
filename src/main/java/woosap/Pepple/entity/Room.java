package woosap.Pepple.entity;

import java.security.Provider.Service;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import woosap.Pepple.dto.RoomDTO;
import woosap.Pepple.entity.type.Category;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roomId;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "sub_title")
    private String subTitle;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime date; // 생성 시간 : 년월일 시분초

    @ElementCollection
    @CollectionTable(name = "category", joinColumns = @JoinColumn(name = "category_id"))
    private List<Category> category; // 카테고리

    @Column(name = "capacity")
    private int capacity; // 방 입장 제한인원수

    @Column(name = "creator")
    private String creator; // 방 개설자

    public RoomDTO entityToDto(Room room) {
        return RoomDTO.builder()
            .roomId(room.getRoomId())
            .capacity(room.getCapacity())
            .category(room.getCategory())
            .date(room.getDate())
            .creator(room.getCreator())
            .sub_title(room.getSubTitle())
            .title(room.getTitle())
            .build();
    }
}
