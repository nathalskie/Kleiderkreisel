/*
 * Copyright © 2018 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.kleiderkreisel.ejb;

import dhbwka.wwi.vertsys.javaee.kleiderkreisel.jpa.Category;
import dhbwka.wwi.vertsys.javaee.kleiderkreisel.jpa.Verkaufsanzeige;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * Einfache EJB mit den üblichen CRUD-Methoden für Aufgaben
 */
@Stateless
@RolesAllowed("kleiderkreisel-app-user")
public class VerkaufsanzeigeBean extends EntityBean<Verkaufsanzeige, Long> { 
   
    public VerkaufsanzeigeBean() {
        super(Verkaufsanzeige.class);
    }
  
    /**
     * Alle Aufgaben eines Benutzers, nach Fälligkeit sortiert zurückliefern.
     * @param username Benutzername
     * @return Alle Aufgaben des Benutzers
     */
    public List<Verkaufsanzeige> findByUsername(String username) {
        return em.createQuery("SELECT a FROM Verkaufsanzeige a WHERE a.owner.username = :username ORDER BY a.dueDate")
                 .setParameter("username", username)
                 .getResultList();
    }
    
    /**
     * Suche nach Aufgaben anhand ihrer Bezeichnung, Kategorie und Status.
     * 
     * Anders als in der Vorlesung behandelt, wird die SELECT-Anfrage hier
     * mit der CriteriaBuilder-API vollkommen dynamisch erzeugt.
     * 
     * @param search In der Kurzbeschreibung enthaltener Text (optional)
     * @param category Kategorie (optional)
     * @return Liste mit den gefundenen Aufgaben
     */
    public List<Verkaufsanzeige> search(String search, Category category) {
        // Hilfsobjekt zum Bauen des Query
        CriteriaBuilder cb = this.em.getCriteriaBuilder();
        
        // SELECT t FROM Anzeige t
        CriteriaQuery<Verkaufsanzeige> query = cb.createQuery(Verkaufsanzeige.class);
        Root<Verkaufsanzeige> from = query.from(Verkaufsanzeige.class);
        query.select(from);

        // ORDER BY dueDate, dueTime
        query.orderBy(cb.asc(from.get("dueDate")));
        
        // WHERE t.shortText LIKE :search
        if (search != null && !search.trim().isEmpty()) {
            query.where(cb.like(from.get("shortText"), "%" + search + "%"));
        }
        
        // WHERE t.category = :category
        if (category != null) {
            query.where(cb.equal(from.get("category"), category));
        }
        
        return em.createQuery(query).getResultList();
    }
}
