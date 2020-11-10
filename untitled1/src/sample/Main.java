package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.Classes.Theatre;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class Main extends Application {

    private static StackPane centrePane = new StackPane();
    Timer timer;


    @Override
    public void start(Stage primaryStage) throws Exception{

        BorderPane root = new BorderPane();


        primaryStage.setTitle("NOX CINEMA");
        primaryStage.setScene(new Scene(createContent(root),1300,600));
        primaryStage.getIcons().add(new Image("sample/movie_logo.png"));
        primaryStage.show();


    }

    private Parent createContent(BorderPane root){


        root.setCenter(new ScrollPane(centrePane));
        centrePane.setPadding(new Insets(0,50,0,50));

        Theatre theatre = new Theatre(10,15);
        VBox row = new VBox();
        row.setSpacing(5);



        createSeats(theatre,row);
        createRightInterface(root);


        centrePane.getChildren().add(row);



        return root;
    }



    private void createSeats(Theatre theatre, VBox row){

        Rectangle emptyRectangle = new Rectangle(120,10);
        emptyRectangle.setFill(Color.TRANSPARENT);

        HBox columnIndexHbox = new HBox();
        columnIndexHbox.setAlignment(Pos.CENTER);
        columnIndexHbox.setSpacing(5);
        columnIndexHbox.getChildren().add(emptyRectangle);
        for(int i = 0; i< theatre.getSeatRows().get(0).size(); i++) {
            columnIndexHbox.getChildren().add(new ColumnIndexUI(String.valueOf(i+1)));
        }

        row.getChildren().addAll(columnIndexHbox);



        for(int i=0; i < theatre.getSeatRows().size(); i++){



            HBox columInRow = new HBox();
            columInRow.setSpacing(5);
            columInRow.setAlignment(Pos.CENTER);


            columInRow.getChildren().add(new RowIndexUI(theatre.getSeatRows().get(i).get(0).getSeatNo().substring(0,1)));

            for(int j=0; j< theatre.getSeatRows().get(i).size(); j++){


                SeatUI seatUI = new SeatUI(theatre.getSeatRows().get(i).get(j));



                columInRow.getChildren().add(seatUI);

            }

            row.getChildren().add(columInRow);

        }
    }



    private void createRightInterface(BorderPane root){



        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinWidth(300);
        StackPane stackPane = new StackPane();
        scrollPane.setContent(stackPane);
        scrollPane.setFitToHeight(true);
        stackPane.setStyle("-fx-background-color: #d7d6db;");
        stackPane.setMinWidth(300);
        root.setRight(scrollPane);



        stackPane.getChildren().add(createBeforeSelectionInteface(stackPane,root));



    }

    private Node createBeforeSelectionInteface(StackPane stackPane, Node root){
        stackPane.setAlignment(Pos.CENTER);

        HBox movieHbox = new HBox();
        VBox movieTitleVbox = new VBox();
        movieHbox.setPadding(new Insets(50,10,50,10));
        movieHbox.setSpacing(25);
        ImageView movieImage = new ImageView(new Image("sample/the-incredibles.jpg"));
        movieImage.setFitHeight(150);
        movieImage.setFitWidth(112.5);
        Text movieTitleText = new Text("The Deplorable");
        Text featuringText = new Text("\nFeaturing: Tariff Man");
        movieTitleText.setFont(Font.font("Agency FB",FontWeight.EXTRA_BOLD,24));
        featuringText.setFont(Font.font(null,FontWeight.BOLD,12));
        Text stars = new Text("\u2605 \u2605 \u2605 \u2605 \u2605");
        stars.setFont(Font.font(20));
        stars.setFill(Color.rgb(212,175,55));
        HBox ratingHBox = new HBox(stars);
        movieTitleVbox.getChildren().addAll(movieTitleText,featuringText,ratingHBox);
        movieHbox.setAlignment(Pos.CENTER);
        movieHbox.getChildren().addAll(movieTitleVbox,movieImage);


        Text availableText = new Text("Available");
        Text bookedText = new Text("Booked");
        Rectangle availableRectangle = new Rectangle(20,20,Color.GREEN);
        Rectangle bookedRectangle = new Rectangle(20,20, Color.RED);
        Button bookButton = new Button("Book Now");
        if(SeatUI.getSelectedSeats().size()<=0)
        bookButton.setDisable(true);

        HBox legendHbox = new HBox();
        legendHbox.setAlignment(Pos.CENTER);
        legendHbox.setSpacing(10);
        legendHbox.getChildren().addAll(availableRectangle,availableText,bookedRectangle,bookedText);

        VBox mainVbox = new VBox();
        mainVbox.setSpacing(50);
        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.getChildren().addAll(movieHbox,legendHbox,bookButton);







        bookButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Printing Selected Lists...");
                for(SeatUI seat: SeatUI.getSelectedSeats()){
                    System.out.println(seat.getSeatNo() + " " + seat.getCategory());
                }
                stackPane.getChildren().remove(mainVbox);
                stackPane.getChildren().add(createCustInterface(stackPane,root));
                centrePane.setDisable(true);


            }
        });

        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(SeatUI.getSelectedSeats().size() <= 0){
                    bookButton.setDisable(true);
                } else {
                    bookButton.setDisable(false);
                }
            }
        });

        return mainVbox;
    }

    private Node createCustInterface(StackPane stackPane, Node root){

        VBox mainVbox = new VBox();
        mainVbox.setSpacing(10);

        Text custInfoText = new Text("Please enter customer details: ");
        custInfoText.setFont(Font.font("Agency FB", FontWeight.BOLD,24));

        HBox custInfoTextHbox = new HBox();
        custInfoTextHbox.setAlignment(Pos.CENTER);
        custInfoTextHbox.setPadding(new Insets(10,0,40,0));
        custInfoTextHbox.getChildren().add(custInfoText);

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(15,15,0,10));
        gridPane.setVgap(7);
        gridPane.setHgap(5);


        for(int i =0; i< SeatUI.getSelectedSeats().size(); i++){

            gridPane.add(new Text("Customer "+ (i+1) + ": "),0,i);
            gridPane.add(new TextField(),1,i);
        }


        Button proceedButton = new Button("Proceed");
        Button cancelButton = new Button("Cancel");

        HBox buttonsHbox = new HBox();
        buttonsHbox.setSpacing(10);
        buttonsHbox.setAlignment(Pos.CENTER);
        buttonsHbox.getChildren().addAll(proceedButton,cancelButton);

        mainVbox.getChildren().addAll(custInfoTextHbox,gridPane, buttonsHbox);




        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stackPane.getChildren().remove(mainVbox);
                stackPane.getChildren().add(createBeforeSelectionInteface(stackPane,root));
                centrePane.setDisable(false);
            }
        });

        proceedButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stackPane.getChildren().remove(mainVbox);
                stackPane.getChildren().add(createPayInfoInterface(stackPane,root));
            }
        });
        return mainVbox;
    }

    private Node createPayInfoInterface(StackPane stackPane, Node root){


        VBox mainVbox = new VBox();
        mainVbox.setSpacing(10);

        Text paymentInfoText = new Text("Enter payment details: ");
        paymentInfoText.setFont(Font.font("Agency FB", FontWeight.BOLD,24));

        HBox custInfoTextHbox = new HBox();
        custInfoTextHbox.setAlignment(Pos.CENTER);
        custInfoTextHbox.setPadding(new Insets(10,0,40,0));
        custInfoTextHbox.getChildren().add(paymentInfoText);


        VBox payMethodVbox = new VBox();
        payMethodVbox.setSpacing(10);
        payMethodVbox.setPadding(new Insets(0,0,0,20));
        Text selectPayMethodText =  new Text("Select payment method: ");
        ToggleGroup payMethodToggleGroup = new ToggleGroup();
        RadioButton cashRadioButton = new RadioButton("Cash");
        RadioButton creditCardRadioButton = new RadioButton("Credit Card");
        cashRadioButton.setToggleGroup(payMethodToggleGroup);
        cashRadioButton.setSelected(true);
        creditCardRadioButton.setToggleGroup(payMethodToggleGroup);
        payMethodVbox.getChildren().addAll(selectPayMethodText,cashRadioButton,creditCardRadioButton);




        Button processButton = new Button("Process");
        Button cancelButton = new Button("Cancel");

        HBox buttonsHbox = new HBox();
        buttonsHbox.setSpacing(10);
        buttonsHbox.setAlignment(Pos.CENTER);
        buttonsHbox.getChildren().addAll(processButton,cancelButton);


        VBox summaryVbox = new VBox();
        summaryVbox.setPadding(new Insets(50,0,0,0));


        GridPane summaryGridPane = new GridPane();
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(75);
        column1.setHalignment(HPos.CENTER);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(75);
        column2.setHalignment(HPos.CENTER);
        summaryGridPane.getColumnConstraints().addAll(column1,column1);


        summaryGridPane.setHgap(50);
        summaryGridPane.setVgap(10);
        Text summaryText = new Text("Summary");
        summaryText.setFont(Font.font("Agency FB", FontWeight.BOLD,20));
        summaryText.setUnderline(true);
        summaryGridPane.add(summaryText,0,0,2,1);

        Text seatNoText= new Text("Seat No");
        seatNoText.setFont(Font.font("Agency FB", FontWeight.BOLD,18));
        Text priceText = new Text("Price");
        priceText.setFont(Font.font("Agency FB", FontWeight.BOLD,18));
        summaryGridPane.add(seatNoText,0,1);
        summaryGridPane.add(priceText,1,1);


        double total =0;
        for(int i = 0; i < SeatUI.getSelectedSeats().size(); i++){

            SeatUI seat = SeatUI.getSelectedSeats().get(i);
            Text seatNo = new Text(seat.getSeatNo());
            Text seatPrice = new Text("$" + seat.getPrice());
            summaryGridPane.add(seatNo,0,i+2);
            summaryGridPane.add(seatPrice,1,i+2);


            total += seat.getPrice();
        }

        Text totalText = new Text("Total");
        totalText.setFont(Font.font("Agency FB", FontWeight.EXTRA_BOLD,18));
        Text totalValueText = new Text("$" + total);
        totalValueText.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,18));
        totalValueText.setFill(Color.GREEN);

        summaryGridPane.add(totalText,0,2+SeatUI.getSelectedSeats().size());
        summaryGridPane.add(totalValueText,1,2+SeatUI.getSelectedSeats().size());

        summaryVbox.getChildren().add(summaryGridPane);



        mainVbox.getChildren().addAll(custInfoTextHbox,payMethodVbox, buttonsHbox,summaryVbox);



        processButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                stackPane.getChildren().remove(mainVbox);
                stackPane.getChildren().add(createProcessTransaction(stackPane,root));
            }
        });

        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stackPane.getChildren().remove(mainVbox);
                stackPane.getChildren().add(createBeforeSelectionInteface(stackPane,root));
                centrePane.setDisable(false);
            }
        });



        return mainVbox;
    }

    private Node createProcessTransaction(StackPane stackPane, Node root){

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(25);

        Text waitText = new Text("Please wait, we are processing your request...");
        Text successTransText = new Text("Transaction Successful!");
        successTransText.setFont(Font.font("Arial",FontWeight.MEDIUM,18));
        successTransText.setFill(Color.GREEN);

        ProgressIndicator pb = new ProgressIndicator();

        Button okButton = new Button("OK");

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {

                        vbox.getChildren().removeAll(waitText,pb);
                        vbox.getChildren().addAll(successTransText,okButton);
                        cancel();
                        timer.cancel();
                        timer.purge();


                        for (SeatUI seat:SeatUI.getSelectedSeats()){
                            seat.getRectangle().setFill(Color.RED);
                            seat.removeAllEventHandlers();
                        }
                        SeatUI.getSelectedSeats().clear();

                    }
                });

            }
        };


        vbox.getChildren().addAll(waitText,pb);
        timer = new Timer();
        timer.schedule(timerTask,5000);


        okButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stackPane.getChildren().remove(vbox);
                stackPane.getChildren().add(createBeforeSelectionInteface(stackPane,root));
                centrePane.setDisable(false);
            }
        });
        return vbox;
    }


    public static void main(String[] args) {
        launch(args);
    }
}



