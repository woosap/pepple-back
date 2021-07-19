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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woosap.Pepple.entity.type.Category;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Room {

    @Id
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    @Column(name = "sub_title")
    private String sub_title;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime date; // 생성 시간 : 년월일 시분초

    @ElementCollection
    @CollectionTable(name = "category", joinColumns = @JoinColumn(name = "select_sategory"))
    private List<Category> category; // 카테고리

    @Column(name = "capacity")
    private int capacity; // 방 입장 제한인원수

    @Column(name = "peoples")
    private int peoples; // 현재 인원수

    @Column(name = "maker")
    private String maker; // 방 개설자

    @ElementCollection
    @CollectionTable(name = "room_user", joinColumns = @JoinColumn(name = "clinets_info"))
    private List<User> userV; // 같은 방에 있는 Client 정보

}
