package bookrental;

public class Recommended extends AbstractEvent {

    private Long id;
    private String bookid;
    private int revqty;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
