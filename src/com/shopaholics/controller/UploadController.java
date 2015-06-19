package com.shopaholics.controller;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 







import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;

/**
 * Servlet implementation class UploadServlet
 */
@WebServlet("/uploadController")
@MultipartConfig(maxFileSize = 16177215) 
public class UploadController extends HttpServlet {
	String url ="jdbc:mysql://localhost:3306/shopaholics";
	//String url ="jdbc:postgresql://localhost:5432/sample";
	String user ="root";
	//String user ="postgres";
	String password ="13wh1a0507";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
		
	}

	PreparedStatement st1 = null;
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doProcess(request, response);
	}
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// gets values of text fields
        String productname = request.getParameter("productname");
        String description = request.getParameter("description");
        String selected = null;
       /* if(request.getParameter("points") != null) {
        	selected = request.getParameter("points");
        	 System.out.println("in if"+selected);
        }*/
        selected = request.getParameter("points");
               System.out.println("selected"+selected);
        String img_id = request.getParameter("productid");
      //  System.out.println("after");
        String img = request.getParameter("photo");
        int  price = Integer.parseInt(request.getParameter("price"));
        int  stock = Integer.parseInt(request.getParameter("stock"));
        int  offer = Integer.parseInt(request.getParameter("offers"));
        InputStream inputStream = null; // input stream of the upload file
       // System.out.println(firstName);
      //  System.out.println(img_id);
        // System.out.println(img);
        // obtains the upload file part in this multipart request
        Part filePart = request.getPart("photo");
        System.out.println(filePart);
        if (filePart != null) {
            // prints out some information for debugging
            System.out.println(filePart.getName());
            System.out.println(filePart.getSize());
            System.out.println(filePart.getContentType());
             
            // obtains input stream of the upload file
            inputStream = filePart.getInputStream();
        }
         
        Connection conn = null; // connection to the database
        String message = null;  // message will be sent back to client
        
		
        try {
            // connects to the database
        	Class.forName("com.mysql.jdbc.Driver");
        	//Class.forName("org.postgresql.Driver");
        	System.out.println("mysql driver is loaded");
            conn = DriverManager.getConnection(url, user, password);
            HttpSession hs = request.getSession();
			//add name as session attribute
			String name=(String)hs.getAttribute("sunm");
            String query1 ="select seller_id from seller where seller_name = '"+name+"'";
    		st1 = conn.prepareStatement(query1);
    		System.out.println("after st1 ");
    		ResultSet rst1 = st1.executeQuery();
    		System.out.println("after rst1");
    		int seller_id = 0;
    		while(rst1.next()){
    			seller_id = rst1.getInt("seller_id");
    			System.out.println(seller_id);
    		}
    		
            // constructs SQL statement
            String sql = "insert into product(prod_id, prod_name,description,stock,price,image, offers,seller_id,main_cat) values (?,?,?,?,?,?,?,?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, img_id);
            statement.setString(2, productname);
            statement.setString(3, description);
            statement.setInt(4, stock);
            statement.setInt(5, price);
             
            if (inputStream != null) {
                // fetches input stream of the upload file for the blob column
            	System.out.println(inputStream);
                statement.setBlob(6, inputStream);
            }
            statement.setInt(7, offer);
            statement.setInt(8, seller_id);
            statement.setString(9, selected);
            
            // sends the statement to the database server
            int row = statement.executeUpdate();
            if (row > 0) {
                message = "File uploaded and saved into database";
            }
        } catch (SQLException ex) {
            message = "ERROR: " + ex.getMessage();
            ex.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            if (conn != null) {
                // closes the database connection
                try {
                    conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            // sets the message in request scope
            request.setAttribute("Message", message);
             
            // forwards to the message page
            getServletContext().getRequestDispatcher("/productslist.jsp").forward(request, response);
        }
    }
	}


