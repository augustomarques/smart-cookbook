package br.com.amarques.smartcookbook.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "receitas")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "modo_preparo")
    private String modoPreparo;

    public Receita() {}

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

    public String getModoPreparo() {
        return modoPreparo;
    }

    public void setModoPreparo(String modoPreparo) {
        this.modoPreparo = modoPreparo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Receita receita = (Receita) o;
        return Objects.equals(id, receita.id) && Objects.equals(nome, receita.nome) && Objects.equals(modoPreparo, receita.modoPreparo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, modoPreparo);
    }
}
