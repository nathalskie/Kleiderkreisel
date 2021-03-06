/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.kleiderkreisel.jpa;

import java.io.Serializable;
import java.sql.Date;
//import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Eine zu erledigende Aufgabe.
 */
@Entity
public class Verkaufsanzeige implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "anzeige_ids")
    @TableGenerator(name = "anzeige_ids", initialValue = 0, allocationSize = 5000)
    private long id;

    @ManyToOne
    @NotNull(message = "Die Anzeige muss einem Benutzer zugeordnet werden.")
    private User owner;

    @ManyToOne
    private Category category;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private AnzeigeArt anzeigeArt;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private PreisArt preisArt;

    @Column(length = 50)
    @NotNull(message = "Die Bezeichnung darf nicht leer sein.")
    @Size(min = 1, max = 50, message = "Die Bezeichnung muss zwischen ein und 50 Zeichen lang sein.")
    private String shortText;

    private long preis;
    
    @Lob
    @NotNull
    private String longText;

    @NotNull(message = "Das Datum darf nicht leer sein.")
    private Date dueDate;


    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Verkaufsanzeige() {
    }

    public Verkaufsanzeige(User owner, Category category, AnzeigeArt anzeigeArt, String shortText, String longText, Date dueDate, long preis, PreisArt preisArt) {
        this.owner = owner;
        this.category = category;
        this.anzeigeArt=anzeigeArt;
        this.shortText = shortText;
        this.longText = longText;
        this.dueDate = dueDate;
        this.preis=preis;
        this.preisArt=preisArt;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getShortText() {
        return shortText;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public AnzeigeArt getAnzeigeArt() {
        return anzeigeArt;
    }

    public void setAnzeigeArt(AnzeigeArt anzeigeArt) {
        this.anzeigeArt = anzeigeArt;
    }
    
    public PreisArt getPreisArt(){
        return preisArt;
    }
    
    public void setPreisArt(PreisArt preisArt){
        this.preisArt=preisArt;
    }
    
    public long getPreis(){
        return preis;
    }
    
    public void setPreis(long preis){
        this.preis=preis;
    }


    //</editor-fold>

}
