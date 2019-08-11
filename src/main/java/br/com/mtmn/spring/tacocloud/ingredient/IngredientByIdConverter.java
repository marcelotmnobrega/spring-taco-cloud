package br.com.mtmn.spring.tacocloud.ingredient;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IngredientByIdConverter implements Converter<String, Ingredient> {

	private final IngredientRepository ingredientRepo;

	@Override
	public Ingredient convert(String id) {
		return ingredientRepo.findById(id).orElse(null);
	}

}
