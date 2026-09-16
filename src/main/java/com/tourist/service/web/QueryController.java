package com.tourist.service.web;

import com.tourist.service.domain.CateringType;
import com.tourist.service.service.QueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class QueryController {

    private final QueryService queryService;

    @GetMapping("/spots")
    public String spots(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("items", queryService.listSpots(keyword));
        model.addAttribute("keyword", keyword);
        return "query/spots";
    }

    @GetMapping("/routes")
    public String routes(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("items", queryService.listRoutes(keyword));
        model.addAttribute("keyword", keyword);
        return "query/routes";
    }

    @GetMapping("/route/{id}")
    public String routeDetail(@PathVariable Long id, Model model) {
        model.addAttribute("route", queryService.getRoute(id));
        return "query/routeDetail";
    }

    @GetMapping("/catering")
    public String catering(@RequestParam(required = false) CateringType type,
                           @RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("items", queryService.listCatering(type, keyword));
        model.addAttribute("type", type);
        model.addAttribute("keyword", keyword);
        return "query/catering";
    }

    @GetMapping("/performance")
    public String performance(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("items", queryService.listPerformance(keyword));
        model.addAttribute("keyword", keyword);
        return "query/performance";
    }

    @GetMapping("/weather")
    public String weather(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("items", queryService.listWeather(keyword));
        model.addAttribute("keyword", keyword);
        return "query/weather";
    }

    @GetMapping("/road")
    public String road(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("items", queryService.listRoad(keyword));
        model.addAttribute("keyword", keyword);
        return "query/road";
    }

    @GetMapping("/transport")
    public String transport(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("items", queryService.listTransport(keyword));
        model.addAttribute("keyword", keyword);
        return "query/transport";
    }
}