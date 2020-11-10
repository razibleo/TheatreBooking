package sample.Classes;

import java.util.ArrayList;

public class Theatre {

    private int rows;
    private int columns;
    private ArrayList<Seat> seatRow;
    private ArrayList<ArrayList<Seat>> seatRows = new ArrayList<>();

    public Theatre(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;

        createSeats();
    }

    public void createSeats(){

        char alphabet = '\u0040';
        String category;
        double price;


        for (int i=0; i< this.rows; i++) {
            alphabet++;

            seatRow = new ArrayList<>();


            double relativeRowPosition = (double)(i+1)/(double)rows;

            if(relativeRowPosition<=0.7){
                category = "Saver";
                price = 10;

            } else if(relativeRowPosition<=0.9){
                category = "ROYAL";
                price = 15;
            } else{
                category = "VIP";
                price = 30;
            }





            for (int j = 0; j < this.columns; j++) {

                String row = String.format("%s%04d",String.valueOf(alphabet),j+1);


                seatRow.add(new Seat(row,category,price));
            }
            seatRows.add(seatRow);
        }
    }

    public ArrayList<ArrayList<Seat>> getSeats() {
        return seatRows;
    }

     public static class Seat{

        String seatNo;
        String category;
        private double price;

        public Seat(String seatNo, String category, double price) {
            this.seatNo = seatNo;
            this.category = category;
            this.price = price;
        }

        public String getSeatNo() {
            return seatNo;
        }

        public String getCategory() {
            return category;
        }

         public double getPrice() {
             return price;
         }

         @Override
        public String toString() {
            return this.seatNo +  " " + this.price;
        }
    }

    public ArrayList<Seat> getSeatRow() {
        return seatRow;
    }

    public ArrayList<ArrayList<Seat>> getSeatRows() {
        return seatRows;
    }

    public void printSeats(){

        for(int i=0; i < getSeatRows().size(); i++){


            for(int j=0; j< getSeatRows().get(i).size(); j++){


                System.out.println(getSeatRows().get(i).get(j));
            }
        }

    }

}