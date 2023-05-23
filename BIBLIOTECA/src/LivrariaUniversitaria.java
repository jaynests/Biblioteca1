import java.sql.*;
public class LivrariaUniversitaria {
    private static final String URL = "jdbc:mysql://localhost:3306/livraria_universitaria";
    private static final String USER = "seu_usuario";
    private static final String PASSWORD = "sua_senha";

    public static void main(String[] args) {
        try {
            // Conexão com o banco de dados
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

            // Menu principal
            int opcao;
            do {
                System.out.println("---- Menu ----");
                System.out.println("<1> Cadastrar Livro");
                System.out.println("<2> Pesquisar Livro por Preço");
                System.out.println("<3> Pesquisar Livro por Título");
                System.out.println("<4> Excluir Livro");
                System.out.println("<5> Sair");

                opcao = Integer.parseInt(System.console().readLine("Escolha uma opção: "));

                switch (opcao) {
                    case 1:
                        cadastrarLivro(connection);
                        break;
                    case 2:
                        pesquisarLivroPorPreco(connection);
                        break;
                    case 3:
                        pesquisarLivroPorTitulo(connection);
                        break;
                    case 4:
                        excluirLivro(connection);
                        break;
                    case 5:
                        System.out.println("Encerrando o programa.");
                        break;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                        break;
                }

                System.out.println();
            } while (opcao != 5);

            connection.close();
        } catch (SQLException e) {
            System.out.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }

    public static void cadastrarLivro(Connection connection) throws SQLException {
        System.out.println("---- Cadastro de Livro ----");

        String titulo = System.console().readLine("Título: ");
        String autor = System.console().readLine("Autor: ");
        float preco = Float.parseFloat(System.console().readLine("Preço: "));

        String query = "INSERT INTO Livros (titulo, autor, vl_preco) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, titulo);
        statement.setString(2, autor);
        statement.setFloat(3, preco);
        statement.executeUpdate();

        System.out.println("Livro cadastrado com sucesso!");
    }

    public static void pesquisarLivroPorPreco(Connection connection) throws SQLException {
        System.out.println("---- Pesquisa de Livro por Preço ----");

        float valor = Float.parseFloat(System.console().readLine("Digite o valor mínimo do livro: "));

        String query = "SELECT * FROM Livros WHERE vl_preco >= ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setFloat(1, valor);
        ResultSet resultSet = statement.executeQuery();

        System.out.println("Livros encontrados:");
        while (resultSet.next()) {
            int id = resultSet.getInt("id_livro");
            String titulo = resultSet.getString("titulo");
            String autor = resultSet.getString("autor");
            float preco = resultSet.getFloat("vl_preco");

            System.out.println("ID: " + id
