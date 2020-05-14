package bookrental;

import bookrental.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.MimeTypeUtils;

import javax.persistence.*;

@Entity
@Table(name="Recommendation_table")
public class Recommendation {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private long id;
        private String bookid;
        private int revqty;

        @PostPersist
        public void onPostPersist() {

            Recommended recommended = new Recommended();
            recommended.setId(this.getId());
            recommended.setBookid(this.getBookid());
            ObjectMapper objectMapper = new ObjectMapper();
            String json = null;

            try {
                json = objectMapper.writeValueAsString(recommended);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("JSON format exception", e);
            }

            KafkaProcessor processor = Application.applicationContext.getBean(KafkaProcessor.class);
            MessageChannel outputChannel = processor.outboundTopic();

            outputChannel.send(MessageBuilder
                    .withPayload(json)
                    .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                    .build());
        }

    @PostUpdate
    public void onPostUpdate() {

        Recommended recommended = new Recommended();
        recommended.setId(this.getId());
        recommended.setBookid(this.getBookid());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(recommended);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }

        KafkaProcessor processor = Application.applicationContext.getBean(KafkaProcessor.class);
        MessageChannel outputChannel = processor.outboundTopic();

        outputChannel.send(MessageBuilder
                .withPayload(json)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
    }

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getBookid() {
            return bookid;
        }

        public void setBookid(String bookid) {
            this.bookid = bookid;
        }
        public int getRevqty() {
            return revqty;
        }

        public void setRevqty(int revqty) {
            this.revqty = revqty;
        }

}
