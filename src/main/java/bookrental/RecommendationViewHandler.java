package bookrental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecommendationViewHandler {


    @Autowired
    private RecommendationRepository recommendationRepository;

/*    @StreamListener(KafkaProcessor.INPUT)
    public void whenReserved_then_CREATE_1 (@Payload Incomed incomed) {
        try {
            if (incomed.isMe()) {
                // view 객체 생성
                Recommendation recommendation = new Recommendation();
                recommendation.setBookid(incomed.getBookid());
                // view 객체에 이벤트의 Value 를 set 함
                // view 레파지 토리에 save
                recommendationRepository.save(recommendation);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/



}