package edu.zju.transaction_demo;

import java.sql.SQLException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws SQLException, InterruptedException
    {
    		IsolationDemo.serializable();
    }
}
