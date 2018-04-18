package bd;

import java.sql.Connection;

public class Test {

	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		Connection connection = null;
		OraConn.register();
		connection = OraConn.open(connection);
		OraConn.close(connection);
	}

}
