package libraryApp.Models;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "book")
public class Book {

    public Book() {
    }
    @DatabaseField(generatedId = true)
    private int id;
    @DatabaseField(columnName = "CATEGORY_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Category category;
    @DatabaseField(columnName = "AUTHOR_ID", foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, canBeNull = false)
    private Author author;
    @DatabaseField(columnName = "TITLE")
    private String title;
    @DatabaseField(columnName = "DESCRIPTION")
    private String description;
    @DatabaseField(columnName = "RATE", width = 1)
    private int rate;
    @DatabaseField(columnName = "ISBN")
    private String ISBN;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", category=" + category +
                ", author=" + author +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rate=" + rate +
                ", ISBN='" + ISBN + '\'' +
                '}';
    }
    public String getDesc()
    {

        return "Autor: " + this.author.getName() + " " + this.author.getSurname() + "\n" +
                "Opis: " + this.getDescription() + "\n" +
                "Ocena: " + this.getRate() + "\n" +
                "ISBN: " + this.getISBN();
    }
}
