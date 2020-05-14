package bookrental;

import bookrental.config.kafka.KafkaProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class PolicyHandler{

    @Autowired
    RecommendationRepository recommendationRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverIncomed_Revhistory(@Payload Incomed incomed){

        if(incomed.isMe()){
            System.out.println("##### listener Revhistory : " + incomed.toJson());
            Recommendation reservation = new Recommendation();
            System.out.println("incomed.getBookid() = " + incomed.getBookid());
            reservation.setBookid(incomed.getBookid());
            System.out.println("===============================");
            reservation.setRevqty(0);
            recommendationRepository.save(reservation);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRevsuccessed_Revhistory(@Payload Revsuccessed revsuccessed){

        boolean present = recommendationRepository.findBybookid(revsuccessed.getBookid()).isPresent();

        if(revsuccessed.isMe() && present){
            System.out.println("##### listener Revhistory : " + revsuccessed.toJson());
            recommendationRepository.findBybookid(revsuccessed.getBookid())
                    .ifPresent(
                            reservation -> {
                                int qty = reservation.getRevqty();
                                reservation.setRevqty(qty+1);
                                recommendationRepository.save(reservation);
                            }
                    )
            ;
        } else if(revsuccessed.isMe() && !present) {
            System.out.println("##### listener newRevhistory : " + revsuccessed.toJson());
            Recommendation reservation = new Recommendation();
            System.out.println("revsuccessed.getBookid() = " + revsuccessed.getBookid());
            reservation.setBookid(revsuccessed.getBookid());
            System.out.println("===============================");
            reservation.setRevqty(1);
            recommendationRepository.save(reservation);
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRevcanceled_Revhistory(@Payload Revcanceled revcanceled){

        boolean present = recommendationRepository.findBybookid(revcanceled.getBookid()).isPresent();

        if(revcanceled.isMe() && present){
            System.out.println("##### listener Revhistory : " + revcanceled.toJson());
            recommendationRepository.findBybookid(revcanceled.getBookid())
                    .ifPresent(
                            reservation -> {
                                int qty = reservation.getRevqty();
                                reservation.setRevqty(qty-1);
                                recommendationRepository.save(reservation);
                            }
                    );
            ;
        } else if(revcanceled.isMe() && !present)  {
            System.out.println("##### listener newRevhistory : " + revcanceled.toJson());
            Recommendation reservation = new Recommendation();
            System.out.println("revsuccessed.getBookid() = " + revcanceled.getBookid());
            reservation.setBookid(revcanceled.getBookid());
            System.out.println("===============================");
            reservation.setRevqty(0);
            recommendationRepository.save(reservation);
        }
    }

}
