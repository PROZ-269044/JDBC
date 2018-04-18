package bd;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class OraConn 
{
	private static String ErrorDescription; // do przechowywania opisu b��d�w;
	private static int ErrorCode; // do przechowywania kodu b��du, 100 - nie mo�na zarejestrowa� sterownika, 200 - b��d otwarcia, 300 - b��d zamkni�cia, 400 - pr�ba zamkni�cia nieistniej�cego po��czenia
	
	OraConn()
	{
		ErrorDescription= null; // domy�lnie nie ma opisu b��du
		ErrorCode = 0; // domy�lny kod b��du to zero
	}
	
	public static Connection open(Connection connection)
	{
		try
		{
			connection = DriverManager.getConnection("jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf", "mradzimi", "mradzimi");		//polaczenie = connection;
			System.out.println("Po��czono z baz�");
		}
		catch(SQLException Ex)
		{
			setErrorData("B��d logowania do bazy b�dz baza jest uszkodzona", 200);
			showErrorData();
			
			System.out.println(Ex.getMessage());
			System.out.println(Ex.getErrorCode());
		}
		return connection;
	}
	
	public static void close(Connection connection)
	{	
		try
		{
			System.out.println("Pr�ba zamkni�cia po��czenia");
			connection.close();
			System.out.println("Po��czenie jest zamkni�te");
		}
		catch(SQLException Ex)
		{
			setErrorData("B��d wylogowania z bazy", 300);
			showErrorData();
			System.out.println(Ex.getSQLState());
			System.out.println(Ex.getErrorCode());
		}
		catch(NullPointerException NEx) // mo�e doj�� do wywo�ania metody zamkni�cia bazy, gdy connetion = null - st�d obs�uga NullPointerException
		{
			setErrorData("Wylogowanie z nieistniej�cej bazy", 400);
			showErrorData();
		}
	}

	public static void register()
	{
		try
		{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("Sterownik JDBC zosta� zarejestrowany");
		}
		catch(SQLException Ex)
		{
			setErrorData("Nie uda�o si� zarejestrowa� sterownika", 100);
			showErrorData();
			
			
			System.out.println(Ex.getSQLState());
			System.out.println(Ex.getErrorCode());
		}
		
	}
	
	// metody do obs�ugi b��d�w opieraj�cej si� na polach klasy OraConn - setErrorData ustawia w�asno�ci, showErrorData wy�wietla b��d
	public static void setErrorData(String Message, int Code)
	{
		ErrorDescription = Message;
		ErrorCode = Code;
	}
	
	public static void showErrorData()
	{
		System.out.println("Kod Bl�du: " + ErrorCode);
		System.out.println("Opis B��du: " + ErrorDescription);
	}
	
}
