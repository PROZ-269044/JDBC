package bd;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class OraConn 
{
	private static String ErrorDescription; // do przechowywania opisu b³êdów;
	private static int ErrorCode; // do przechowywania kodu b³êdu, 100 - nie mo¿na zarejestrowaæ sterownika, 200 - b³¹d otwarcia, 300 - b³¹d zamkniêcia, 400 - próba zamkniêcia nieistniej¹cego po³¹czenia
	
	OraConn()
	{
		ErrorDescription= null; // domyœlnie nie ma opisu b³êdu
		ErrorCode = 0; // domyœlny kod b³êdu to zero
	}
	
	public static Connection open(Connection connection)
	{
		try
		{
			connection = DriverManager.getConnection("jdbc:oracle:thin:@ora3.elka.pw.edu.pl:1521:ora3inf", "mradzimi", "mradzimi");		//polaczenie = connection;
			System.out.println("Po³¹czono z baz¹");
		}
		catch(SQLException Ex)
		{
			setErrorData("B³¹d logowania do bazy b¹dz baza jest uszkodzona", 200);
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
			System.out.println("Próba zamkniêcia po³¹czenia");
			connection.close();
			System.out.println("Po³¹czenie jest zamkniête");
		}
		catch(SQLException Ex)
		{
			setErrorData("B³¹d wylogowania z bazy", 300);
			showErrorData();
			System.out.println(Ex.getSQLState());
			System.out.println(Ex.getErrorCode());
		}
		catch(NullPointerException NEx) // mo¿e dojœæ do wywo³ania metody zamkniêcia bazy, gdy connetion = null - st¹d obs³uga NullPointerException
		{
			setErrorData("Wylogowanie z nieistniej¹cej bazy", 400);
			showErrorData();
		}
	}

	public static void register()
	{
		try
		{
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			System.out.println("Sterownik JDBC zosta³ zarejestrowany");
		}
		catch(SQLException Ex)
		{
			setErrorData("Nie uda³o siê zarejestrowaæ sterownika", 100);
			showErrorData();
			
			
			System.out.println(Ex.getSQLState());
			System.out.println(Ex.getErrorCode());
		}
		
	}
	
	// metody do obs³ugi b³êdów opieraj¹cej siê na polach klasy OraConn - setErrorData ustawia w³asnoœci, showErrorData wyœwietla b³¹d
	public static void setErrorData(String Message, int Code)
	{
		ErrorDescription = Message;
		ErrorCode = Code;
	}
	
	public static void showErrorData()
	{
		System.out.println("Kod Blêdu: " + ErrorCode);
		System.out.println("Opis B³êdu: " + ErrorDescription);
	}
	
}
