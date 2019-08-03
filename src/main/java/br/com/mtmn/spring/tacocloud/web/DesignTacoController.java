package br.com.mtmn.spring.tacocloud.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.mtmn.spring.tacocloud.domain.*;
import br.com.mtmn.spring.tacocloud.domain.Ingredient.Type;
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

//    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
//        return ingredients.stream()
//                .filter(i -> i.getType().equals(type))
//                .collect(Collectors.toList());
//    }

    @GetMapping
    public String showDesignForm(Model model) {

        List<Ingredient> ingredients = new ArrayList<>();
        ingredientRepository.findAll().forEach(ingredients::add);

        Map<String, List<Ingredient>> collect =
                ingredients.stream().collect(Collectors.groupingBy(i -> i.getType().toString().toLowerCase()));
        model.addAllAttributes(collect);
        model.addAttribute("design", new Taco());
        return "design";
    }

    @PostMapping
    public String processDesign(@Valid @ModelAttribute("design") Taco design, Errors errors, @ModelAttribute Order order) {

        if (errors.hasErrors()) {
            return "design";
        }
        log.info("Processing design: " + design);
        Taco saved = tacoRepository.save(design);
//        order.addDesign(saved);

        return "redirect:/orders/current";
    }
}
