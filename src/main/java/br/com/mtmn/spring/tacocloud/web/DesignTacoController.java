package br.com.mtmn.spring.tacocloud.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.mtmn.spring.tacocloud.domain.Ingredient;
import br.com.mtmn.spring.tacocloud.domain.Ingredient.Type;
import br.com.mtmn.spring.tacocloud.domain.IngredientRepository;
import br.com.mtmn.spring.tacocloud.domain.Taco;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("/design")
@RequiredArgsConstructor
public class DesignTacoController {

    private final IngredientRepository ingredientRepository;

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(i -> i.getType().equals(type))
                .collect(Collectors.toList());
    }

    @GetMapping
    public String showDesignForm(Model model) {

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        Stream.of(Type.values()).forEach(type -> {
            model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
        });

        model.addAttribute("design", new Taco());
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors) {
        if (errors.hasErrors()) {
            return "design";
        }

        // Save the taco design...
        // We'll do this in chapter 3
        log.info("Processing design: " + design);

        return "redirect:/orders/current";
    }
}
