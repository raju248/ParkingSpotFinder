/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingmanagementsystem;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Tech Land
 */
public class OtherPermanentParkingSpotController implements Initializable {

    @FXML
    private JFXListView<PermanentParkingSpot> ListView;
    @FXML
    private JFXTextField address;
    @FXML
    private JFXCheckBox guardCheck;
    @FXML
    private JFXButton search;
    
    DatabaseHelper c = new DatabaseHelper();
    int guard = 0;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        ListView.setPlaceholder(new Label("No Content In List"));

        address.textProperty().addListener((observable, oldValue, newValue) -> {

            
            if (guardCheck.isSelected()) {
                guard = 1;
            }
            else
                guard = 0;

            loadData();

        });
        
        
        guardCheck.setOnAction(e->
            {
                if(!address.getText().isEmpty())
                {
                        if(guardCheck.isSelected())
                            guard = 1;
                        else
                            guard = 0;
                        
                        loadData();
                }
            });
        
        
        search.setOnAction(e->
        {
            if(address.getText().isEmpty())
            {
                address.requestFocus();
                return;
            }
            
            if(guardCheck.isSelected())
                guard = 1;
            else
                guard = 0;
            
            loadData();
            
        });
        
        loadDataEverythingOnFirstRun();
        
    }    
    
    ObservableList<PermanentParkingSpot> list = FXCollections.observableArrayList();

    class XCell extends ListCell<PermanentParkingSpot> {

        PermanentParkingSpot lastItem;

        VBox vbox = new VBox();
        
        Label IDLabel = new Label("ID : ");
        Label ID = new Label();
        HBox hbox0 = new HBox();

        Label AddressLabel = new Label("Address : ");
        Label Address1 = new Label();
        HBox hbox1 = new HBox();

        Label PhoneNoLabel = new Label("Phone No : ");
        Label PhoneNo1 = new Label("");
        HBox hbox2 = new HBox();

        Label RentLabel = new Label("Rent : ");
        Label Rent1 = new Label("");
        HBox hbox3 = new HBox();

        Label GuardLabel = new Label("Guard : ");
        Label Guard = new Label("");
        HBox hbox4 = new HBox();

        Label dateLabel = new Label("Added : ");
        Label Date = new Label("");
        HBox hbox5 = new HBox();
        
        Label nameLabel = new Label("Added by: ");
        Label name = new Label("");
        HBox hbox6 = new HBox();


        public XCell() {
            super();

            this.getStylesheets().add("GeniunCoder.css");

            this.hbox0.getChildren().addAll(IDLabel, ID);
            this.hbox1.getChildren().addAll(AddressLabel, Address1);
            this.hbox2.getChildren().addAll(RentLabel, Rent1);
            this.hbox3.getChildren().addAll(PhoneNoLabel, PhoneNo1);
            this.hbox4.getChildren().addAll(GuardLabel, Guard);
            this.hbox5.getChildren().addAll(dateLabel, Date);
            this.hbox6.getChildren().addAll(nameLabel, name);

            this.vbox.setPadding(new Insets(10, 10, 10, 10));
            String cssLayout = "-fx-border-color: #00001a;\n"
                    + "-fx-border-insets: 2;\n"
                    + "-fx-border-width: 3;\n"
                    + "-fx-font-weight: bold";

            this.vbox.setStyle(cssLayout);

            this.vbox.getChildren().addAll(hbox0, hbox1, hbox2, hbox3, hbox4, hbox5,hbox6);
        }

        protected void updateItem(PermanentParkingSpot item, boolean empty) {
            super.updateItem(item, empty);
            setText(null);  // No text in label of super class
            if (empty) {
                lastItem = null;
                setGraphic(null);
            } else {
                lastItem = item;

                this.Address1.setText(item.getAddress());
                this.Rent1.setText(String.valueOf(item.getRent()) + " Tk.");
                this.PhoneNo1.setText(item.getPhoneNo());
                this.ID.setText(String.valueOf(item.getID()));
                this.name.setText(item.getName());

                if (item.getGuard() == 1) {
                    Guard.setText("Available");
                } else {
                    Guard.setText("Not Available");
                }

                Date.setText(item.getAddedDate());

                setGraphic(vbox);
            }

        }

    }
    
    public void loadDataEverythingOnFirstRun()
    {
        ListView.getItems().clear();
        list.clear();
        
        c.connectDB();
        try {

            String sql = "select * from Ads join ParkingSpotOwner on Ads.SpotOwnerId = ParkingSpotOwner.SpotOwnerId "
                       + "join Users on Users.UserId = ParkingSpotOwner.UserId where ParkingSpotOwner.SpotOwnerId <> "+ ParkingSpotOwnerHomeController.ps.getSpotOwerId();
            
            PreparedStatement ps = c.connection.prepareStatement(sql);
            
            ResultSet resultSet = ps.executeQuery();
            
            ArrayList<PermanentParkingSpot> dataForTable = new ArrayList();

            while (resultSet.next()) {

                PermanentParkingSpot pps = new PermanentParkingSpot();

                Date date = null;
                Timestamp timestamp = resultSet.getTimestamp("addedDate");
                if (timestamp != null) {
                    date = new java.util.Date(timestamp.getTime());
                }

                SimpleDateFormat sm = new SimpleDateFormat("EE dd-MMMM-yyyy hh:mm a");

//                String StartTime = String.valueOf(resultSet.getDate("StartTime"));
                String addedDate = sm.format(date);
                String Address = resultSet.getString("Address");
                String contact = resultSet.getString("Contact");

                System.out.println(Address);
                
                int guard = resultSet.getInt("Guard");
                int rent = resultSet.getInt("Rent");

                pps.setAddedDate(addedDate);
                pps.setAddress(Address);

                pps.setGuard(guard);
                pps.setPhoneNo(contact);
                pps.setRent(rent);
                pps.setName(resultSet.getString("Name"));
                pps.setID(resultSet.getInt("AddID"));

                list.add(pps);
            }

            if(list.size()==0)
            {
                ListView.setPlaceholder(new Label("No Spot Found"));
            }
            
            ListView.setItems(list);

            ListView.setCellFactory(new Callback<ListView<PermanentParkingSpot>, ListCell<PermanentParkingSpot>>() {

                @Override
                public ListCell<PermanentParkingSpot> call(ListView<PermanentParkingSpot> param) {
                    return new XCell();
                }

            });

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (c.connection != null) {
                try {
                    c.connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
    
   public void loadData() {
       

        ListView.getItems().clear();
        list.clear();
        
        c.connectDB();
        try {

            //String sql = "select * from Ads where Address like ? and  Guard = ? and SpotOwnerId <> "+ ParkingSpotOwnerHomeController.ps.getSpotOwerId();
            
            String sql = "select * from Ads join ParkingSpotOwner on Ads.SpotOwnerId = ParkingSpotOwner.SpotOwnerId "
                       + "join Users on Users.UserId = ParkingSpotOwner.UserId where Address like ? and Guard = ? "
                    + "and ParkingSpotOwner.SpotOwnerId <> " +ParkingSpotOwnerHomeController.ps.getSpotOwerId();
            
            PreparedStatement ps = c.connection.prepareStatement(sql);
            ps.setString(1, "%" + address.getText().trim() +"%");
            ps.setInt(2, guard);
            ResultSet resultSet = ps.executeQuery();
            
            ArrayList<PermanentParkingSpot> dataForTable = new ArrayList();

            while (resultSet.next()) {

                PermanentParkingSpot pps = new PermanentParkingSpot();

                Date date = null;
                Timestamp timestamp = resultSet.getTimestamp("addedDate");
                if (timestamp != null) {
                    date = new java.util.Date(timestamp.getTime());
                }

                SimpleDateFormat sm = new SimpleDateFormat("EE dd-MMMM-yyyy hh:mm a");

//                String StartTime = String.valueOf(resultSet.getDate("StartTime"));
                String addedDate = sm.format(date);
                String Address = resultSet.getString("Address");
                String contact = resultSet.getString("Contact");

                System.out.println(Address);
                
                int guard = resultSet.getInt("Guard");
                int rent = resultSet.getInt("Rent");

                pps.setAddedDate(addedDate);
                pps.setAddress(Address);

                pps.setGuard(guard);
                pps.setPhoneNo(contact);
                pps.setRent(rent);
                pps.setName(resultSet.getString("Name"));
                pps.setID(resultSet.getInt("AddID"));

                list.add(pps);
            }

            if(list.size()==0)
            {
                ListView.setPlaceholder(new Label("No Spot Found"));
            }
            
            ListView.setItems(list);

            ListView.setCellFactory(new Callback<ListView<PermanentParkingSpot>, ListCell<PermanentParkingSpot>>() {

                @Override
                public ListCell<PermanentParkingSpot> call(ListView<PermanentParkingSpot> param) {
                    return new XCell();
                }

            });

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (c.connection != null) {
                try {
                    c.connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }

    }
}
