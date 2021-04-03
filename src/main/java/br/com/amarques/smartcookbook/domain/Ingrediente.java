package br.com.amarques.smartcookbook.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "ingredientes")
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @OneToOne
    @JoinColumn(name = "receita_id")
    private Receita receita;

    protected Ingrediente() {}

    public Ingrediente(Receita receita){
        this();
        this.receita = receita;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Receita getReceita() {
        return receita;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingrediente that = (Ingrediente) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(receita, that.receita);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, receita);
    }
}
