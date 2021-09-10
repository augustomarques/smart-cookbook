package br.com.amarques.smartcookbook.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "ingredients")
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recipe_id", updatable = false)
    private final Recipe recipe;

    protected Ingredient() {
        this.recipe = null;
    }

    public Ingredient(Recipe recipe) {
        this.recipe = recipe;
    }

}
