package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    private static final String PASSWORD = "";
    private static final String USERNAME = "gitpod";
    private static final String JDBC_URL = "jdbc:postgresql://localhost/postgres";

    public static void main(String[] args) {
        System.out.println();
        System.out.println("Aplicação Java de Exemplo\n");

        Cliente cliente = new Cliente();

        cliente.setRenda(1500);
        cliente.setSexo('F');
        cliente.setNome("Bento");
        cliente.setCpf("777777");
        System.out.println("");

        Cliente cliente1 = new Cliente();

        cliente1.setRenda(2000);
        cliente1.setSexo('M');
        cliente1.setNome("Miguel");
        cliente1.setCpf("777777");
        System.out.println("");


        System.out.println("Sexo: "+cliente.getNome());
        System.out.println("Renda: "+cliente.getRenda());
        System.out.println("Sexo: "+cliente.getSexo());
        System.out.println("Sexo: "+cliente.getCpf());

        if(cliente.getCpf().equals(cliente1.getCpf()))
            System.out.println("Clientes tem o mesmo CPF!");
        else System.out.println("Clientes tem os CPF diferentes!");

        System.out.println("");

        System.out.println("Sexo: "+cliente1.getNome());
        System.out.println("Renda: "+cliente1.getRenda());
        System.out.println("Sexo: "+cliente1.getSexo());
        System.out.println("Sexo: "+cliente1.getCpf());

        if(cliente.getCpf().equals(cliente1.getCpf()))
            System.out.println("Clientes tem o mesmo CPF!");
        else System.out.println("Clientes tem os CPF diferentes!");
     

        //new App();
    }

    public App(){
        try(var conn = getConnection()){
            carregarDriverJDBC();
            listarEstados(conn);
            localizarEstado(conn, "PR");
            listarDadosTabela(conn, "produto");
        } catch (SQLException e) {
            System.err.println("Não foi possível conectar ao banco de dados: " + e.getMessage());
        }        
    }

    private void listarDadosTabela(Connection conn, String tabela) {
        var sql = "select * from " + tabela;
        //System.out.println(sql);
        try {
            var statement = conn.createStatement();
            var result = statement.executeQuery(sql);

            var metadata = result.getMetaData();
            int cols = metadata.getColumnCount();

            for (int i = 1; i <= cols; i++) {
                System.out.printf("%-25s | ", metadata.getColumnName(i));
            }
            System.out.println();

            while(result.next()){
                for (int i = 1; i <= cols; i++) {
                    System.out.printf("%-25s | ", result.getString(i));
                }
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("Erro na execução da consulta: " + e.getMessage());
        }
        
    }

    private void localizarEstado(Connection conn, String uf) {
        try{
            //var sql = "select * from estado where uf = '" + uf + "'"; //suscetível a SQL Injection
            var sql = "select * from estado where uf = ?";
            var statement = conn.prepareStatement(sql);
            //System.out.println(sql);
            statement.setString(1, uf);
            var result = statement.executeQuery();
            if(result.next()){
                System.out.printf("Id: %d Nome: %s UF: %s\n", result.getInt("id"), result.getString("nome"), result.getString("uf"));
            }
            System.out.println();
        } catch(SQLException e){
            System.err.println("Erro ao executar consulta SQL: " + e.getMessage());
        }
        
    }

    private void listarEstados(Connection conn) {
        try{
            System.out.println("Conexão com o banco realizada com sucesso.");

            var statement = conn.createStatement();
            var result = statement.executeQuery("select * from estado");
            while(result.next()){
                System.out.printf("Id: %d Nome: %s UF: %s\n", result.getInt("id"), result.getString("nome"), result.getString("uf"));
            }
            System.out.println();
        } catch (SQLException e) {
            System.err.println("Não foi possível executar a consulta ao banco: " + e.getMessage());
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    private void carregarDriverJDBC() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Não foi possível carregar a biblioteca para acesso ao banco de dados: " + e.getMessage());
        }
    }
}
