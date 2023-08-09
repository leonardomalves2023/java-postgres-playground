package com.example;

public class Cliente{
    private double renda;
    private char sexo;
    private boolean especial;
    private String nome;
    private String cpf;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Cliente(){
        System.out.println("Criando classe Cliente");
    }
    
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        if(nome.isBlank())
            System.out.println("Nome é obrigatório: ");
        else
            this.nome = nome.toUpperCase();
    }
    public double getRenda() {
        return renda;
    }
    public void setRenda(double renda) {
        this.renda = renda;
    }
    public char getSexo() {
        return sexo;
    }
    public void setSexo(char sexo) {
        this.sexo = sexo;
    }
    public boolean isEspecial() {
        return especial;
    }
    public void setEspecial(boolean especial) {
        this.especial = especial;
    }
 

    

}