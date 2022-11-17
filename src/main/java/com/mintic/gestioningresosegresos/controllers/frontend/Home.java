package com.mintic.gestioningresosegresos.controllers.frontend;

import com.mintic.gestioningresosegresos.models.dtos.SistemaGestion;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class Home {

    @GetMapping
    public String viewHome(Model model) {
        List<SistemaGestion> sistemas = new ArrayList<>();
        sistemas.add(new SistemaGestion("empresas", "empresas", "https://cdn1.iconfinder.com/data/icons/business-partnership-color/64/corporation-bank-company-organization-enterprise-128.png"));
        sistemas.add(new SistemaGestion("empleados", "empleados", "https://cdn1.iconfinder.com/data/icons/seo-and-development-hexagon/128/2-128.png"));
        sistemas.add(new SistemaGestion("ingresos y gastos", "ingresos-gastos", "https://cdn2.iconfinder.com/data/icons/scenarium-vol-3-1/128/002_money_cash_gold_wealth_income_deposit_finance-128.png"));

        model.addAttribute("sistemas", sistemas);
        model.addAttribute("title", "Home");
        return "home";
    }
}
