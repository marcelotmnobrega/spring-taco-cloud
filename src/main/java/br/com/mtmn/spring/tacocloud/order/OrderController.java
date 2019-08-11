package br.com.mtmn.spring.tacocloud.order;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {

	private final OrderRepository orderRepository;

	@GetMapping("/current")
	public String orderForm(Model model) {
		return "orderForm";
	}

	@PostMapping
	public String processOrder(@Valid Order order, Errors errors, SessionStatus sessionStatus) {
		if (errors.hasErrors()) {
			return "orderForm";
		}
		orderRepository.save(order);
		log.info("Order submitted: " + order);
		sessionStatus.setComplete();
		return "redirect:/";
	}

}