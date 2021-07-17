package woosap.Pepple.service;

import org.springframework.stereotype.Service;
import woosap.Pepple.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public Boolean titleDuplicateCheck(String title) {
        return roomRepository.existsByTitle(title);
    }
}
