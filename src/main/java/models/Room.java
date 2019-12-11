package models;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private int id;
    private int nrJucatori;
    private String numeCreator;
    private List<String> jucatori=new ArrayList<>();

    public Room(int id, int nrJucatori, String numeCreator) {
        this.id = id;
        this.nrJucatori = nrJucatori;
        this.numeCreator = numeCreator;
    }

    public Room(String numeCreator) {
        this.nrJucatori=0;
        this.numeCreator = numeCreator;
        this.jucatori.add(numeCreator);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", nrJucatori=" + nrJucatori +
                ", numeCreator='" + numeCreator + '\'' +
                ", jucatori=" + jucatori +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNrJucatori() {
        return nrJucatori;
    }

    public void setNrJucatori(int nrJucatori) {
        this.nrJucatori = nrJucatori;
    }

    public String getNumeCreator() {
        return numeCreator;
    }

    public void setNumeCreator(String numeCreator) {
        this.numeCreator = numeCreator;
    }

    public List<String> getJucatori() {
        return jucatori;
    }

    public void addJucator(String jucator) {
        this.jucatori.add(jucator);
        this.nrJucatori++;
    }

    public void setJucatori(List<String> jucatori) {
        this.jucatori = jucatori;
    }


}
