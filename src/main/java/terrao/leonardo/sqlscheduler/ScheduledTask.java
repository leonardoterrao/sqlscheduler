package terrao.leonardo.sqlscheduler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTask {

	private final SimpleDateFormat hourDateFormate = new SimpleDateFormat("HH:mm:ss");
	private final SimpleDateFormat dateFormate = new SimpleDateFormat("dd/MM/yyyy");

	@Scheduled(fixedDelay=3000)
	public void reportCurrentTime() {
		System.out.println("The time now is: " + hourDateFormate.format(new Date()));
	}
	
	@Scheduled(fixedDelay=10000)
	public void runSql() {
		try {
			final Connection connection = DriverManager.getConnection("address", "login", "pass");
			final Statement statement = connection.createStatement();
			final ResultSet resultSet = statement.executeQuery("SELECT * FROM FERIADOS WHERE DATA > SYSDATE");
			
			System.out.println("DATA  |  DESCRIÇÃO");
			
			while (resultSet.next()) {
				Date data = resultSet.getDate("DATA");
				String descricao = resultSet.getString("DESCRICAO");
				System.out.println(dateFormate.format(data) + "  |  " + descricao);
			}
			
		} catch (SQLException sqlEsException) {
			sqlEsException.printStackTrace();
		}

	}
	
}
