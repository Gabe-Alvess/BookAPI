package be.intecbrussel.bookapi.model.dto;

public class BBResponse {
    private long bbId;
    private String imgURL;
    private String title;
    private String borrowDate;
    private String dueDate;
    private boolean renewed;

    public BBResponse(long bbId, String imgURL, String title, String borrowDate, String dueDate, boolean renewed) {
        this.bbId = bbId;
        this.imgURL = imgURL;
        this.title = title;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.renewed = renewed;
    }


    public long getBbId() {
        return bbId;
    }

    public void setBbId(long bbId) {
        this.bbId = bbId;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isRenewed() {
        return renewed;
    }

    public void setRenewed(boolean renewed) {
        this.renewed = renewed;
    }
}
