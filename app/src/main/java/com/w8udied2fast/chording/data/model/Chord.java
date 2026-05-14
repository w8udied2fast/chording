package com.w8udied2fast.chording.data.model;

import java.util.List;

public class Chord {
    private String name;
    private String root;
    private List<Position> positions;

    // Геттеры и сеттеры для полей Chord
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getRoot() { return root; }
    public void setRoot(String root) { this.root = root; }

    public List<Position> getPositions() { return positions; }
    public void setPositions(List<Position> positions) { this.positions = positions; }

    // Внутренний класс для позиции пальца на грифе
    public static class Position {
        private int string; // 1-6 (1 это самая тонкая струна, 6 это самая толстая)
        private int fret;   // 0 - открытая струна, >0 - номер лада

        // Геттеры
        public int getString() { return string; }
        public int getFret() { return fret; }

        // Сеттеры
        public void setString(int string) { this.string = string; }
        public void setFret(int fret) { this.fret = fret; }
    }
}