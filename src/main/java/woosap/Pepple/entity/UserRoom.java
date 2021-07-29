package woosap.Pepple.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userRoomId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private long RoomId;

}