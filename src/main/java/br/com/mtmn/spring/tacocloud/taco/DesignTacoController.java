package br.com.mtmn.spring.tacocloud.taco;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.mtmn.spring.tacocloud.taco.*;
import br.com.mtmn.spring.tacocloud.ingredient.Ingredient;
import br.com.mtmn.spring.tacocloud.ingredient.IngredientRepository;
import br.com.mtmn.spring.tacocloud.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
@RequiredArgsConstructor
@SessionAttributes("order")
public class DesignTacoController {

	private final IngredientRepository ingredientRepository;

	private final TacoRepository tacoRepository;

	@ModelAttribute(name = "order")
	public Order order() {
		return new Order();
	}

	@ModelAttribute(name = "taco")
	public Taco taco() {
		return new Taco();
	}

	@GetMapping
	public String showDesignForm(Model model) {

		List<Ingredient> ingredients = new ArrayList<>();
		ingredientRepository.findAll().forEach(ingredients::add);

		Map<String, List<Ingredient>> ingredientByType = ingredients.stream()
				.collect(Collectors.groupingBy(ing -> ing.getType().toString().toLowerCase()));
		model.addAllAttributes(ingredientByType);
		model.addAttribute("taco", new Taco());
		return "design";
	}

	@PostMapping
	public String processDesign(@Valid @ModelAttribute("taco") Taco taco, Errors errors, @ModelAttribute Order order) {

		if (errors.hasErrors()) {
			return "design";
		}
		log.info("Processing design: " + taco);
		Taco saved = tacoRepository.save(taco);
		order.addDesign(saved);

		return "redirect:/orders/current";
	}

}
