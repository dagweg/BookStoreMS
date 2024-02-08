package model;


import java.time.LocalDate;



class Book{
    private String title;
    private Author author;
    private Publisher publisher;
    private LocalDate pubDate;
    private Integer id;
    private Double price;
    private Integer quantitySold, stockQuantity;
    private Genre genre;

    // Setters and Getters
    public Integer getQuantitySold() { return this.quantitySold; }
    public void setQuantitySold(Integer n) { this.quantitySold = n;}
    public Integer getStockQuantity() { return this.stockQuantity; }
    public void setStockQuantity(Integer n) { this.stockQuantity = n;}
    public String getTitle() { return this.title; }
    public void setTitle(String title) { this.title = title; }
    public Author getAuthor() { return this.author; }
    public void setAuthor(Author author) { this.author = author; }
    public Publisher getPublisher() { return this.publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }
    public LocalDate getPubDate() { return this.pubDate; }
    public void setPubDate(LocalDate pubDate) { this.pubDate = pubDate; }
    public Integer getId() { return this.id; }
    public void setId(Integer id) { this.id = id; }
    public Double getPrice() { return this.price; }
    public void setPrice(Double price) { this.price = price; }
    public Genre getGenre() { return this.genre; }
    public void setGenre(Genre genre) { this.genre = genre; }
}