package backend.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import backend.model.NotificationModel;

@Repository
public interface NotificationRepository extends MongoRepository<NotificationModel, String> {
    List<NotificationModel> findByUserId(String userId);
}
