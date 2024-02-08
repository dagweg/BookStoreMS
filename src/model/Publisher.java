package model;

class Publisher{
    private String name;
    private String email;
    private Integer id;
    private Integer booksPublished;

    public String getPubName() { return this.name; }
    public void setPubName(String pubName) { this.name = pubName; }
    public String getPubEmail() { return this.email; }
    public void setPubEmail(String pubEmail) { this.email = pubEmail; }
    public Integer getPubID() { return this.id; }
    public void setPubID(Integer pubID) { this.id = pubID; }
    public Integer getBooksPublished() { return this.booksPublished; }
    public void setBooksPublished(Integer booksPublished) { this.booksPublished = booksPublished; }

}