package com.example.wguc196stephenw.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "terms")
public class TermEntity {
    @PrimaryKey(autoGenerate = true)
    private int term_id;
    private String term_title;
    private String term_start_date;
    private String term_end_date;
    public TermEntity(String term_title, String term_start_date, String term_end_date) {
        this.term_title = term_title;
        this.term_start_date = term_start_date;
        this.term_end_date = term_end_date;
    }
    public void setTerm_id(int term_id) {
        this.term_id = term_id;
    }
    public void setTerm_title(String term_title) {
        this.term_title = term_title;
    }
    public void setTerm_start_date(String term_start_date) {
        this.term_start_date = term_start_date;
    }
    public void setTerm_end_date(String term_end_date) {
        this.term_end_date = term_end_date;
    }
    public int getTerm_id() {
        return term_id;
    }
    public String getTerm_title() {
        return term_title;
    }
    public String getTerm_start_date() {
        return term_start_date;
    }
    public String getTerm_end_date() {
        return term_end_date;
    }
    @Override
    public String toString() {
        return getTerm_title();
    }
}
