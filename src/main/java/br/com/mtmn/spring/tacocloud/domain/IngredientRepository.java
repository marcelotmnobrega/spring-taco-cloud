package br.com.mtmn.spring.tacocloud.domain;

public interface IngredientRepository {

    Iterable<Ingredient> findAll();
    Ingredient findOne(String id);
    Ingredient save(Ingredient ingredient);
}
