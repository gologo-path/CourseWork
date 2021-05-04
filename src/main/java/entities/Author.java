package entities;

import java.io.Serializable;

public class Author implements Serializable {

        protected String name;
        protected String surname;
        protected String fathers;
        protected char id_a;


        Author(char id_a, String name, String surname,String fathers){
            this.id_a = id_a;
            this.name = name;
            this.surname = surname;
            this.fathers = fathers;
        }
        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getSurname() {
            return surname;
        }
        public void setFathers(String fathers) {
            this.fathers = fathers;
        }

        public String getFathers() {
            return fathers;
        }
        public void setId_a(char id_a) {
            this.id_a = id_a;
        }

        public char getId_a() {
            return id_a;
        }
}
