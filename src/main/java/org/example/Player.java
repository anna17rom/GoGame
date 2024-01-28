package org.example;

import org.example.ServerResponse.Mode;

import java.util.HashSet;
import java.util.Set;

// Abstrakcyjna klasa Player, reprezentująca gracza w grze
public abstract class Player {
    protected int IntersectionsInTerritories;
    Boolean IfPassed=false; // Flaga informująca o tym, czy gracz przeszedł (czy zatwierdził "pass")
    protected int capturedStones; // Liczba zdobytych kamieni przez gracza
    protected Set<Set<Intersection>> territories=new HashSet<>(); // Zbiór terytoriów gracza
    int size;  // Rozmiar planszy
    private String name;  // Nazwa gracza
    protected int moveCount = 0;  // Licznik wykonanych ruchów

    // Metoda zwracająca nazwę gracza
    public String getName() {
        return name;
    }

    // Metoda ustawiająca nazwę gracza
    public void setName(String name) {
        this.name = name;
    }

    // Metoda abstrakcyjna zwracająca tryb gry gracza
    public abstract Mode mode();

    // Metoda abstrakcyjna zwracająca następny ruch gracza
    public abstract GoBoard.Stone nextMove(GoBoard board);

    // Metoda abstrakcyjna wysyłająca informację o rozpoczęciu gry
    public abstract void sendGameStarted(int player, GoBoard board);

    // Metoda zwracająca liczbę wykonanych ruchów przez gracza
    public int getMoveCount() {
        return moveCount;
    }

    // Metoda abstrakcyjna wysyłająca planszę do gracza
    public abstract void sendBoard(GoBoard board);

    // Metoda abstrakcyjna zwracająca liczbę zdobytych kamieni przez gracza
    public abstract int getCapturedStones();

    // Metoda abstrakcyjna dodająca zdobyte kamienie do puli gracza
    public abstract void addCapturedStones(int nb);

    // Metoda abstrakcyjna zwracająca liczbę skrzyżowań w terytoriach gracza
    public abstract int getIntersectionsInTerritories();

    // Metoda abstrakcyjna zwracająca zbiór terytoriów gracza
    public abstract Set<Set<Intersection>> getTerritories();

    // Metoda abstrakcyjna ustawiająca zbiór terytoriów gracza
    public abstract Set<Set<Intersection>> setTerritories(Set<Set<Intersection>> territories);

    // Metoda abstrakcyjna dodająca terytorium do zbioru terytoriów gracza
    public abstract void addTerritory(Set<Intersection> territories);
    // Metoda abstrakcyjna obliczająca terytoria gracza
    public abstract void calculateTerritory();

    // Metoda abstrakcyjna usuwająca zdobyte kamienie z puli gracza
    public abstract void removeCapturedStones(int nb);

    // Metoda abstrakcyjna sprawdzająca, czy gracz zatwierdził pas
    public abstract boolean passed();

    // Metoda abstrakcyjna wysyłająca informację o zakończeniu gry do gracza
    public abstract void sendGameOver();

    // Metoda abstrakcyjna zatwierdzająca pas gracza siłą
    public abstract void EnforsedPass();
    // Metoda zwracająca rozmiar planszy
    protected int getSize(){return this.size;}
}
