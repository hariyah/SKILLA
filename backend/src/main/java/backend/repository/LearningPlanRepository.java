package backend.repository;

import backend.model.LearningPlanModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LearningPlanRepository extends MongoRepository<LearningPlanModel,String> {
}
