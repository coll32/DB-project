package com.mycompany.databaseexample;



import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Timestamp;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javafx.collections.transformation.FilteredList;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class DatabaseSQLiteController implements Initializable {


        
    @FXML
    private TableView tableView = new TableView<Book>();
    @FXML 
    private TableView memberView = new TableView<Member>();
    @FXML
    private TableView checkedOutView = new TableView<CheckOut>();
    
    @FXML
    private TableView logsView = new TableView<Logs>();
    
    @FXML
    private TableView overdueView = new TableView<Overdue>();
    @FXML
    private TableView roomView = new TableView<LibraryRoom>();
    
    @FXML
    private ToggleButton overdueToggle;

    @FXML
    private VBox vBox; //like the outer section of ui

    @FXML
    private TextField nameTextField, authorTextField, yearTextField, memberNumber;
    
    @FXML
    private TextField memNameText, libNumText, bookIDTextField;
    
    @FXML
    private TextField bookIDText, actionText, actionDateText, timesCheckedText;
    @FXML
    private TextField dateToReserve;
    @FXML
    private Text errormsg;
    
    @FXML
    private Text recordUpdate;

    @FXML
    Label footerLabel;
    @FXML
    TableColumn id = new TableColumn("ID");

    @Override
    public void initialize(URL location, ResourceBundle rb) {
        try {
            loadData();
        } catch (SQLException ex) {
            
            System.out.println(ex.toString());
        }
        intializeColumns();
        CreateSQLiteTable();
    }

    //String databaseURL = "jdbc:sqlite:src/main/resources/com/mycompany/databaseexample/BooksDB.db";
    String databaseURL = "jdbc:sqlserver://localhost:1433;databaseName=Library;encrypt=true;trustServerCertificate=true;";
    String username = "jessie"; // Replace with your SQL Server username
    String password = "2311";
    /* Connect to a sample database
     */
    private ObservableList<Book> data; //object that holds data
    private ObservableList<Member> memData;
    private ObservableList<CheckOut> checkOutData;
    private ObservableList<Logs> logData;
    private ObservableList<Overdue> overdueData;
    private ObservableList<LibraryRoom> roomData;

    /*
       ArrayList: Resizable-array implementation of the List interface. 
       Implements all optional list operations, and permits all elements, including null.
       ObservableList: A list that allows listeners to track changes when they occur
    */
    
    
    public DatabaseSQLiteController() throws SQLException {
        this.data = FXCollections.observableArrayList();
        this.memData = FXCollections.observableArrayList();
        this.checkOutData = FXCollections.observableArrayList();
        this.logData = FXCollections.observableArrayList();
        this.overdueData = FXCollections.observableArrayList();
        this.roomData = FXCollections.observableArrayList();
    }

    private void intializeColumns() {

        id = new TableColumn("ID");
        id.setMinWidth(50);
        id.setCellValueFactory(new PropertyValueFactory<Book, Integer>("id"));

        TableColumn name = new TableColumn("Name");
        name.setMinWidth(300);
        name.setCellValueFactory(new PropertyValueFactory<Book, String>("name"));

        TableColumn author = new TableColumn("Author");
        author.setMinWidth(200);
        author.setCellValueFactory(new PropertyValueFactory<Book, String>("author"));

        TableColumn year = new TableColumn("Year");
        year.setMinWidth(100);
        year.setCellValueFactory(new PropertyValueFactory<Book, Integer>("year"));
        tableView.setItems(data);
        tableView.getColumns().addAll(id, name, author, year);

        
        //initalize member table
        id = new TableColumn("ID");
        id.setMinWidth(50);
        id.setCellValueFactory(new PropertyValueFactory<Member, Integer>("id"));
        
      
        
        TableColumn memName = new TableColumn("Name");
        memName.setMinWidth(300);
        memName.setCellValueFactory(new PropertyValueFactory<Member, String>("Name"));
        
        TableColumn libNum = new TableColumn("LibraryNumber");
        libNum.setMinWidth(100);
        libNum.setCellValueFactory(new PropertyValueFactory<Member, Integer>("LibraryNumber"));
        
        memberView.setItems(memData);
        memberView.getColumns().addAll(memName, libNum);
        
        //checkedout Table
        id = new TableColumn("ID");
        id.setMinWidth(50);
        id.setCellValueFactory(new PropertyValueFactory<CheckOut, Integer>("id"));
        
      
        
        TableColumn checkedLibNum = new TableColumn("LibraryNumber");
        checkedLibNum.setMinWidth(100);
        checkedLibNum.setCellValueFactory(new PropertyValueFactory<CheckOut, Integer>("LibraryNumber"));
        
        TableColumn checkedBookNum = new TableColumn("BookID");
        checkedBookNum.setMinWidth(100);
        checkedBookNum.setCellValueFactory(new PropertyValueFactory<CheckOut, Integer>("BookID"));
        
        TableColumn checkedBookName = new TableColumn("Book Name");
        checkedBookName.setMinWidth(300);
        checkedBookName.setCellValueFactory(new PropertyValueFactory<CheckOut, String>("name"));
        
        TableColumn dueDate = new TableColumn("Due Date");
        dueDate.setMinWidth(300);
        dueDate.setCellValueFactory(new PropertyValueFactory<CheckOut, String>("dueDate"));
        
        checkedOutView.setItems(checkOutData);
        checkedOutView.getColumns().addAll(checkedLibNum, checkedBookNum, checkedBookName, dueDate);
        
        //logTable
        TableColumn logBookNum = new TableColumn("BookID");
        logBookNum.setMinWidth(100);
        logBookNum.setCellValueFactory(new PropertyValueFactory<Logs, Integer>("BookID"));
        
        TableColumn logAction = new TableColumn("LastAction");
        logAction.setMinWidth(100);
        logAction.setCellValueFactory(new PropertyValueFactory<Logs, String>("LastAction"));
        
        TableColumn logActionTime = new TableColumn("LastActionTime");
        logActionTime.setMinWidth(100);
        logActionTime.setCellValueFactory(new PropertyValueFactory<Logs, String>("LastActionTime"));
        
        TableColumn logCheckedNum = new TableColumn("TimesCheckedOut");
        logCheckedNum.setMinWidth(100);
        logCheckedNum.setCellValueFactory(new PropertyValueFactory<Logs, Integer>("TimesCheckedOut"));
        
        logsView.setItems(logData);
        logsView.getColumns().addAll(logBookNum,logAction,logActionTime, logCheckedNum );
        
        //overdueTable
        TableColumn overdueNum = new TableColumn("LibraryNumber");
        overdueNum.setMinWidth(100);
        overdueNum.setCellValueFactory(new PropertyValueFactory<Overdue, Integer>("LibraryNumber"));
        
        TableColumn overdueBookID = new TableColumn("BookID");
        overdueBookID.setMinWidth(100);
        overdueBookID.setCellValueFactory(new PropertyValueFactory<Overdue, Integer>("BookID"));
       
        TableColumn overdueDate = new TableColumn("DueDate");
        overdueDate.setMinWidth(100);
        overdueDate.setCellValueFactory(new PropertyValueFactory<Overdue, String>("DueDate"));
        
        overdueView.setItems(overdueData);
        overdueView.getColumns().addAll(overdueNum, overdueBookID, overdueDate);
        
        TableColumn roomNumber = new TableColumn("RoomNumber");
        roomNumber.setMinWidth(100);
        roomNumber.setCellValueFactory(new PropertyValueFactory<LibraryRoom, Integer>("RoomNumber"));
        
        TableColumn reservedBy = new TableColumn("ReservedBy");
        reservedBy.setMinWidth(100);
        reservedBy.setCellValueFactory(new PropertyValueFactory<LibraryRoom, Integer>("ReservedBy"));
        
        TableColumn dateReserved = new TableColumn("DateReserved");
        dateReserved.setMinWidth(100);
        dateReserved.setCellValueFactory(new PropertyValueFactory<LibraryRoom, String>("DateReserved"));
        
        TableColumn reservedOn = new TableColumn("ReservedOn");
        reservedOn.setMinWidth(100);
        reservedOn.setCellValueFactory(new PropertyValueFactory<LibraryRoom, String>("ReservedOn"));
        
        roomView.setItems(roomData);
        roomView.getColumns().addAll(roomNumber, reservedBy, dateReserved,reservedOn);
        
    }

    public void loadData() throws SQLException {

        Connection conn = null;
        Statement stmt = null;
 
        try {

            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL, username, password);

            System.out.println("Connection to SQLite has been established.");
            System.out.println(databaseURL);
            String sql = "SELECT * FROM Books;";
            String sql2 = "SELECT * FROM Members;";
            String sql3 = "SELECT * FROM CheckOut";
            String sql4 = "SELECT * FROM BookLog";
            String sql5 = "SELECT * FROM Overdue";
            String sql6 = "SELECT * FROM StudyRooms";
            // Ensure we can query the actors table
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            //System.out.println(rs.getInt("ID") + rs.getString("Name"));

            while (rs.next()) {
                
                Book book;
                book = new Book(rs.getInt("ID"), rs.getString("Name"), rs.getString("Author"), rs.getInt("Year"));
                //System.out.println( book.getId() + book.getName() + book.getAuthor() + book.getYear());
                data.add(book);
            }
            
            stmt = conn.createStatement();
            ResultSet rsMem = stmt.executeQuery(sql2);
            //System.out.println( rsMem.getString("Name"));
            
            while (rsMem.next()) {
                Member member;
                member = new Member(rsMem.getInt("LibraryNumber"), rsMem.getString("Name"));
                //System.out.println( member.getLibraryNumber() + member.getName());
                memData.add(member);
            }
            
            stmt = conn.createStatement();
            ResultSet rsCheck = stmt.executeQuery(sql3);
            //System.out.println( rsMem.getString("Name"));
            
            while (rsCheck.next()) {
                CheckOut checkedOut;
                checkedOut = new CheckOut(rsCheck.getInt("LibraryNumber"), rsCheck.getInt("BookID"), rsCheck.getString("name"), rsCheck.getString("dueDate"));
                System.out.println( checkedOut.getLibraryNumber() + checkedOut.getBookID());
                checkOutData.add(checkedOut);
            }
            
            stmt = conn.createStatement();
            ResultSet rsLogs = stmt.executeQuery(sql4);
            //System.out.println( rsMem.getString("Name"));
            
            while (rsLogs.next()) {
                Logs log;
                //checkedOut = new CheckOut(rsCheck.getInt("LibraryNumber"), rsCheck.getInt("BookID"), rsCheck.getString("name"), rsCheck.getString("dueDate"));
                log = new Logs(rsLogs.getInt("BookID"), rsLogs.getString("LastAction"), rsLogs.getString("LastActionTime"), rsLogs.getInt("TimesCheckedOut"));
                logData.add(log);
            }
            
            stmt = conn.createStatement();
            ResultSet rsover = stmt.executeQuery(sql5);
            //System.out.println( rsMem.getString("Name"));
            
            while (rsover.next()) {
                Overdue overdue;
                //checkedOut = new CheckOut(rsCheck.getInt("LibraryNumber"), rsCheck.getInt("BookID"), rsCheck.getString("name"), rsCheck.getString("dueDate"));
                overdue = new Overdue(rsover.getInt("LibraryNumber"), rsover.getInt("BookID"), rsover.getString("DueDate"));
                overdueData.add(overdue);
            }
            
            stmt = conn.createStatement();
            ResultSet rsRoom = stmt.executeQuery(sql6);
            
            while (rsRoom.next()) {
                LibraryRoom room;
                //checkedOut = new CheckOut(rsCheck.getInt("LibraryNumber"), rsCheck.getInt("BookID"), rsCheck.getString("name"), rsCheck.getString("dueDate"));
                room = new LibraryRoom(rsRoom.getInt("RoomNumber"), rsRoom.getInt("ReservedBy"), rsRoom.getString("DateReserved"), rsRoom.getString("ReservedOn"));
                roomData.add(room);
            }

            rs.close();
            rsMem.close();
            rsCheck.close();
            rsLogs.close();
            rsover.close();
            rsRoom.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public void drawText() {
        //Drawing a text 
        Text text = new Text("The Book Database");

        //Setting the font of the text 
        text.setFont(Font.font("Edwardian Script ITC", 55));

        //Setting the position of the text 
//        text.setX(600);
//        text.setY(100);
        //Setting the linear gradient 
        Stop[] stops = new Stop[]{
            new Stop(0, Color.DARKSLATEBLUE),
            new Stop(1, Color.RED)
        };
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);

        //Setting the linear gradient to the text 
        text.setFill(linearGradient);
        // Add the child to the grid
        vBox.getChildren().add(text);

    }

    /**
     * Insert a new row into the movies table
     *
     * @param name
     * @param author
     * @param year
     * @throws java.sql.SQLException
     */
    
    public void insert(String name, String author, int year) throws SQLException {
        int last_inserted_id = 0;
        Connection conn = null;
        try {
            // create a connection to the database

            conn = DriverManager.getConnection(databaseURL, username, password);

            System.out.println("Connection to SQLite has been established.");

            System.out.println("Inserting one record!");
            Statement stmt = conn.createStatement();
            ResultSet rs2 = stmt.executeQuery("SELECT MAX(ID) FROM Books");
            int maxId = 0;

            if (rs2.next()) {
                maxId = rs2.getInt(1); // Retrieve the maximum ID
            }

            String sql = "INSERT INTO Books(ID, Name, Author, Year) VALUES(?,?,?,?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setInt(1, maxId+1);
            pstmt.setString(2, name);
            pstmt.setString(3, author);
            pstmt.setInt(4, year);
            pstmt.executeUpdate();
            
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                last_inserted_id = rs.getInt(1);
            }
            
            

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("last_inserted_id " + last_inserted_id);
  
        //data.add(new Movie(last_inserted_id, title, year, rating));
        data.add(new Book(last_inserted_id, name, author, year));
    }

    @FXML
    public void handleAddMovie(ActionEvent actionEvent) {
        System.out.println("Name: " + nameTextField.getText() + "\nAuthor: " + authorTextField.getText() + "\nYear: " + yearTextField.getText());

        try {
            // insert a new rows
            insert(nameTextField.getText(), authorTextField.getText(),Integer.parseInt(yearTextField.getText()));

            System.out.println("Data was inserted Successfully");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        nameTextField.setText("");
        authorTextField.setText("");
        yearTextField.setText("");

        footerLabel.setText("Record inserted into table successfully!");
    }
    
    
    
    public void insertMember(String name) throws SQLException {
        int last_inserted_id = 0;
        Connection conn = null;
        try {
            // create a connection to the database

            conn = DriverManager.getConnection(databaseURL, username, password);

            System.out.println("Connection to SQLite has been established.");

            System.out.println("Inserting one record!");

            String sql = "INSERT INTO Members(name) VALUES(?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            //pstmt.setInt(2, libraryNumber);
            pstmt.executeUpdate();
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                last_inserted_id = rs.getInt(1);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        System.out.println("last_inserted_id " + last_inserted_id);
  
        //data.add(new Movie(last_inserted_id, title, year, rating));
        memData.add(new Member(last_inserted_id, name));
    }

    @FXML
    public void handleAddMember(ActionEvent actionEvent) {

        //System.out.println("Name: " + nameTextField.getText() + "\nAuthor: " + authorTextField.getText() + "\nYear: " + yearTextField.getText());

        try {
            // insert a new rows
            //insertMember( memNameText.getText(),Integer.parseInt(libNumText.getText()));
            insertMember( memNameText.getText());

            System.out.println("Data was inserted Successfully");
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }

        memNameText.setText("");
        libNumText.setText("");


        footerLabel.setText("Record inserted into table successfully!");
    }

    private void CreateSQLiteTable() {
        // SQL statement for creating a new table
        /*String sql = "CREATE TABLE IF NOT EXISTS Books (\n"
                + "	id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	author text NOT NULL,\n"
                + "	year int NOT NULL\n"
                + ");";*/
        String sql = "SELECT 1";

        try (Connection conn = DriverManager.getConnection(databaseURL, username, password);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
            
            System.out.println("Table Created Successfully");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteRecord(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL, username, password);

            String sql = "DELETE FROM Books WHERE id=" + Integer.toString(id);
            String sql2 = "DELETE FROM CheckOut WHERE BookID=" + Integer.toString(id);
            String sql3 = "DELETE FROM Members WHERE LibraryNumber=" + Integer.toString(id);
            //String sql = "INSERT INTO CheckOut (id, name, author, year) SELECT id, name, author, year FROM Books WHERE id=" + Integer.toString(id);

            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);
            stmt.executeUpdate(sql3);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            
            tableView.getItems().remove(selectedIndex); //selected index != id because selected index changes based off deletions
            memberView.getItems().remove(selectedIndex);
            checkedOutView.getItems().remove(selectedIndex);
            
            System.out.println("Record Deleted Successfully");
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    @FXML
    private void handleDeleteAction(ActionEvent event) throws IOException {
        System.out.println("Delete Movie");
        //Check whether item is selected and set value of selected item to Label
        if (tableView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle Delete Action");
                System.out.println(index);
                //Movie movie = (Movie) tableView.getSelectionModel().getSelectedItem();
                Book book = (Book) tableView.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + book.getId());
                System.out.println("Name: " + book.getName());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("year: " + book.getYear());
                deleteRecord(book.getId(), selectedIndex);
            }

        }
        if (memberView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = memberView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle Delete Action");
                System.out.println(index);
                Member member = (Member) memberView.getSelectionModel().getSelectedItem();
                //CheckOut checkOut = (CheckOut) checkedOutView.getSelectionModel().getSelectedItem();
                
                deleteRecord(member.getLibraryNumber(), selectedIndex);
            }

        }
        if (checkedOutView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = checkedOutView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle Delete Action");
                System.out.println(index);
               
                CheckOut checkOut = (CheckOut) checkedOutView.getSelectionModel().getSelectedItem();
                
                deleteRecord(checkOut.getBookID(), selectedIndex);
            }

        }
    }
    
        public void checkOut(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL, username, password);

            int memberToInsert =  Integer.valueOf(memberNumber.getText());
            //String sql = "INSERT INTO CheckOut (id, name, author, year) SELECT id, name, author, year FROM Books WHERE id=" + Integer.toString(id);
            String sql = "INSERT INTO CheckOut (BookID, LibraryNumber, name, author, year) SELECT BOOKS.ID, Members.LibraryNumber, BOOKS.Name, Books.Author, Books.Year FROM Books, Members "
                    +"WHERE Books.id = " +Integer.toString(id) + " AND Members.LibraryNumber= " +memberToInsert;
            String deleteSQL = "DELETE FROM Books WHERE ID=" +Integer.toString(id);       
                    //System.out.println(sql);
                    
                    /*INSERT INTO CheckOut (BookID, MemberID)
                    SELECT Books.id, Members.LibraryNumber
                    FROM Books, Members
                    WHERE Books.id = 100
                    AND Members.LibraryNumber = 222;*/
                    
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.executeUpdate(deleteSQL);
            tableView.getItems().remove(selectedIndex);
            System.out.println("Record Checked Out Successfully");
            errormsg.setText(" ");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if (e.getMessage().equals("Cannot have more than 3 check-out records for this library number")) {
                errormsg.setText("Cannot check out more than 3 books");
            } else if (e.getMessage().equals("The transaction ended in the trigger. The batch has been aborted.")){
                errormsg.setText("This book has just been checked out.");
            }else {
                errormsg.setText(e.getMessage());
            }
            
        } catch (NumberFormatException e) {
            errormsg.setText("Please Enter a Library Number");
        } finally {

            //tableView.getItems().remove(selectedIndex); //selected index != id because selected index changes based off deletions
            
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
    @FXML
    private void handleCheckOutAction(ActionEvent event) throws IOException {
        System.out.println("Check Out Book");
        //Check whether item is selected and set value of selected item to Label
        if (tableView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle checkOut Action");
                System.out.println(index);
                //Movie movie = (Movie) tableView.getSelectionModel().getSelectedItem();
                Book book = (Book) tableView.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + book.getId());
                System.out.println("Name: " + book.getName());
                System.out.println("Author: " + book.getAuthor());
                System.out.println("Year: " + book.getYear());
                checkOut(book.getId(), selectedIndex);
            }

        }
    }
    
    public void checkIn(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL, username, password);

            //String sql = "INSERT INTO CheckOut (id, name, author, year) SELECT id, name, author, year FROM Books WHERE id=" + Integer.toString(id);
            String sql = "INSERT INTO Books (id, name, author, year) SELECT BookID, name, author, year FROM CheckOut WHERE BookID =" + Integer.toString(id);
            String sql2 = "DELETE FROM CheckOut WHERE BookID =" +Integer.toString(id);
            
            
               
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
            stmt.executeUpdate(sql2);
            checkedOutView.getItems().remove(selectedIndex); //selected index != id because selected index changes based off deletions
            System.out.println("Record Checked in Successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            //checkedOutView.getItems().remove(selectedIndex); //selected index != id because selected index changes based off deletions
            //System.out.println("Record Checked in Successfully");
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
    @FXML
    private void handleCheckInAction(ActionEvent event) throws IOException {
        System.out.println("Check In Book");
        //Check whether item is selected and set value of selected item to Label
        if (checkedOutView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = checkedOutView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle checkIn Action");
                System.out.println(index);
                //Movie movie = (Movie) tableView.getSelectionModel().getSelectedItem();
                CheckOut checkOut = (CheckOut) checkedOutView.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + checkOut.getBookID());
                System.out.println("Name: " + checkOut.getName());
                System.out.println("Author: " + checkOut.getAuthor());
                System.out.println("year: " + checkOut.getYear());
                checkIn(checkOut.getBookID(), selectedIndex);
            }

        }
    }

    Integer index = -1;

     public void addToOverdue() {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL, username, password);

            //String sql = "INSERT INTO CheckOut (id, name, author, year) SELECT id, name, author, year FROM Books WHERE id=" + Integer.toString(id);
            
            String sql = "INSERT INTO Overdue (LibraryNumber, BookID, DueDate) "
          + "SELECT LibraryNumber, BookID, DueDate "
          + "FROM CheckOut WHERE DueDate < GETDATE() "
          + "AND NOT EXISTS (SELECT 1 FROM Overdue WHERE Overdue.LibraryNumber = CheckOut.LibraryNumber AND Overdue.BookID = CheckOut.BookID AND Overdue.DueDate = CheckOut.DueDate);";

            
               
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
      

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

           
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
     
     @FXML
    private void handleAddToOverdue(ActionEvent event) throws IOException {
        
        //Check whether item is selected and set value of selected item to Label
        addToOverdue();
        recordUpdate.setText("Overdue Records Updated");
    }
    @FXML
    private void showRowData() {

        index = tableView.getSelectionModel().getSelectedIndex();
        if (index <= -1) {
            return;
        }

        System.out.println("showRowData");
        System.out.println(index);
        Book book = (Book) tableView.getSelectionModel().getSelectedItem();
        //Movie movie = (Movie) tableView.getSelectionModel().getSelectedItem();
        System.out.println("ID: " + book.getId());
        System.out.println("Name: " + book.getName());
        System.out.println("Author: " + book.getAuthor());
        System.out.println("Year: " + book.getYear());

        nameTextField.setText(book.getName());
        authorTextField.setText(book.getAuthor());
        yearTextField.setText(Integer.toString(book.getYear()));

        String content = "Id= " + book.getId() + "\nName= " + book.getName() + "\nAuthor=  " + book.getAuthor() + "\nYear= " + Integer.toString(book.getYear());

    }


    @SuppressWarnings("empty-statement")
    public ObservableList<Book> search(String _name, String _author, String _year) throws SQLException {
        ObservableList<Book> searchResult = FXCollections.observableArrayList();
        // read data from SQLite database
        CreateSQLiteTable();
        String sql = "Select * from Books where"; //querey

        if (!_name.isEmpty()) {
            sql += " Name like '%" + _name + "%'";
        }
        if (!_year.isEmpty()) {
            sql += " and Year ='" + _year + "'";
        }

        if (!_author.isEmpty()) {
            sql += " and Author like '%" + _author + "%'";
        }

        System.out.println(sql);
        try (Connection conn = DriverManager.getConnection(databaseURL, username, password);
                Statement stmt = conn.createStatement()) {
            // create a ResultSet

            ResultSet rs = stmt.executeQuery(sql); //
            // checking if ResultSet is empty
            if (rs.next() == false) {
                System.out.println("ResultSet in empty");
            } else {
                // loop through the result set
                do {

                    int recordId = rs.getInt("id");
                    String name = rs.getString("name");
                    String author = rs.getString("author");
                    int year = Integer.parseInt(rs.getString("year"));
                 

                    //Movie movie = new Movie(recordId, title, year, rating);
                    Book book = new Book(recordId, name, author, year);
                    searchResult.add(book);
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return searchResult;
    }
    public void reserveRoom(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL, username, password);

            int reservedBy = Integer.valueOf(memberNumber.getText());
            String dateReserved = dateToReserve.getText();
            Timestamp currentTime = new Timestamp(System.currentTimeMillis()); // Get current timestamp
            String sql = "UPDATE StudyRooms SET ReservedBy = ?, ReservedOn = ?, DateReserved = ? WHERE RoomNumber = ?";
        
            preparedStatement = conn.prepareStatement(sql);

       
            preparedStatement.setInt(1, reservedBy);
            preparedStatement.setString(2, dateReserved);
            preparedStatement.setTimestamp(3, currentTime); // Set current timestamp
            preparedStatement.setInt(4, id);

            
            int rowsUpdated = preparedStatement.executeUpdate();
            
            
               
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            System.out.println("Record reserved Successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            //checkedOutView.getItems().remove(selectedIndex); //selected index != id because selected index changes based off deletions
            //System.out.println("Record Checked in Successfully");
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
    @FXML
    private void handleReserveRoom(ActionEvent event) throws IOException {
        System.out.println("reserveRoom");
        //Check whether item is selected and set value of selected item to Label
        if (roomView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = roomView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle room Reserve Action");
                System.out.println(index);
                //Movie movie = (Movie) tableView.getSelectionModel().getSelectedItem();
                LibraryRoom room = (LibraryRoom) roomView.getSelectionModel().getSelectedItem();
                System.out.println("room num: " + room.getRoomNumber());
                System.out.println("reserved by: " + room.getReservedBy());
                System.out.println("reserved on: " + room.getReservedOn());
                System.out.println("date reserved: " + room.getDateReserved());
                reserveRoom(room.getRoomNumber(), selectedIndex);
            }

        }
    }
    
    public void removeReservation(int id, int selectedIndex) {

        Connection conn = null;
        PreparedStatement preparedStatement = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL, username, password);

            String sql = "UPDATE StudyRooms SET ReservedBy = NULL, DateReserved = NULL, ReservedOn = NULL WHERE RoomNumber = ?";
            // Set current timestamp
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            
            int rowsUpdated = preparedStatement.executeUpdate();
            
            
               
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

            System.out.println("Record reserved Successfully");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {

            //checkedOutView.getItems().remove(selectedIndex); //selected index != id because selected index changes based off deletions
            //System.out.println("Record Checked in Successfully");
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }
    @FXML
    private void handleRemoveReservation(ActionEvent event) throws IOException {
        System.out.println("reserveRoom");
        //Check whether item is selected and set value of selected item to Label
        if (roomView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = roomView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println("Handle room Reserve Action");
                System.out.println(index);
                //Movie movie = (Movie) tableView.getSelectionModel().getSelectedItem();
                LibraryRoom room = (LibraryRoom) roomView.getSelectionModel().getSelectedItem();
                System.out.println("room num: " + room.getRoomNumber());
                System.out.println("reserved by: " + room.getReservedBy());
                System.out.println("reserved on: " + room.getReservedOn());
                System.out.println("date reserved: " + room.getDateReserved());
                removeReservation(room.getRoomNumber(), selectedIndex);
            }

        }
    }
    @FXML
    private void handleSearchAction(ActionEvent event) throws IOException, SQLException {
        String _name = nameTextField.getText().trim();
        String _author = authorTextField.getText().trim();
        String _year = yearTextField.getText().trim();
        ObservableList<Book> tableItems = search(_name, _author, _year);
        tableView.setItems(tableItems);

    }
    @FXML
    private void handleMemSearchAction(ActionEvent event) throws IOException, SQLException {
        String _memName = memNameText.getText().trim();
        String _LibraryNumber = libNumText.getText().trim();
        ObservableList<Member> memItems = searchMembers(_memName, _LibraryNumber);
        memberView.setItems(memItems);

    }
    @SuppressWarnings("empty-statement")
    public ObservableList<Member> searchMembers(String _name, String _libraryNumber) throws SQLException {
        ObservableList<Member> searchResult = FXCollections.observableArrayList();
        // read data from SQLite database
        CreateSQLiteTable();
        String sql = "Select * from Members where"; //querey

        if (!_name.isEmpty()) {
            sql += " name like '%" + _name + "%'";
        }
        if (!_libraryNumber.isEmpty()) {
            sql += " and LibraryNumber ='" + _libraryNumber + "'";
        }

        System.out.println(sql);
        try (Connection conn = DriverManager.getConnection(databaseURL, username, password);
                Statement stmt = conn.createStatement()) {
            // create a ResultSet

            ResultSet rs = stmt.executeQuery(sql); //
            // checking if ResultSet is empty
            if (rs.next() == false) {
                System.out.println("ResultSet in empty");
            } else {
                // loop through the result set
                do {

                    String name = rs.getString("name");
                    int libraryNumber = Integer.parseInt(rs.getString("LibraryNumber"));
                 

                    //Movie movie = new Movie(recordId, title, year, rating);
                    //Book book = new Book(recordId, name, author, year);
                    Member member = new Member(libraryNumber, name);
                    searchResult.add(member);
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return searchResult;
    }
    @FXML
    private void handleCheckOutSearchAction(ActionEvent event) throws IOException, SQLException {
        String _BookID = bookIDTextField.getText().trim();
        String _LibraryNumber = memberNumber.getText().trim();
        String _name = nameTextField.getText().trim();
        ObservableList<CheckOut> memItems = searchCheckOut(_LibraryNumber, _BookID, _name);
        checkedOutView.setItems(memItems);

    }
    @SuppressWarnings("empty-statement")
    public ObservableList<CheckOut> searchCheckOut(String _libraryNumber, String _bookID, String _name) throws SQLException {
        ObservableList<CheckOut> searchResult = FXCollections.observableArrayList();
        // read data from SQLite database
        CreateSQLiteTable();
        String sql = "Select * from CheckOut where"; //querey

        if (!_name.isEmpty()) {
            sql += " Name like '%" + _name + "%'";
        }
        if (!_libraryNumber.isEmpty()) {
            sql += " and LibraryNumber ='" + _libraryNumber + "'";
        }
        if (!_bookID.isEmpty()) {
            sql += " and BookID ='" + _bookID + "'";
        }

        System.out.println(sql);
        try (Connection conn = DriverManager.getConnection(databaseURL, username, password);
                Statement stmt = conn.createStatement()) {
            // create a ResultSet

            ResultSet rs = stmt.executeQuery(sql); //
            // checking if ResultSet is empty
            if (rs.next() == false) {
                System.out.println("ResultSet in empty");
            } else {
                // loop through the result set
                do {

              
                    int libraryNumber = Integer.parseInt(rs.getString("LibraryNumber"));
                    int bookNum = Integer.parseInt(rs.getString("BookID"));
                    String bookName = rs.getString("Name");
                 

                    //Movie movie = new Movie(recordId, title, year, rating);
                    //Book book = new Book(recordId, name, author, year);
                    CheckOut checkOut = new CheckOut(libraryNumber, bookNum, bookName);
                    searchResult.add(checkOut);
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return searchResult;
    }
    
    
    @FXML
    private void handleLogSearchAction(ActionEvent event) throws IOException, SQLException {
        /* String _BookID = bookIDTextField.getText().trim();
        String _LibraryNumber = memberNumber.getText().trim();
        String _name = nameTextField.getText().trim();
        ObservableList<CheckOut> memItems = searchCheckOut(_LibraryNumber, _BookID, _name);
        checkedOutView.setItems(memItems);*/
        String _BookID = bookIDText.getText().trim();
        String _action = actionText.getText().trim();
        String _actionDate = actionDateText.getText().trim();
        String _timesChecked = timesCheckedText.getText().trim();
        ObservableList<Logs> logItems = searchLogs(_BookID, _action, _actionDate, _timesChecked);
        logsView.setItems(logItems);

    }
    @SuppressWarnings("empty-statement")
    public ObservableList<Logs> searchLogs(String _BookID, String _action, String _actionDate, String _timesChecked) throws SQLException {
        ObservableList<Logs> searchResult = FXCollections.observableArrayList();
        // read data from SQLite database
        CreateSQLiteTable();
        String sql = "Select * from BookLog where"; //querey

        if (!_BookID.isEmpty()) {
            sql += " BookID ='" + _BookID + "'";
        }
        if (!_action.isEmpty()) {
            sql += " and LastAction like '%" + _action + "%'";
        }
        
        if (!_actionDate.isEmpty()) {
            sql += " and LastActionTime like '%" + _actionDate + "%'";
        }
        if (!_timesChecked.isEmpty()) {
            sql += " and TimesCheckedOut ='" + _timesChecked + "'";
        }

        System.out.println(sql);
        try (Connection conn = DriverManager.getConnection(databaseURL, username, password);
                Statement stmt = conn.createStatement()) {
            // create a ResultSet

            ResultSet rs = stmt.executeQuery(sql); //
            // checking if ResultSet is empty
            if (rs.next() == false) {
                System.out.println("ResultSet in empty");
            } else {
                // loop through the result set
                do {

                    /* int libraryNumber = Integer.parseInt(rs.getString("LibraryNumber"));
                    int bookNum = Integer.parseInt(rs.getString("BookID"));
                    String bookName = rs.getString("Name");*/
                    
                    int bookID = Integer.parseInt(rs.getString("BookID"));
                    String action = rs.getString("LastAction");
                    String actionDate = rs.getString("LastActionTime");
                    int timesCheckedOut = Integer.parseInt(rs.getString("TimesCheckedOut"));
                 
                    Logs log = new Logs(bookID, action, actionDate, timesCheckedOut);
                    //Movie movie = new Movie(recordId, title, year, rating);
                    //Book book = new Book(recordId, name, author, year);
                    
                    searchResult.add(log);
                } while (rs.next());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return searchResult;
    }
    @FXML
    private void handleShowAllRecords(ActionEvent event) throws IOException, SQLException {
        tableView.setItems(data);
        memberView.setItems(memData);
        checkedOutView.setItems(checkOutData);
        logsView.setItems(logData);

    }
    
    

    /**
     * Update a record in the movies table
     *
     * @param name
     * @param year
     * @param author
     * @throws java.sql.SQLException
     */
    public void update(String name,  String author, int year, int selectedIndex, int id) throws SQLException {

        Connection conn = null;
        try {
            // create a connection to the database
            conn = DriverManager.getConnection(databaseURL, username, password);
            String sql = "UPDATE Books SET name = ?, author =?, year =? Where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, author);
            pstmt.setString(3, Integer.toString(year));;
            pstmt.setString(4, Integer.toString(id));

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }

    }

    @FXML
    private void handleUpdateRecord(ActionEvent event) throws IOException, SQLException {

        //Check whether item is selected and set value of selected item to Label
        if (tableView.getSelectionModel().getSelectedItem() != null) {

            int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
            System.out.println("Selected Index: " + selectedIndex);

            if (selectedIndex >= 0) {

                System.out.println(index);
                //Movie movie = (Movie) tableView.getSelectionModel().getSelectedItem();
                Book book = (Book) tableView.getSelectionModel().getSelectedItem();
                System.out.println("ID: " + book.getId());

                try {
                    // insert a new rows
                    //update(nameTextField.getText(), Integer.parseInt(authorTextField.getText()), yearTextField.getText(), selectedIndex, movie.getId());
                    update(nameTextField.getText(), authorTextField.getText(), Integer.parseInt(yearTextField.getText()), selectedIndex, book.getId());
                    System.out.println("Record updated successfully!");
                } catch (SQLException ex) {
                    System.out.println(ex.toString());
                }

                nameTextField.setText("");
                authorTextField.setText("");
                yearTextField.setText("");

                footerLabel.setText("Record updated successfully!");
                data.clear();
                loadData();
                tableView.refresh();
            }

        }

    }
    
   
    
   

    
     
    
}
