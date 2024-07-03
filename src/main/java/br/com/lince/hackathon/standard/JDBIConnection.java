package br.com.lince.hackathon.standard;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class JDBIConnection {
    private static final Logger logger = Logger.getLogger(JDBIConnection.class.getName());

    /**
     * Chamada padrão para iniciar uma instância de JDBI, para manipular banco de dados.
     *
     * @return retorna instancia do JDBI conectado ao banco de dados da aplicação
     */
    public static Jdbi instance() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            final var env = System.getenv();
            final var server = env.get("DATABASE_SERVER");
            final var database = env.get("DATABASE_NAME");
            final var user = env.get("DATABASE_USER");
            final var password = env.get("DATABASE_PASSWORD");

            if (server == null || server.isBlank()) {
                logger.severe("DATABASE_SERVER vazio");
            }
            if (database == null || database.isBlank()) {
                logger.severe("DATABASE_NAME vazio");
            }
            if (user == null || user.isBlank()) {
                logger.severe("DATABASE_USER vazio");
            }
            if (password == null || password.isBlank()) {
                logger.severe("DATABASE_PASSWORD vazio");
            }

            final var connection = DriverManager.getConnection(
                    "jdbc:sqlserver://" + server
                            + ";SelectMethod=cursor;sendStringParametersAsUnicode=false;responseBuffering=adaptive;"
                            + "User=" + user
                            + ";PassWord=" + password
                            + ";DataBaseName=" + database
                            + ";encrypt=false"
            );

            return Jdbi.create(connection).installPlugin(new SqlObjectPlugin());
        } catch (final SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Erro de configuração no banco de dados", e);
        }
    }
}
