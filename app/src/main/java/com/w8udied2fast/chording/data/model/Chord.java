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
        private int finger;   // 0 - не указан, 1-4 это номер пальца (1 - указательный, 4 - мизинец)

        // Конструкторы
        public Position(int string, int fret, int finger) {
            this.string = string;
            this.fret = fret;
            this.finger = finger;
        }

        public Position(int string, int fret) {
            this(string, fret, 0);
        }

        public Position() {}

        // Геттеры и сеттеры
        public int getString() { return string; }
        public void setString(int string) { this.string = string; }

        public int getFret() { return fret; }
        public void setFret(int fret) { this.fret = fret; }

        public int getFinger() { return finger; }
        public void setFinger(int finger) { this.finger = finger; }
    }
}