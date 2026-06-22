package com.example.finmarkprojectfiner.catalog.prodcontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class FinmarkProdPageController {

    @GetMapping("/financialservices")
    public String financialServices() {
        return "financial-services"; //pointing to financial-services.html
    }

    @GetMapping("/marketinganalytics")
    public String marketingServices() {
        return "marketing-services"; //pointing to marketing-services.html
    }

    @GetMapping("/bi-services")
    public String biServices() {
        return "bi-services"; //pointing to bi-services.html
    }

    @GetMapping("/consultingservices")
    public String consultingServices() {
        return "consulting-services"; //pointing to consulting-services.html
    }

}
