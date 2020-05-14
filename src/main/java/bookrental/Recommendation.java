package bookrental;

import javax.persistence.*;

@Entity
@Table(name="Recommendation_table")
public class Recommendation {

        @Id
        @GeneratedValue(strategy=GenerationType.AUTO)
        private long id;
        private String bookid;
        private int revqty;

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
