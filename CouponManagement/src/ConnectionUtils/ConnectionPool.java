package ConnectionUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class ConnectionPool {
	private static ConnectionPool m_connectionPool = null; 
	private Set<Connection> m_connections;//open connections -> running now
	private int curr = 0;
	
	//db connection design
	
    // private constructor restricted to this class itself 
    private ConnectionPool() 
    {       
    	m_connections = new HashSet<Connection>();
   
    	for(int i=0; i< 50 ; i++) {
    		m_connections.add(SQLconnection.GetConnection()); 
    	}
    } 
  
    // static method to create instance of Singleton class 
    public static ConnectionPool getInstance() 
    { 
        if (m_connectionPool == null) {
        	
        	System.out.println("Connection to the mysql DB ....");
        	
        	try {
        	m_connectionPool = new ConnectionPool(); 
        	} catch (Exception e) {
        		e.getMessage();
        	}

        	System.out.println("Connected");
        }
  
        return m_connectionPool; 
    } 
    
    public synchronized Connection getConnection() {
    	Connection connection = null;
    		
    	if(m_connections.isEmpty())
    	{
    		try {
    			wait();
    		}catch (InterruptedException e) {
    			System.out.println(e.getMessage());   
    		}
    	}
    	else {
    		connection = m_connections.iterator().next();
    		m_connections.remove(connection);
    		// TODO: check if iterator.next removes the returned connection. 
    	}
    	
    	System.out.println("connection num " + curr++ + "is out ");
    	return connection;
    }

    public synchronized void restoreConnection(Connection connection) {
    	
    	m_connections.add(connection);
    	notifyAll();
    }
    
    void closeAllConnections() {
    	try {
    		for (Connection var : m_connections) { 
    			var.close();
    		}
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
    }
}
