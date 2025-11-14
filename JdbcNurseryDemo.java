import java.sql.*;
import java.util.Scanner;

public class JdbcNurseryDemo {

    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/nurserydatabase";
        String user = "root";      
        String pass = "12345";      

        Scanner sc = new Scanner(System.in);

        try {
           
            Class.forName("com.mysql.cj.jdbc.Driver");

            
            Connection con = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Connected to Nursery Database!");

            while (true) {
                System.out.println("\n=== 🌱 Plant Nursery Menu ===");
                System.out.println("1. Add New Plant");
                System.out.println("2. Display All Plants");
                System.out.println("3. Update Plant Details");
                System.out.println("4. Delete Plant");
                System.out.println("5. Search Plant by Type");
                System.out.println("6. Exit");
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();

                switch (choice) {

                    case 1:
                        
                        System.out.print("Enter Plant ID: ");
                        int pid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Plant Name: ");
                        String pname = sc.nextLine();
                        System.out.print("Enter Plant Type: ");
                        String ptype = sc.nextLine();
                        System.out.print("Enter Price: ");
                        double price = sc.nextDouble();
                        System.out.print("Enter Quantity: ");
                        int qty = sc.nextInt();

                        String insertQuery = "INSERT INTO plants VALUES (?, ?, ?, ?, ?)";
                        PreparedStatement psInsert = con.prepareStatement(insertQuery);
                        psInsert.setInt(1, pid);
                        psInsert.setString(2, pname);
                        psInsert.setString(3, ptype);
                        psInsert.setDouble(4, price);
                        psInsert.setInt(5, qty);

                        int rowsInserted = psInsert.executeUpdate();
                        System.out.println(rowsInserted + " plant added successfully!");
                        psInsert.close();
                        break;

                    case 2:
                       
                        String selectQuery = "SELECT * FROM plants";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(selectQuery);

                        System.out.println("\nID\tName\t\tType\t\tPrice\tQuantity");
                        System.out.println("--------------------------------------------------");
                        while (rs.next()) {
                            System.out.println(rs.getInt("plant_id") + "\t" +
                                               rs.getString("plant_name") + "\t\t" +
                                               rs.getString("plant_type") + "\t\t" +
                                               rs.getDouble("price") + "\t" +
                                               rs.getInt("quantity"));
                        }
                        rs.close();
                        stmt.close();
                        break;

                    case 3:
                        
                        System.out.print("Enter Plant ID to update: ");
                        int upid = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter new Plant Name: ");
                        String newName = sc.nextLine();
                        System.out.print("Enter new Price: ");
                        double newPrice = sc.nextDouble();
                        System.out.print("Enter new Quantity: ");
                        int newQty = sc.nextInt();

                        String updateQuery = "UPDATE plants SET plant_name=?, price=?, quantity=? WHERE plant_id=?";
                        PreparedStatement psUpdate = con.prepareStatement(updateQuery);
                        psUpdate.setString(1, newName);
                        psUpdate.setDouble(2, newPrice);
                        psUpdate.setInt(3, newQty);
                        psUpdate.setInt(4, upid);

                        int rowsUpdated = psUpdate.executeUpdate();
                        System.out.println(rowsUpdated + " plant updated successfully!");
                        psUpdate.close();
                        break;

                    case 4:
                        
                        System.out.print("Enter Plant ID to delete: ");
                        int delid = sc.nextInt();

                        String deleteQuery = "DELETE FROM plants WHERE plant_id=?";
                        PreparedStatement psDelete = con.prepareStatement(deleteQuery);
                        psDelete.setInt(1, delid);

                        int rowsDeleted = psDelete.executeUpdate();
                        System.out.println(rowsDeleted + " plant deleted successfully!");
                        psDelete.close();
                        break;

                    case 5:
                        
                        sc.nextLine(); 
                        System.out.print("Enter Plant Type to search: ");
                        String searchType = sc.nextLine();

                        String searchQuery = "SELECT * FROM plants WHERE plant_type=?";
                        PreparedStatement psSearch = con.prepareStatement(searchQuery);
                        psSearch.setString(1, searchType);
                        ResultSet rsSearch = psSearch.executeQuery();

                        boolean found = false;
                        System.out.println("\nID\tName\t\tType\t\tPrice\tQuantity");
                        System.out.println("--------------------------------------------------");
                        while (rsSearch.next()) {
                            found = true;
                            System.out.println(rsSearch.getInt("plant_id") + "\t" +
                                               rsSearch.getString("plant_name") + "\t\t" +
                                               rsSearch.getString("plant_type") + "\t\t" +
                                               rsSearch.getDouble("price") + "\t" +
                                               rsSearch.getInt("quantity"));
                        }
                        if (!found) {
                            System.out.println("No plants found of type: " + searchType);
                        }
                        rsSearch.close();
                        psSearch.close();
                        break;

                    case 6:
                        
                        System.out.println("Exiting program... Goodbye! 🌿");
                        con.close();
                        sc.close();
                        System.exit(0);

                    default:
                        System.out.println("❌ Invalid choice! Try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