class SeatUI extends StackPane {

    private static List<SeatUI> selectedSeats = new ArrayList<>();
    private String seatNo;
    private String category;
    private double price;
    private boolean isSelected = false;
    private Text categoryText;
    private Rectangle rectangle = new Rectangle(40,40);


    public SeatUI(Theatre.Seat seat) {

        this.seatNo = seat.getSeatNo();
        this.category = seat.getCategory();
        this.price = seat.getPrice();

        setAlignment(Pos.CENTER);
        rectangle.setFill(Color.GREEN);
        rectangle.setStrokeWidth(0);

        getChildren().add(rectangle);

        if(category.equals("VIP")){
            categoryText = new Text(category);
            categoryText.setFill(Color.WHITE);
            getChildren().add(categoryText);
            rectangle.setFill(Color.rgb(102, 122, 155));
        } else if(category.equals("ROYAL")){
           rectangle.setFill(Color.rgb(16, 86, 19));
        }




        setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                setCursor(Cursor.HAND);
                rectangle.setFill(Color.rgb(234, 188, 23));
                System.out.println(seatNo + " " + category);
            }
        });

        setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(!isSelected)
                    if(category.equals("VIP")) {
                        rectangle.setFill(Color.rgb(102, 122, 155));
                    }  else if(category.equals("ROYAL")){
                        rectangle.setFill(Color.rgb(16, 86, 19));
                    }else {
                        rectangle.setFill(Color.GREEN);
                    }
            }
        });



        setOnMouseClicked( new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(mouseEvent.getButton() == MouseButton.PRIMARY){
                    if(!isSelected){
                        rectangle.setFill(Color.rgb(234, 188, 23));
                        isSelected = true;
                        addToSelectedList();

                    } else {

                        if(category.equals("VIP")) {
                            rectangle.setFill(Color.rgb(102, 122, 155));
                        } else if(category.equals("ROYAL")){
                            rectangle.setFill(Color.rgb(16, 86, 19));
                        } else {
                            rectangle.setFill(Color.GREEN);
                        }
                        isSelected = false;
                        removeFromSelectedList();
                    }

                }

            }
        });



    }

    private void addToSelectedList(){

        selectedSeats.add(this);
    }

    private  void removeFromSelectedList(){
        selectedSeats.remove(this);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public static List<SeatUI> getSelectedSeats() {
        return selectedSeats;
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


    public void removeAllEventHandlers(){

        setOnMouseEntered(null);
        setOnMouseExited(null);
        setOnMouseClicked(null);
    }
}


class ColumnIndexUI extends StackPane{


    public ColumnIndexUI(String column) {

        Rectangle rectangle = new Rectangle(40,20);
        rectangle.setStrokeWidth(0);
        rectangle.setFill(Color.rgb(119, 121, 124));

        Text columnIndexText = new Text(column);
        columnIndexText.setFill(Color.WHITE);
        columnIndexText.setFont(Font.font(null,FontWeight.MEDIUM,14));
        setPadding(new Insets(0,0,100,0));
        setAlignment(Pos.CENTER);
        getChildren().addAll(rectangle,columnIndexText);


    }

}

class RowIndexUI extends StackPane{


    public RowIndexUI(String column) {

        Rectangle rectangle = new Rectangle(20,40);
        rectangle.setStrokeWidth(0);
        rectangle.setFill(Color.rgb(119, 121, 124));

        Text rowIndexText = new Text(column);
        rowIndexText.setFill(Color.WHITE);
        rowIndexText.setFont(Font.font(null,FontWeight.MEDIUM,14));
        setPadding(new Insets(0,100,0,0));
        setAlignment(Pos.CENTER);
        getChildren().addAll(rectangle,rowIndexText);


    }

}
