/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thogakade;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class CustomerController {
    public static boolean addCustomer(Customer customer) throws ClassNotFoundException, SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stm = connection.prepareStatement("INSERT INTO Customer VALUES(?,?,?,?)");
        stm.setObject(1, customer.getId());
        stm.setObject(2, customer.getName());
        stm.setObject(3, customer.getAddress());
        stm.setObject(4, customer.getSalary());
        
        int res = stm.executeUpdate();
        return res>0;
    }

    public static Customer searchCustomer(String id) throws ClassNotFoundException, SQLException {
        Statement stm = DBConnection.getInstance().getConnection().createStatement();
        ResultSet rst = stm.executeQuery("SELECT * FROM Customer WHERE id = '"+id+"'");
        if(rst.next()){
            return new Customer(id, rst.getString("name"), rst.getString("address"), rst.getDouble("salary"));
        }else{
            return null;
        }
    }
    
    public static boolean updateCustomer(Customer customer) throws ClassNotFoundException, SQLException{
        Connection connection = DBConnection.getInstance().getConnection();
        String SQL = "UPDATE Customer set name = ?, address = ?, salary = ? WHERE id = ?";
        PreparedStatement stm = connection.prepareStatement(SQL);
        
        stm.setObject(1, customer.getName());
        stm.setObject(2, customer.getAddress());
        stm.setObject(3, customer.getSalary());
        stm.setObject(4, customer.getId());
        
        int res = stm.executeUpdate();
        return res>0;
    }
    
    public static boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException{
        return DBConnection.getInstance().getConnection().createStatement().executeUpdate("DELETE FROM Customer WHERE id='"+id+"'")>0;
    }
    
    public static ArrayList<Customer>getAllCustomer() throws SQLException, ClassNotFoundException{
        ResultSet rst = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM Customer");
        ArrayList<Customer>customerList = new ArrayList<>();
        while(rst.next()){
            customerList.add(new Customer(rst.getString("id"), rst.getString("name"), rst.getString("address"), rst.getDouble("salary")));
        }
        return customerList;
    }
    
}
