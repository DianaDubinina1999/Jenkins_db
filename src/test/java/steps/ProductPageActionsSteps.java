package steps;

import io.cucumber.java.ru.Дано;

import java.sql.*;

public class ProductPageActionsSteps {

    Connection connection;
    Statement statement;

    public ProductPageActionsSteps() throws SQLException {
        String connectionType = System.getProperty("local");
        if (connectionType != null && connectionType.equals("remote")) {

            connection = DriverManager.getConnection
                    ("http://149.154.71.152:8080/h2-console", "user", "pass");
        } else {

            connection = DriverManager.getConnection
                    ("jdbc:h2:tcp://localhost:9092/mem:testdb", "user", "pass");
        }
        statement = connection.createStatement();
    }

    @Дано("заходим на сайт, вводим логин  и пароль")
    public void заходимНаСайтВводимЛогинИПароль () {
    }


    @Дано("Выборка всех строк таблицы FOOD и проверка на количество записей")
    public void выборкаВсехСтрокТаблицыFOODИПроверкаНаКоличествоЗаписей() throws SQLException {
        String query = "SELECT COUNT(*) as rowCount FROM FOOD";
        ResultSet resultSet = statement.executeQuery(query);

        if (resultSet.next()) {
            int rowCount = resultSet.getInt("rowCount");
            if (rowCount != 4) {
                throw new IllegalStateException("Таблица FOOD не содержит 4 записи. Обнаружено записей: " + rowCount);
            } else {
                System.out.println("Таблица FOOD содержит 4 записи.");
            }
        } else {
            throw new IllegalStateException("Не удалось получить количество записей в таблице FOOD");
        }


        }


        @Дано("Добавление новых товаров в таблицу FOOD {int},{string},{string}, {int}")
        public void добавлениеНовыхТоваровВТаблицуFOOD ( int arg0, String arg1, String arg2,int arg3) throws
        SQLException {
            PreparedStatement statement1 = connection.prepareStatement
                    ("INSERT INTO FOOD (FOOD_ID, FOOD_NAME, FOOD_TYPE, FOOD_EXOTIC) VALUES (?, ?, ?, ?)");
            statement1.setInt (1, arg0);
            statement1.setString (2, arg1);
            statement1.setString (3, arg2);
            statement1.setInt (4, arg3);

            statement1.executeUpdate ();
        }

        @Дано("Удаление всех строк таблицы FOOD с индексом >={int}")
       public void удалениеВсехСтрокТаблицыFOODСИндексом ( int arg0) throws SQLException {
           String delete = "DELETE from FOOD where FOOD_ID BETWEEN 5 AND 8";
          statement.executeUpdate (delete);
        }

}
