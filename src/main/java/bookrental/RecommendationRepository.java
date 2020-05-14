package bookrental;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecommendationRepository extends CrudRepository<Recommendation, Long> {
    Optional<Recommendation> findBybookid(String BookId);

}